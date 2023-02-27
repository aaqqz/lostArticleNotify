package project.toy.api.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DomainTest {

    @Test
    @DisplayName("LostCategory 값 찾기")
    void getLostCategory() {
        // lostStatus Enum
        LostStatus test1 = LostStatus.RECEIVE;
        assertThat(LostStatus.RECEIVE).isEqualTo(test1);
        System.out.println("lostStatus Enum = " + test1);

        // lostStatus key
        LostStatus test2 = LostStatus.getKey("수령");
        assertThat(LostStatus.RECEIVE).isEqualTo(test2);
        System.out.println("lostStatus key = " + test2);

        // lostStatus value
        String test3 = LostStatus.RECEIVE.getValue();
        assertThat("수령").isEqualTo(test3);
        System.out.println("lostStatus value = " + test3);

        // lostStatus key parameter null || ''
        LostStatus test4 = LostStatus.getKey(null);
        assertThat(LostStatus.ETC).isEqualTo(test4);
        System.out.println("lostStatus key = " + test4);

        LostStatus test5 = LostStatus.getKey("");
        assertThat(LostStatus.ETC).isEqualTo(test5);
        System.out.println("lostStatus key = " + test5);
    }

}
