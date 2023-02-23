package project.toy.api.scheduler.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.toy.api.domain.LostItem;
import project.toy.api.domain.MemberLostItem;
import project.toy.api.repository.LostItemRepository;
import project.toy.api.repository.LostItemRepositoryCustom;
import project.toy.api.vo.MemberLostItemVO;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LostItemService {

    private final LostItemRepository lostItemRepository;

    public List<LostItem> findLostItem(MemberLostItem memberItem) {

        return lostItemRepository.findLostItem(memberItem);
    }
}
