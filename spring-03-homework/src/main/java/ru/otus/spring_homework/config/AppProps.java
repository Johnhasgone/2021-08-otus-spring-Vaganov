package ru.otus.spring_homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "app")
@Configuration
public class AppProps {
    private Resources resources;
    private int minAnswers;
    private String locale;

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public int getMinAnswers() {
        return minAnswers;
    }

    public void setMinAnswers(int minAnswers) {
        this.minAnswers = minAnswers;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public static class Resources {
        Resource ru;
        Resource en;

        public Resource getRu() {
            return ru;
        }

        public void setRu(Resource ru) {
            this.ru = ru;
        }

        public Resource getEn() {
            return en;
        }

        public void setEn(Resource en) {
            this.en = en;
        }
    }
}
