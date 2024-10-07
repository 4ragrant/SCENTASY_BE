package fouragrant.scentasy.biz.member.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Scent {
    TOP_AQUAL("아쿠아"),
    TOP_BERGAMOT("베르가뭇"),
    TOP_MINT("민트"),
    TOP_LEMON("레몬"),
    TOP_GRAPEFRUIT("자몽"),
    TOP_PEACH("복숭아"),
    TOP_THYME("타임"),
    TOP_FIG("무화과"),
    TOP_BLACKCHERRY("블랙체리"),
    TOP_GREEN("그린"),
    MIDDLE_FREESIA("프리지아"),
    MIDDLE_ROSE("로즈"),
    MIDDLE_PEPPER("블랙페퍼"),
    MIDDLE_ROSEMARY("로즈마리"),
    MIDDLE_MUGUET("뮤게"),
    MIDDLE_LILYOFTHEVALLEY("릴리오브더밸리"),
    MIDDLE_MAGNOLIA("매그놀리아"),
    MIDDLE_OCEAN("오션"),
    MIDDLE_BLACKCURRANT("블랙커런트"),
    BASE_MUSK("화이트머스크"),
    BASE_VANILLA("바닐라"),
    BASE_PATCHOULI("패츌리"),
    BASE_SANDALWOOD("샌달우드"),
    BASE_ALDEHYDE("알데하이드"),
    BASE_LEATHER("레더"),
    BASE_CEDAR("시더우드"),
    BASE_BLUEMARIN("마린블루"),
    BASE_AMBER("앰버"),
    BASE_FRANKINCENSE("프랑킨세스"),
    BASE_HINOKI("히노키");

    private final String description;

    Scent(String description) {this.description = description;}

    public static final List<Scent> SCENT_MAPPING = Arrays.asList(
            AQUAL, BERGAMOT, MINT, LEMON, GRAPEFRUIT, PEACH, THYME, FIG,
            BLACKCHERRY, GREEN, FREESIA, ROSE, PEPPER, ROSEMARY, MUGUET,
            LILYOFTHEVALLEY, MAGNOLIA, OCEAN, BLACKCURRANT, MUSK, VANILLA,
            PATCHOULI, SANDALWOOD, ALDEHYDE, LEATHER, CEDAR, BLUEMARIN,
            AMBER, FRANKINCENSE, HINOKI
    );
}
