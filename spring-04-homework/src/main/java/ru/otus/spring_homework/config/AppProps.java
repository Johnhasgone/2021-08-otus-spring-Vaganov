package ru.otus.spring_homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "app")
@Configuration
public class AppProps implements LocalizationProps, QuestionServiceProps, QuestionsDaoProps {
    private Resource resource;
    private int minAnswers;
    private String locale;

    @Override
    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public int getMinAnswers() {
        return minAnswers;
    }

    public void setMinAnswers(int minAnswers) {
        this.minAnswers = minAnswers;
    }

    @Override
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
