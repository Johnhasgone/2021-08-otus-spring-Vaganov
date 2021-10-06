package ru.otus.spring_homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring_homework.config.LocalizationProps;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {
    private final MessageSource messageSource;
    private final LocalizationProps config;


    @Override
    public String localize(String message, Object... objects) {
        return messageSource.getMessage(message, objects, Locale.forLanguageTag(config.getLocale()));
    }
}
