package fouragrant.scentasy.biz.perfume.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Scent {
    TOP_BERGAMOT("베르가뭇"),
    TOP_MINT("민트"),
    TOP_LEMON("레몬"),
    TOP_AQUA("아쿠아"),
    TOP_GRAPEFRUIT("자몽"),
    TOP_PEACH("복숭아"),
    TOP_FIG("무화과"),
    TOP_BLACKCHERRY("블랙체리"),
    TOP_GREEN("그린"),
    MIDDLE_FREESIA("프리지아"),
    MIDDLE_ROSE("로즈"),
    MIDDLE_PEPPER("블랙페퍼"),
    MIDDLE_ROSEMARY("로즈마리"),
    MIDDLE_MUGUET("뮤게"),
    MIDDLE_MAGNOLIA("매그놀리아"),
    MIDDLE_OCEAN("오션"),
    MIDDLE_BLACKCURRANT("블랙커런트"),
    BASE_MUSK("화이트머스크"),
    BASE_VANILLA("바닐라"),
    BASE_SANDALWOOD("샌달우드"),
    BASE_LEATHER("레더"),
    BASE_PATCHOULI("패츌리"),
    BASE_CEDAR("시더우드"),
    BASE_AMBER("앰버"),
    BASE_ALDEHYDE("알데하이드"),
    BASE_FRANKINCENSE("프랑킨세스"),
    BASE_HINOKI("히노키"),
    BASE_BLUEMARIN("마린블루"),
    TOP_THYME("타임"),
    MIDDLE_LILYOFTHEVALLEY("릴리오브더밸리");

    private final String description;

    Scent(String description) {this.description = description;}

//    public static final List<Scent> SCENT_MAPPING = Arrays.asList(TOP_AQUA, TOP_BERGAMOT, TOP_MINT, TOP_LEMON, TOP_GRAPEFRUIT, TOP_PEACH, TOP_THYME, TOP_FIG, TOP_BLACKCHERRY, TOP_GREEN,
//            MIDDLE_FREESIA, MIDDLE_ROSE, MIDDLE_PEPPER, MIDDLE_ROSEMARY, MIDDLE_MUGUET, MIDDLE_LILYOFTHEVALLEY, MIDDLE_MAGNOLIA, MIDDLE_OCEAN, MIDDLE_BLACKCURRANT,
//            BASE_MUSK, BASE_VANILLA, BASE_PATCHOULI, BASE_SANDALWOOD, BASE_ALDEHYDE, BASE_LEATHER, BASE_CEDAR, BASE_BLUEMARIN, BASE_AMBER, BASE_FRANKINCENSE, BASE_HINOKI
//    );
}
