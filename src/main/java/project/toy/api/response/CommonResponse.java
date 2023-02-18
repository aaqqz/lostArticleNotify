package project.toy.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Getter
@NoArgsConstructor
public class CommonResponse {
    private int status;
    private String msg;
    private Object body;


    @Builder
    public CommonResponse(int status, String msg, Object body) {
        this.status = status;
        this.msg = msg;
        this.body = body;
    }

    public static CommonResponse defaultCommonResponse() {

        CommonResponse common = CommonResponse.builder()
                .status(200)
                .msg("응답 성공입니다.")
                .build();
        return common;
    }

    public static CommonResponse defaultCommonResponse(String msg, Object body) {
        CommonResponse common = CommonResponse.builder()
                .status(200)
                .msg("응답 성공입니다.")
                .body(body)
                .build();
        return common;
    }
}
