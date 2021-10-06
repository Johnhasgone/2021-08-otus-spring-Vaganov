package ru.otus.spring_homework.service;

public interface LocalizationService {
    String localize(String message, Object ... objects);
}
