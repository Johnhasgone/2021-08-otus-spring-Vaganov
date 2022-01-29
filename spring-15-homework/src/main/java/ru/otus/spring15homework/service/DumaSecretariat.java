package ru.otus.spring15homework.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.otus.spring15homework.domain.DraftLaw;
import ru.otus.spring15homework.domain.Law;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static ru.otus.spring15homework.domain.Header.COUNCIL_APPROVAL_DATE;
import static ru.otus.spring15homework.domain.Header.DUMA_APPROVAL_DATE;

@Service
public class DumaSecretariat {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Message<DraftLaw> prepareForApprovalProcess(DraftLaw draftLaw) {
        System.out.println(draftLaw.getTitle() + "\n\n" + draftLaw.getText().replaceAll("\\.", ".\n"));

        return MessageBuilder
                .withPayload(draftLaw)
                .setHeader("regNumber", new Random().nextInt())
                .setHeader("approvalStartDate", LocalDate.now())
                .build();
    }

    public Law prepareLaw(Message<DraftLaw> message) {
        DraftLaw draftLaw = message.getPayload();
        MessageHeaders headers = message.getHeaders();

        Law law = new Law();
        law.setRegisterNumber(new Random().nextInt(500) + "-ФЗ");
        law.setSignedDate(LocalDate.now());
        law.setTitle(draftLaw.getTitle());
        law.setText(
                law.getSignedDate().format(formatter) + " №" + law.getRegisterNumber()  + "\n\n" +
                        "Принят Государственной Думой " + formatter.format((LocalDate) headers.get(DUMA_APPROVAL_DATE.getValue())) + "\n" +
                        "Одобрен Советом Федерации " + formatter.format((LocalDate) headers.get(COUNCIL_APPROVAL_DATE.getValue())) + "\n\n" +
                        draftLaw.getText().replaceAll("\\.", "\\.\n\n") +
                        "\n\nПрезидент Российской Федерации\nМосква, Кремль"
        );

        return law;
    }
}
