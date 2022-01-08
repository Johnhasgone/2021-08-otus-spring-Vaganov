package ru.otus.spring14homework.service;


public interface EntityConvertService<S, R> {
    R convert(S source);
}
