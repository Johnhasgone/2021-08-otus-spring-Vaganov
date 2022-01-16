package ru.otus.spring14homework.service.listener;

import liquibase.pro.packaged.S;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;

public class MigrationProcessListener<T, S> implements ItemProcessListener<T, S> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public void beforeProcess(T item) {
        logger.info("Начало обработки");
    }

    @Override
    public void afterProcess(T item, S result) {
        logger.info("Конец обработки - {}", result);
    }

    @Override
    public void onProcessError(T item, Exception e) {
        logger.info("Ошибка обработки");
    }
}
