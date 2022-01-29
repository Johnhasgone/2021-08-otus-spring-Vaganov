package ru.otus.spring15homework.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.otus.spring15homework.domain.DraftLaw;
import ru.otus.spring15homework.domain.Header;

import java.util.Random;

@Service
public class StateDuma {
    private static final int total = 300;
    private int votedFor;
    private int votedAgainst;
    private int abstained;

    public Message<DraftLaw> vote(Message<DraftLaw> message) {
        votedFor = new Random().ints(100, 250).findAny().getAsInt();
        votedAgainst = new Random().nextInt(50);
        abstained = total - votedFor - votedAgainst;

        System.out.println("Голосование: ЗА - " + votedFor + ", ПРОТИВ - " + votedAgainst + ", ВОЗДЕРЖАЛИСЬ - " + abstained);

        return MessageBuilder
                .withPayload(message.getPayload())
                .copyHeaders(message.getHeaders())
                .setHeader(Header.DUMA_APPROVAL.getValue(), votedFor > votedAgainst + abstained)
                .build();
    }
}
