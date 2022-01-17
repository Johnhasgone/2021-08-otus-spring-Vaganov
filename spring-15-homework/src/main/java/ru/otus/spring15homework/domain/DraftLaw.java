package ru.otus.spring15homework.domain;

public class DraftLaw {
    private String text;
    private boolean signed;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }
}
