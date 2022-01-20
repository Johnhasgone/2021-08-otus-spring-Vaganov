package ru.otus.spring15homework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowExtension;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import ru.otus.spring15homework.domain.DraftLaw;
import ru.otus.spring15homework.service.DumaSecretariat;
import ru.otus.spring15homework.service.InitiativeDepartment;
import ru.otus.spring15homework.service.ProfileCommittees;

import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

@Configuration
@EnableIntegration
public class IntegrationConfig {
    @Autowired
    private InitiativeDepartment department;

    @Autowired
    private DumaSecretariat secretariat;

    @Autowired
    private ProfileCommittees committees;


    //TODO think about what channel type to choose
    @Bean
    public QueueChannel departmentChannel() {
        return MessageChannels.queue( 10 ).get();
    }

    @Bean QueueChannel stateDumaFirstReadingChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean QueueChannel stateDumaSecondReadingChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean QueueChannel stateDumaThirdReadingChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean QueueChannel federalCouncilChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel publicationChannel() {
        return MessageChannels.publishSubscribe(System.out::println).get();
    }

    @Bean
    public PublishSubscribeChannel committeeChannel() {
        return MessageChannels.publishSubscribe().get();
    }


    @Bean
    public IntegrationFlow mainLegislativeProcessFlow() {
        return IntegrationFlows.fromSupplier(department::createDraftLaw)
                .channel(departmentChannel())
                .log(LoggingHandler.Level.INFO, "В секретариат Госдумы поступил законопроект ",
                        m -> ((DraftLaw) m.getPayload()).getTitle())
                .handle(m -> secretariat.prepareForApprovalProcess((Message<DraftLaw>) m)) // or transform or string bean and method name
                .log(LoggingHandler.Level.INFO, "Законопроект подготовлен в серетариате Госдумы и отправлен в профильные комитеты")
                .transform(Message.class, m -> committees.checkEconomicIssues(m))
                .route("headers['commiteeApproval']",
                        m -> m
                                .subFlowMapping(false, acceptModificationsFlow())
                                .subFlowMapping(true, sf -> sf.gateway(stateDumaFirstReadingChannel())))

                .channel("stateDumaFirstReadingChannel")
                .handle("stateDuma", "readFirst") // TODO add bean
                .route("headers['dumaApproval1']",
                        m -> m.subFlowMapping(false, acceptModificationsFlow())
                                .subFlowMapping(true, sf -> sf.gateway(stateDumaSecondReadingChannel())))

                .channel(stateDumaSecondReadingChannel())
                .handle("stateDuma", "readSecond")
                .route("headers['dumaApproval2']",
                        m -> m.subFlowMapping(false, acceptModificationsFlow())
                                .subFlowMapping(true, sf -> sf.gateway(stateDumaThirdReadingChannel())))

                .channel(stateDumaThirdReadingChannel())
                .enrichHeaders(Map.of("vote", new Random().nextBoolean()))
                .route("headers['vote']", m -> m.channelMapping(false, new NullChannel())
                        .subFlowMapping(true, sf -> sf.channel(federalCouncilChannel())))

                .channel(federalCouncilChannel())
                .enrichHeaders(Map.of("veto", new Random().nextBoolean())) // TODO change weight?
                .route()

                .channel(presidentChannel())
                .enrichHeaders(Map.of("veto", new Random().nextBoolean()))

                .channel("publicationChannel")
                .log(LoggingHandler.Level.INFO, "", m -> m.getHeaders().get("resolution")) // veto?
                .logAndReply(LoggingHandler.Level.INFO, "", message -> {}) // in the end of flow = president veto
                .get();
    }

    @Bean
    public IntegrationFlow acceptModificationsFlow() {
        return f -> f
                .transform(Message.class, m -> department.acceptModifications(m)) // maybe work only with payload
                .to(mainLegislativeProcessFlow());
    }

    @Bean
    public IntegrationFlow

    // to use for return for rework if draft has some modifications (secretariat gets result from
    // committees, then decides where to send draft - back to department, or to 1st reading in state duma)
    // subflow mapping is for continuing draft approving, channel mapping - for return

    // we can use this for sending to null channel after veto too with making separate vetoFlow with only one channel
    // and log("На законопроект наложено вето") вето ставится в header?
    @Bean
    public IntegrationFlow routeFlow() {
        return f -> f
                .<Integer, Boolean>route(p -> p % 2 == 0,
                        m -> m.channelMapping(true, "departmentChannel")
                                .subFlowMapping(false, sf ->
                                        sf.<Integer>handle((p, h) -> p * 3)))
                .transform(Object::toString)
                .channel(c -> c.queue("oddChannel"));
    }

    // We can do this another way - with sending all to different subflows.
    // Don't need this now, but who knows...
    // don't forget to use gateway to continue the main flow after one of subflow!!!
    @Bean
    public IntegrationFlow splitRouteAggregate() {
        return f -> f
                .split()
                .<Integer, Boolean>route(o -> o % 2 == 0,
                        m -> m
                                .subFlowMapping(true, oddFlow())
                                .subFlowMapping(false, sf -> sf.gateway(evenFlow())))
                .aggregate();
    }

    @Bean
    public IntegrationFlow oddFlow() {
        return f -> f.handle(m -> System.out.println("odd"));
    }

    @Bean
    public IntegrationFlow evenFlow() {
        return f -> f.handle((p, h) -> "even");
    }

    // We can create custom flow methods too, maybe useful

    public class CustomIntegrationFlowDefinition
            extends IntegrationFlowExtension<CustomIntegrationFlowDefinition> {

        public CustomIntegrationFlowDefinition upperCaseAfterSplit() {
            return split()
                    .transform("payload.toUpperCase()");
        }

        public CustomIntegrationFlowDefinition customAggregate(Consumer<CustomAggregatorSpec> aggregator) {
            return register(new CustomAggregatorSpec(), aggregator);
        }

    }

    public class CustomAggregatorSpec extends AggregatorSpec {

        CustomAggregatorSpec() {
            outputProcessor(group ->
                    group.getMessages()
                            .stream()
                            .map(Message::getPayload)
                            .map(String.class::cast)
                            .collect(Collectors.joining(", ")));
        }

    }

}
