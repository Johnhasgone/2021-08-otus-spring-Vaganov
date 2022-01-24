package ru.otus.spring15homework.domain;

public enum ChainElement {
    DEPARTMENT("Министерство-инициатор"),
    DUMA_SECRETARIAT("Секретариат Государственной Думы"),
    COMMITTEES("Профильные комитеты"),
    STATE_DUMA("Государственная Дума"),
    COUNCIL("Совет Федерации"),
    PRESIDENT("Президент");

    private final String value;

    ChainElement(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
