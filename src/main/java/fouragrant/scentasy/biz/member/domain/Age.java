package fouragrant.scentasy.biz.member.domain;

import lombok.Getter;

@Getter
public enum Age {
    AGE10("10대"),
    AGE20("20대"),
    AGE30("30대"),
    AGE40("40대"),
    AGE50("50대"),
    AGE60("60대");

    private final String description;

    Age(String description) {this.description = description;}
}
