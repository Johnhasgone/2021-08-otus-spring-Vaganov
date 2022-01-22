package ru.otus.spring15homework.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.otus.spring15homework.domain.DraftLaw;
import ru.otus.spring15homework.domain.Correction;
import ru.otus.spring15homework.domain.Header;

import java.util.List;

@Service
public class ProfileCommittees {
    private static final List<String> deleteModifications = List.of("Положение, противоречащее Конституции",
            "Положение, противоречащее Федеральному закону номер 35",
            "Положение, реализация которого потребует расходов сверх утвержденного бюджета",
            "Положение, угрожающее национальной безопасности");

    public static final List<String> addModifications = List.of(
            "Меры по противодействию коррупции",
            "Положения, направленные на предотвращение ограничения конкуренции",
            "Положения, направленные на поддержку промыслов малых народов России");

    public Message<DraftLaw> checkDraftLaw(Message<DraftLaw> message) {
        Correction correction = new Correction();
        DraftLaw draftLaw = message.getPayload();
        for (String modification : deleteModifications) {
            if (draftLaw.getText().contains(modification)) {
                correction.getDeleteModifications().add(modification);
            }
        }
        for (String modification : addModifications) {
            if (!draftLaw.getText().contains(modification)) {
                correction.getAddModifications().add(modification);
            }
        }
        draftLaw.setCorrection(correction);

        return MessageBuilder
                .withPayload(draftLaw)
                .copyHeaders(message.getHeaders())
                .setHeader(Header.COMMITTEE_APPROVAL.getValue(),
                        correction.getAddModifications().isEmpty() && correction.getDeleteModifications().isEmpty())
                .build();
    }
}
