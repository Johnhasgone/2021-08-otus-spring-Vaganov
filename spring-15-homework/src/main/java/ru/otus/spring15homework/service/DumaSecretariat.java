package ru.otus.spring15homework.service;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import ru.otus.spring15homework.domain.DraftLaw;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

@Service
public class DumaSecretariat {
    public Message<DraftLaw> prepareForApprovalProcess(Message<DraftLaw> message) {
        message.getHeaders().put("regNumber", new Random().nextInt());
        message.getHeaders().put("approvalStartDate", LocalDate.now());
        //message.getHeaders().put("committeeApproval", false);
        message.getPayload().setModifications(new ArrayList<>()); // приложен бланк для замечаний
        return message;
    }
}
