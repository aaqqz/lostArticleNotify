package project.toy.api.scheduler.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.toy.api.common.Common;
import project.toy.api.domain.LostCategory;
import project.toy.api.domain.LostItem;
import project.toy.api.domain.LostStatus;
import project.toy.api.domain.MemberLostItem;
import project.toy.api.repository.LostItemRepository;
import project.toy.api.repository.MemberLostItemRepository;
import project.toy.api.scheduler.vo.LostItemVO;
import project.toy.api.scheduler.vo.SendMailVO;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static project.toy.api.domain.QLostItem.lostItem;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SchedulerService {

    private final LostItemRepository lostItemRepository;

    @Value("${publicData.lostItem.baseUrl}")
    private String baseUrl;

    private final MemberLostItemRepository memberLostItemRepository;
    private final SendMail sendMail;

    // ##### setLostItem #####
    public void setLostItem() {
        try {
            int listTotalCount = Integer.parseInt(lostItemApiCall(1, 1).getList_total_count());

            LostItemVO lostItemVO = lostItemApiCall(listTotalCount - 100, listTotalCount);

            lostItemSave(lostItemVO.getRow());
        } catch (Exception e) {
            log.error("errorMessage", e);
        }
    }

    private void lostItemSave(List<LostItemVO.row> row) {
        List<LostItem> lostItems = new ArrayList<>();
        for (LostItemVO.row apiData : row) {
            LostStatus lostStatus = Common.getLostStatus(apiData.getSTATUS());
            LostCategory lostCategory = Common.getLostCategory(apiData.getCATE());

            LostItem findLostItem = lostItemRepository.findById(apiData.getID())
                    .orElseGet(LostItem::new);

            if ("".equals(findLostItem.getId()) || findLostItem.getId() == null) {
                findLostItem.setId(apiData.getID());
            }
            findLostItem.setStatus(lostStatus);
            findLostItem.setCategory(lostCategory);
            findLostItem.setItemName(apiData.getGET_NAME());
            findLostItem.setItemDetailInfo(apiData.getGET_THING());
            findLostItem.setTakePlace(apiData.getTAKE_PLACE());
            findLostItem.setTakePosition(apiData.getGET_POSITION());
            findLostItem.setRegDate(apiData.getREG_DATE());
            findLostItem.setGetDate(apiData.getGET_DATE());

            lostItems.add(findLostItem);
        }
        lostItemRepository.saveAll(lostItems);
    }

    private LostItemVO lostItemApiCall(int startIndex, int endIndex) {
        LostItemVO lostItemVO;
        String apiCallUrl = baseUrl + startIndex + "/" + endIndex;

        try {
            URL url = new URL(apiCallUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();
            conn.disconnect();

            JsonObject jsonObject = new Gson().fromJson(sb.toString(), JsonObject.class);
            if (jsonObject.has("lostArticleInfo")) {
                lostItemVO = new Gson().fromJson(jsonObject.get("lostArticleInfo"), LostItemVO.class);
            } else {
                lostItemVO = new Gson().fromJson(jsonObject, LostItemVO.class);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lostItemVO;
    }
    // ##### setLostItem #####

    // ##### sendEmail #####
    public void matchingItemSendEmail() {
        List<MemberLostItem> memberLostItems = memberLostItemRepository.findMemberLostItemFetchJoin();
        log.info("memberLostItems={}", memberLostItems);

        List<SendMailVO> mails = memberLostItems.stream()
                .flatMap(memberLostItem -> lostItemRepository.findLostItem(memberLostItem).stream()
                        .map(item -> {
                            SendMailVO sendMailVO = new SendMailVO();
                            sendMailVO.setEmail(memberLostItem.getMember().getEmail());
                            sendMailVO.setStatus(item.getStatus());
                            sendMailVO.setCategory(item.getCategory());
                            sendMailVO.setItemName(item.getItemName());
                            sendMailVO.setItemDetailInfo(item.getItemDetailInfo());
                            sendMailVO.setTakePosition(item.getTakePosition());
                            return sendMailVO;
                        })).collect(Collectors.toList());

        log.info("mails={}", mails);
        mails.forEach(mail -> sendMail.send(mail));
        log.info("이메일이 총 {}건 발송 되었습니다.");
    }
    // ##### sendEmail #####
}
