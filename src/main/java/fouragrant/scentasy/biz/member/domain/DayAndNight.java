package fouragrant.scentasy.biz.member.domain;

import lombok.Getter;

@Getter
public enum DayAndNight {
    DAY("오전"),
    NIGHT("오후");

    private final String description;

    DayAndNight(String description) {this.description = description;}
}
