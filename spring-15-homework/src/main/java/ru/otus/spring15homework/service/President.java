package ru.otus.spring15homework.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.otus.spring15homework.domain.DraftLaw;
import ru.otus.spring15homework.domain.Header;

import java.util.Random;

@Service
public class President {
    public Message<DraftLaw> sign(Message<DraftLaw> message) {
        return MessageBuilder
                .fromMessage(message)
                .setHeader(Header.SIGNED.getValue(), new Random().nextBoolean())
                .build();
    }
}
