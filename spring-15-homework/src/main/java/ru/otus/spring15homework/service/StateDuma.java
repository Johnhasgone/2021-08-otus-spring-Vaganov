package ru.otus.spring15homework.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.otus.spring15homework.domain.DraftLaw;
import ru.otus.spring15homework.domain.Header;

import java.util.Random;

@Service
public class StateDuma {
    private int votedFor = 298;
    private int votedAgainst = 1;
    private int abstained = 1;
    public Message<DraftLaw> readFirst(Message<DraftLaw> message) {
        return MessageBuilder
                .withPayload(message.getPayload())
                .copyHeaders(message.getHeaders())
                .setHeader(Header.DUMA_APPROVAL_1.getValue(), new Random().nextBoolean())
                .build();
    }

    public Message<DraftLaw> readSecond(Message<DraftLaw> message) {
        return MessageBuilder
                .withPayload(message.getPayload())
                .copyHeaders(message.getHeaders())
                .setHeader(Header.DUMA_APPROVAL_2.getValue(), new Random().nextBoolean())
                .build();
    }

    public Message<DraftLaw> vote(Message<DraftLaw> message) {
        return MessageBuilder
                .withPayload(message.getPayload())
                .copyHeaders(message.getHeaders())
                .setHeader(Header.DUMA_VOTE_RESULT.getValue(), votedFor > votedAgainst + abstained)
                .build();
    }
}
