package ru.otus.spring15homework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;
import ru.otus.spring15homework.domain.DraftLaw;
import ru.otus.spring15homework.domain.Header;
import ru.otus.spring15homework.service.DumaSecretariat;
import ru.otus.spring15homework.service.InitiativeDepartment;
import ru.otus.spring15homework.service.ProfileCommittees;

import java.util.Map;
import java.util.Random;

@Configuration
@EnableIntegration
public class IntegrationConfig {
    @Autowired
    private InitiativeDepartment department;

    @Autowired
    private DumaSecretariat secretariat;

    @Autowired
    private ProfileCommittees committees;

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate( 100 ).maxMessagesPerPoll( 1 ).get();
    }

    //TODO think about what channel type to choose
    @Bean
    public QueueChannel departmentChannel() {
        return MessageChannels.queue( 10 ).get();
    }

    @Bean
    public QueueChannel stateDumaFirstReadingChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public QueueChannel stateDumaSecondReadingChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public QueueChannel stateDumaThirdReadingChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public QueueChannel federalCouncilChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public QueueChannel presidentChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public QueueChannel prepareLawChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel publicationChannel() {
        return MessageChannels.publishSubscribe(System.out::println).get();
    }

    @Bean
    public QueueChannel committeeChannel() {
        return MessageChannels.queue(10).get();
    }


    @Bean
    public IntegrationFlow mainLegislativeProcessFlow() {
        return IntegrationFlows
                //.fromSupplier(department::createDraftLaw)
                .from(departmentChannel())
                .log(LoggingHandler.Level.INFO, "Секретариат Государственной Думы:",
                        m -> "Поступил на согласование проект: " + ((DraftLaw) m.getPayload()).getTitle())

                // transform требует измененения метода подготовки сообщения (Message -> Message)
                .handle("dumaSecretariat", "prepareForApprovalProcess") // or transform or string bean and method name
                .log(LoggingHandler.Level.INFO, "Секретариат Государственной Думы:",
                        m -> ((DraftLaw) m.getPayload()).getTitle() + " передан на согласование в профильные комитеты")

                .handle("profileCommittees", "checkDraftLaw")
                //.transform(Message.class, m -> committees.checkDraftLaw(m))
                .log(LoggingHandler.Level.INFO, "профильные комитеты", m -> "done")
                .route("headers['committeeApproval']",
                        m -> m.subFlowMapping(false, acceptModificationsFlow())
                                .subFlowMapping(true, sf -> sf.gateway(stateDumaFirstReadingChannel())))
                .log(LoggingHandler.Level.INFO, "Профильные комитеты:",
                        m -> String.format("Законопроект \"%s\" одобрен и передан в Госдуму для обсуждения в Первом чтении",
                                ((DraftLaw)m.getPayload()).getTitle()))

                .channel(stateDumaFirstReadingChannel())
                .handle("stateDuma", "readFirst") // TODO add bean
                .route("headers['dumaApproval1']",
                        m -> m.subFlowMapping(false, acceptModificationsFlow())
                                .subFlowMapping(true, sf -> sf.gateway(stateDumaSecondReadingChannel())))
                .log(LoggingHandler.Level.INFO, "Государственная дума:",
                        m -> String.format("Законопроект \"%s\" одобрен в Первом чтении",
                        ((DraftLaw)m.getPayload()).getTitle()))

                .channel(stateDumaSecondReadingChannel())
                .handle("stateDuma", "readSecond")
                .route("headers['dumaApproval2']",
                        m -> m.subFlowMapping(false, acceptModificationsFlow())
                                .subFlowMapping(true, sf -> sf.gateway(stateDumaThirdReadingChannel())))
                .log(LoggingHandler.Level.INFO, "Государственная дума:",
                        m -> String.format("Законопроект \"%s\" одобрен во Втором чтении",
                                ((DraftLaw)m.getPayload()).getTitle()))

                .channel(stateDumaThirdReadingChannel())
                .handle("stateDuma", "vote")
                .route("headers['dumaVoteResult']",
                        m -> m.subFlowMapping(false, BaseIntegrationFlowDefinition::nullChannel)
                                .subFlowMapping(true, sf -> sf.gateway(federalCouncilChannel())))
                .log(LoggingHandler.Level.INFO, "Государственная дума:",
                        m -> String.format("Законопроект \"%s\" принят в Третьем чтении",
                                ((DraftLaw)m.getPayload()).getTitle()))

                .channel(federalCouncilChannel())
                .enrichHeaders(Map.of(Header.COUNCIL_APPROVAL.getValue(), new Random().nextBoolean()))
                .log(LoggingHandler.Level.INFO, "Совет Федерации: ",
                        m -> String.format((boolean) m.getHeaders().get(Header.COUNCIL_APPROVAL.getValue())
                                ? "Законопроект \"%s\" одобрен Советом Федерации"
                                : "На законопроект \"%s\" наложено вето",
                                ((DraftLaw)m.getPayload()).getTitle()))
                .route("headers['councilApproval']",
                        m -> m.subFlowMapping(false, BaseIntegrationFlowDefinition::nullChannel)
                                .subFlowMapping(true, sf -> sf.gateway(presidentChannel())))

                .channel(presidentChannel())
                .enrichHeaders(Map.of(Header.SIGNED.getValue(), new Random().nextBoolean()))
                .log(LoggingHandler.Level.INFO, "Президент: ",
                        m -> String.format((boolean) m.getHeaders().get(Header.SIGNED.getValue())
                                        ? "%s подписан"
                                        : "На законопроект \"%s\" наложено вето",
                                ((DraftLaw)m.getPayload()).getTitle()))
                .route("headers['signed']",
                        m -> m.subFlowMapping(false, BaseIntegrationFlowDefinition::nullChannel)
                                .subFlowMapping(true, sf -> sf.gateway(prepareLawChannel())))

                .channel(prepareLawChannel())
                .handle("dumaSecretariat", "prepareLaw")
                .channel("publicationChannel")
                .get()
        ;
    }

    @Bean
    public IntegrationFlow acceptModificationsFlow() {
        return f -> f
                .log(LoggingHandler.Level.INFO, "Министерство-инициатор:", m -> "Законопроект возвращен на доработку")
                .<DraftLaw, DraftLaw>transform(p -> department.acceptModifications(p))
                .channel(departmentChannel());
                //.to(mainLegislativeProcessFlow());
    }

/*

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
*/
}
