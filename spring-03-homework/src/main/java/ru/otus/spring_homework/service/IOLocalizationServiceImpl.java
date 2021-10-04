package ru.otus.spring_homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IOLocalizationServiceImpl {
    private final LocalizationService localizationService;
    private final InputOutputService ioService;

    public String readLine() {
        return ioService.readLine();
    }

    public void print(String str) {
        ioService.output(str);
    }

    public void printLocalized(String str, Object ... objects) {
        ioService.output(localizationService.localize(str, objects));
    }
}
