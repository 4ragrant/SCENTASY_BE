package fouragrant.scentasy.biz.member.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Scent {
    AQUAL("아쿠아"),
    BERGAMOT("베르가뭇"),
    MINT("민트"),
    LEMON("레몬"),
    GRAPEFRUIT("자몽"),
    PEACH("복숭아"),
    THYME("타임"),
    FIG("무화과"),
    BLACKCHERRY("블랙체리"),
    GREEN("그린"),
    FREESIA("프리지아"),
    ROSE("로즈"),
    PEPPER("블랙페퍼"),
    ROSEMARY("로즈마리"),
    MUGUET("뮤게"),
    LILYOFTHEVALLEY("릴리오브더밸리"),
    MAGNOLIA("매그놀리아"),
    OCEAN("오션"),
    BLACKCURRANT("블랙커런트"),
    MUSK("화이트머스크"),
    VANILLA("바닐라"),
    PATCHOULI("패츌리"),
    SANDALWOOD("샌달우드"),
    ALDEHYDE("알데하이드"),
    LEATHER("레더"),
    CEDAR("시더우드"),
    BLUEMARIN("마린블루"),
    AMBER("앰버"),
    FRANKINCENSE("프랑킨세스"),
    HINOKI("히노키");

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
