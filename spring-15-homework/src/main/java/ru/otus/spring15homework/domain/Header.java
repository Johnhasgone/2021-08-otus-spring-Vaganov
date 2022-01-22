package ru.otus.spring15homework.domain;

public enum Header {
    COMMITTEE_APPROVAL("committeeApproval"),
    DUMA_APPROVAL_1("dumaApproval1"),
    DUMA_APPROVAL_2("dumaApproval2"),
    DUMA_VOTE_RESULT("dumaVoteResult"),
    COUNCIL_APPROVAL("councilApproval"),
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
