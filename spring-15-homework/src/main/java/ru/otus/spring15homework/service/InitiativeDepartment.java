package ru.otus.spring15homework.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import ru.otus.spring15homework.domain.DraftLaw;

@Service
public class InitiativeDepartment {
    public DraftLaw createDraftLaw() {
        // TODO add proposals, measures, etc.
        return new DraftLaw();
    }

    public Message<DraftLaw> acceptModifications(Message<DraftLaw> message) {
        // TODO add addMods, delete deleteMods, maybe clean "approved" header
        return message;
        }
    }
}
