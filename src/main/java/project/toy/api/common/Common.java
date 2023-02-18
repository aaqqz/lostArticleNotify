package project.toy.api.common;

import project.toy.api.domain.LostCategory;
import project.toy.api.domain.LostStatus;

public class Common {
    public static LostStatus getLostStatus(String status) {
        switch (status) {
            case "경찰서이관":
                return LostStatus.POLICE;
            case "우체국이관":
                return LostStatus.POST;
            case "보관":
                return LostStatus.KEEP;
            case "수령":
                return LostStatus.RECEIVE;
            default:
                return LostStatus.ETC;
        }
    }

    public static LostCategory getLostCategory(String category) {
        switch (category) {
            case "가방":
                return LostCategory.BAG;
            case "배낭":
                return LostCategory.BACKPACK;
            case "서류봉투":
                return LostCategory.ENVELOPE;
            case "쇼핑백":
                return LostCategory.SHOPBAG;
            case "옷":
                return LostCategory.CLOTHES;
            case "장난감":
                return LostCategory.TOY;
            case "지갑":
                return LostCategory.WALLET;
            case "책":
                return LostCategory.BOOK;
            case "파일":
                return LostCategory.FILE;
            case "핸드폰":
                return LostCategory.MOBILE;
            default:
                return LostCategory.ETC;
        }
    }
}
