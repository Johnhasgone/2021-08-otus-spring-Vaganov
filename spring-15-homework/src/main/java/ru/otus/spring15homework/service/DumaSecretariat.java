package ru.otus.spring15homework.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.otus.spring15homework.domain.DraftLaw;
import ru.otus.spring15homework.domain.Law;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DumaSecretariat {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Message<DraftLaw> prepareForApprovalProcess(DraftLaw draftLaw) {
        return MessageBuilder
                .withPayload(draftLaw)
                .setHeader("regNumber", new Random().nextInt())
                .setHeader("approvalStartDate", LocalDate.now())
                .build();
    }

    public Law prepareLaw(DraftLaw draftLaw) {
        Law law = new Law();
        law.setRegisterNumber(new Random().nextInt(500) + "-ФЗ");
        law.setSignedDate(LocalDate.now());
        law.setTitle(draftLaw.getTitle());
        law.setText(
                law.getSignedDate().format(formatter) + " №" + law.getRegisterNumber()  + "\n\n" +
                        "Принят Государственной Думой\n\n" +
                        "Одобрен Советом Федерации\n\n" +
                        draftLaw.getText().replaceAll("\\.", "\\.\n\n") +
                        "Президент Российской Федерации\nМосква, Кремль"
        );

        return law;
    }
}
