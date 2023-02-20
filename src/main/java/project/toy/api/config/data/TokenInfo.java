package project.toy.api.config.data;

import lombok.Data;

@Data
public class TokenInfo {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}
