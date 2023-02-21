package project.toy.api.domain;

import java.util.Arrays;

public enum LostCategory {
    // 가방, 배낭, 서류봉투, 쇼핑백, 옷, 장난감, 지갑, 책, 파일, 핸드폰, 기타
    BAG, BACKPACK, ENVELOPE, SHOPBAG, CLOTHES, TOY, WALLET, BOOK, FILE, MOBILE, ETC;

//    BAG {public String category(String value) {return "BAG";}};



//    public abstract String category (String value);


//    public static LostCategory findCategory(String value){
//        return Arrays.stream(LostCategory.values())
//                .filter(cate ->  cate.toString().equals(value))
//                .findAny().orElse(ETC);
//    }
}
