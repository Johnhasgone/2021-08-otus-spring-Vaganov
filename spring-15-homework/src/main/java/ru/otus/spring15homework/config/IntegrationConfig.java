package ru.otus.spring15homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public QueueChannel departmentChannel() {
        return MessageChannels.queue( 10 ).get();
    }

    @Bean
    public PublishSubscribeChannel presidentChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow draftLawFlow() {
        return IntegrationFlows.from( "departmentChannel" )
                .split()
                .handle( "dumaSecretariat", "cook" )
                .aggregate()
                .channel( "presidentChannel" )
                .get();
    }


}
