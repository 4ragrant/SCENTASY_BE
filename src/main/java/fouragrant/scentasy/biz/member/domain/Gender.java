package fouragrant.scentasy.biz.member.domain;

public enum Gender {
    MALE("남성"),
    FEMALE("여성"),
    BOTH("중성적");

    private final String description;

    Gender(String description) {this.description = description;}
}
