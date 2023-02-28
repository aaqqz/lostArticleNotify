package project.toy.api.domain;

import project.toy.api.scheduler.service.SendMail;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LostCategory {

    // 가방, 배낭, 서류봉투, 쇼핑백, 옷, 장난감, 지갑, 책, 파일, 핸드폰, 기타
    BAG("가방"),
    BACKPACK("배낭"),
    ENVELOPE("서류봉투"),
    SHOPBAG("쇼핑백"),
    CLOTHES("옷"),
    TOY("장난감"),
    WALLET("지갑"),
    BOOK("책"),
    FILE("파일"),
    MOBILE("핸드폰"),
    ETC("기타");

    private final String value;

    LostCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, LostCategory> LostCategoryMap =
            Stream.of(values())
                    .collect(Collectors.toMap(LostCategory::getValue, e -> e));

    public static LostCategory getKey(String value) {
        return Optional.ofNullable(LostCategoryMap.get(value)).orElse(LostCategory.ETC);
    }
}
