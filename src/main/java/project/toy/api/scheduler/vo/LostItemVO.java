package project.toy.api.scheduler.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LostItemVO {

    private String list_total_count;

    private RESULT RESULT;

    private List<row> row;

    @Getter
    @Setter
    public static class RESULT {
        private String CODE;
        private String MESSAGE;
    }

    @Getter
    @Setter
    public static class row {
        private String ID;              // 분실물 SEQ
        private String STATUS;          // 분실물 상태
        private String CATE;            // 분실물 종류
        private String GET_NAME;        // 분실물명
        private String GET_THING;       // 분실물 상세내용
        private String TAKE_PLACE;      // 분실장소
        private String GET_POSITION;    // 수령위치(회사)
        private String REG_DATE;        // 등록일자 (홈페이지 등록일)
        private String GET_DATE;        // 수령일자 (습득일)

        private String TAKE_ID;         // 분실물 등록자ID
        private String GET_AREA;        // 수령자치구
        private String GET_GOOD;        // 수령물건
        private String READ_CNT;        // 조회수
    }
}
