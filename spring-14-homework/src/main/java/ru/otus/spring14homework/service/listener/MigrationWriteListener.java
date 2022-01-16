package ru.otus.spring14homework.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

public class MigrationWriteListener<T> implements ItemWriteListener<T> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @Override
    public void beforeWrite(List<? extends T> items) {
        logger.info("Начало записи");
    }

    @Override
    public void afterWrite(List<? extends T> items) {
        logger.info("Конец записи");
    }

    @Override
    public void onWriteError(Exception exception, List<? extends T> items) {
        logger.info("Ошибка записи - {}", exception.getMessage());
    }
}
