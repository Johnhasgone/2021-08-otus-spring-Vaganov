package ru.otus.spring15homework.service;

import org.springframework.messaging.Message;
import ru.otus.spring15homework.domain.DraftLaw;

import java.util.Random;

public class StateDuma {
    public Message<DraftLaw> readFirst(Message<DraftLaw> message) {
        //TODO add modifications
        message.getHeaders().put("dumaApproval1", false);
        return message;
    }

    public Message<DraftLaw> readSecond(Message<DraftLaw> message) {
        //TODO add modifications
        message.getHeaders().put("dumaApproval2", false);
        return message;
    }

    public Message<DraftLaw> vote(Message<DraftLaw> message) {
        message.getHeaders().put("vote", new Random().nextBoolean());
        return message;
    }
}
