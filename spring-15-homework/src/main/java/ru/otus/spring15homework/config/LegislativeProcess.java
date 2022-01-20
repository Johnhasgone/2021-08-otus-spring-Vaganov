package ru.otus.spring15homework.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring15homework.domain.DraftLaw;
import ru.otus.spring15homework.domain.Law;

@MessagingGateway
public interface LegislativeProcess {

    @Gateway(requestChannel = "departmentChannel", replyChannel = "presidentChannel")
    Law process(DraftLaw draftLaw);
}
