package ru.otus.spring15homework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;
import ru.otus.spring15homework.domain.DraftLaw;
import ru.otus.spring15homework.service.*;

import java.time.LocalDate;
import java.util.Map;

import static ru.otus.spring15homework.domain.ChainElement.*;
import static ru.otus.spring15homework.domain.Header.*;

@Configuration
@EnableIntegration
public class IntegrationConfig {
    @Autowired
    private InitiativeDepartment department;

    @Autowired
    private StateDuma stateDuma;

    @Autowired
    private DumaSecretariat dumaSecretariat;

    @Autowired
    private ProfileCommittees profileCommittees;

    @Autowired
    private FederalCouncil federalCouncil;

    @Autowired
    private President president;


    @Bean
    public DirectChannel departmentChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public PublishSubscribeChannel publicationChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow mainLegislativeProcessFlow() {
        return IntegrationFlows
                .from(departmentChannel())
                .<DraftLaw>log(LoggingHandler.Level.WARN, DUMA_SECRETARIAT.getValue(),
                        m -> "Поступил на согласование проект: " + m.getPayload().getTitle()
                )

                .handle(dumaSecretariat, "prepareForApprovalProcess")
                .<DraftLaw>log(LoggingHandler.Level.WARN, DUMA_SECRETARIAT.getValue(),
                        m -> m.getPayload().getTitle() + " передан на согласование в профильные комитеты"
                )

                .handle(profileCommittees, "checkDraftLaw")
                .route("headers['" + COMMITTEE_APPROVAL.getValue() + "']",
                        message -> message
                                .subFlowMapping(false, sf -> sf
                                        .<DraftLaw>log(LoggingHandler.Level.WARN, COMMITTEES.getValue(),
                                                m -> String.format("Законопроект \"%s\" возвращен на доработку", m.getPayload().getTitle())
                                        )
                                        .gateway(acceptModificationsFlow())
                                )
                )
                .<DraftLaw>log(LoggingHandler.Level.WARN, COMMITTEES.getValue(),
                        m -> m.getPayload().getTitle() + " передан на рассмотрение в Государственную Думу")

                .handle(stateDuma, "vote")
                .route("headers['" + DUMA_APPROVAL.getValue() + "']",
                        message -> message
                                .subFlowMapping(false, sf -> sf
                                        .<DraftLaw>log(LoggingHandler.Level.WARN, STATE_DUMA.getValue(),
                                                m -> m.getPayload().getTitle() + " не одобрен Государственной Думой")
                                        .nullChannel()
                                )
                                .subFlowMapping(true, sf -> sf
                                        .<DraftLaw>log(LoggingHandler.Level.WARN, STATE_DUMA.getValue(),
                                                m -> m.getPayload().getTitle() + " одобрен Государственной Думой и передан в Совет Федерации")
                                        .enrichHeaders(Map.of(DUMA_APPROVAL_DATE.getValue(), LocalDate.now()))
                                )
                )

                .handle(federalCouncil, "approve")
                .route("headers['" + COUNCIL_APPROVAL.getValue() + "']",
                        message -> message
                                .subFlowMapping(false, sf -> sf
                                        .<DraftLaw>log(LoggingHandler.Level.WARN, COUNCIL.getValue(),
                                                m -> m.getPayload().getTitle() + " не одобрен Советом Федерации - наложено вето")
                                        .nullChannel()
                                )
                                .subFlowMapping(true, sf -> sf
                                        .<DraftLaw>log(LoggingHandler.Level.WARN, COUNCIL.getValue(),
                                                m -> m.getPayload().getTitle() + " одобрен Советом Федерации")
                                        .enrichHeaders(Map.of(COUNCIL_APPROVAL_DATE.getValue(), LocalDate.now()))
                                )
                )

                .handle(president, "sign")
                .route("headers['" + SIGNED.getValue() + "']",
                        message -> message
                                .subFlowMapping(false, sf -> sf
                                        .<DraftLaw>log(LoggingHandler.Level.WARN, PRESIDENT.getValue(),
                                                m -> m.getPayload().getTitle() + " не подписан - наложено вето")
                                        .nullChannel()
                                )
                                .subFlowMapping(true, sf -> sf
                                        .logAndReply(LoggingHandler.Level.WARN, PRESIDENT.getValue(),
                                                m -> ((DraftLaw) m.getPayload()).getTitle() + " подписан")
                                )
                )

                .handle(dumaSecretariat, "prepareLaw")
                .channel(publicationChannel())
                .get()
        ;
    }

    @Bean
    public IntegrationFlow acceptModificationsFlow() {
        return f -> f
                .<DraftLaw, DraftLaw>transform(p -> department.acceptModifications(p))
                .logAndReply(LoggingHandler.Level.WARN, DEPARTMENT.getValue(),
                        m -> ((DraftLaw) m.getPayload()).getTitle() +" доработан"
                );
    }
}
