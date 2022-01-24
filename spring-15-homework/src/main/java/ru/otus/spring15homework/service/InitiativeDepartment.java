package ru.otus.spring15homework.service;

import org.springframework.stereotype.Service;
import ru.otus.spring15homework.domain.DraftLaw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class InitiativeDepartment {
    private static final List<String> proposals = List.of(
            "Положение, противоречащее Конституции.",
            "Положение, противоречащее Федеральному закону номер 35.",
            "Положение, реализация которого потребует расходов сверх утвержденного бюджета.",
            "Положение, угрожающее национальной безопасности.",
            "Общие положения.",
            "Положения, регламентирующие применение Федерального закона.",
            "Положения, расширяющие права и свободы граждан.",
            "Положения, облегчающие ведение бизнеса."
    );

    private static final List<String> titles = List.of(
            "Федеральный закон \"О малом и среднем бизнесе\"",
            "Федеральный закон \"О малой гидроэлектроэнергетике\"",
            "Федеральный закон \"О производстве мягких игрушек в особых экономических зонах\""
    );


    public DraftLaw createDraftLaw() {
        DraftLaw draftLaw = new DraftLaw();
        draftLaw.setText(getRandomString(proposals) +
                getRandomString(proposals) +
                getRandomString(proposals) +
                getRandomString(proposals));
        draftLaw.setTitle(getRandomString(titles));

        return draftLaw;
    }

    public DraftLaw acceptModifications(DraftLaw draftLaw) {
        List<String> addModifications = draftLaw.getCorrection().getAddModifications();
        List<String> deleteModifications = draftLaw.getCorrection().getDeleteModifications();

        System.out.println("Нужны доработки:\nДобавить: \n" + String.join("\n", addModifications) +
                        "\nУдалить: \n" + String.join("\n", deleteModifications)
                );


        if (!addModifications.isEmpty()) {
            draftLaw.setText(draftLaw.getText() + String.join(".", addModifications));
        }

        if (!deleteModifications.isEmpty()) {
            List<String> currentText = new ArrayList<>(List.of(draftLaw.getText().split("\\.")));
            currentText.removeAll(deleteModifications);
            draftLaw.setText(String.join(".", currentText));
        }
        return draftLaw;
    }

    private String getRandomString(List<String> list) {
        return list.get(new Random().nextInt(list.size()));
    }
}

