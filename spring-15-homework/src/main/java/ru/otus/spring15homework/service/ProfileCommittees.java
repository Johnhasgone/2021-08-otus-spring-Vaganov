package ru.otus.spring15homework.service;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import ru.otus.spring15homework.domain.DraftLaw;
import ru.otus.spring15homework.domain.Modification;

import java.util.List;

@Service
public class ProfileCommittees {
    List<String> deleteSentences = List.of("неэкономичное предложение", "меры, требующее высоких расходов");

    public Message<DraftLaw> checkEconomicIssues(Message<DraftLaw> message) {
        Modification modification = new Modification();
        DraftLaw draftLaw = message.getPayload();
        for (String sentence : deleteSentences) {
            if (draftLaw.getText().contains(sentence)) {
                modification.getDeleteModifications().add(sentence);
            }
        }
        if (!modification.getDeleteModifications().isEmpty()) {
            draftLaw.getModifications().add(modification);
            message.getHeaders().put("committeeApproval", false);
        }

        return message;
    }

    // TODO copy this to other committees, add or delete addModifications
}
