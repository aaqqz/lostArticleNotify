package project.toy.api.domain;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LostStatus {
    // 경찰서이관, 보관, 수령, 우체국이관, 기타
    POLICE("경찰서이관"),
    POST("우체국이관"),
    KEEP("보관"),
    RECEIVE("수령"),
    ETC("기타");

    private final String value;

    LostStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, LostStatus> LostStatusMap =
            Stream.of(values())
                    .collect(Collectors.toMap(LostStatus::getValue, e -> e));

    public static LostStatus getKey(String value) {
        return Optional.ofNullable(LostStatusMap.get(value)).orElse(LostStatus.ETC);
    }
}
