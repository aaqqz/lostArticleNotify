package project.toy.api.common;

import project.toy.api.domain.LostCategory;
import project.toy.api.domain.LostStatus;

import java.util.Map;
import java.util.Optional;

public class Common {
    public static LostStatus getLostStatus(String status) {
        Map<String, LostStatus> getStatus = Map.of("경찰서이관", LostStatus.POLICE,
                "우체국이관", LostStatus.POST,
                "보관", LostStatus.KEEP,
                "수령", LostStatus.RECEIVE);

        status = Optional.ofNullable(status).orElse("");
        return Optional.of(getStatus.get(status)).orElse(LostStatus.ETC);
    }

    public static LostCategory getLostCategory(String category) {
        Map<String, LostCategory> getCategory = Map.of("가방", LostCategory.BAG,
                "배낭", LostCategory.BACKPACK,
                "서류봉투", LostCategory.ENVELOPE,
                "쇼핑백", LostCategory.SHOPBAG,
                "옷", LostCategory.CLOTHES,
                "장난감", LostCategory.TOY,
                "지갑", LostCategory.WALLET,
                "책", LostCategory.BOOK,
                "파일", LostCategory.FILE,
                "핸드폰", LostCategory.MOBILE);

        category = Optional.ofNullable(category).orElse("");
        return Optional.ofNullable(getCategory.get(category)).orElse(LostCategory.ETC);
    }
}
