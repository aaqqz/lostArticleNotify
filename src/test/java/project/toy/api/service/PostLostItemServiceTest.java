package project.toy.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.toy.api.domain.MemberLostItem;

@SpringBootTest
public class PostLostItemServiceTest {

    @Autowired
    private MemberLostItemService memberLostItemService;

    @Test
    @DisplayName("유저 분실물 등록")
    public void saveMemberLostITem() {
        //given
        MemberLostItem.builder()

                .build();
        //when

        //then
    }
}
