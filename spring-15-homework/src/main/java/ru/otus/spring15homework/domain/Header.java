package ru.otus.spring15homework.domain;

public enum Header {
    COMMITTEE_APPROVAL("committeeApproval"),
    DUMA_APPROVAL("dumaApproval"),
    DUMA_APPROVAL_DATE("dumaApprovalDate"),
    COUNCIL_APPROVAL("councilApproval"),
    COUNCIL_APPROVAL_DATE("councilApprovalDate"),
    SIGNED("signed")
    ;

    private final String value;

    Header(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
