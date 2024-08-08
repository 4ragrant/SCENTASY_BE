package fouragrant.scentasy.biz.member.domain;

import lombok.Getter;

@Getter
public enum Season {
    SPRING("봄"),
    SUMMER("여름"),
    FALL("가을"),
    WINTER("겨울");

    private final String description;

    Season(String description) {this.description = description;}
}
