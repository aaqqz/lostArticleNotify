package project.toy.api.scheduler.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.toy.api.domain.LostItem;
import project.toy.api.repository.LostItemRepository;
import project.toy.api.scheduler.vo.LostItemVo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final LostItemRepository lostItemRepository;

    @Value("${publicData.lostItem.baseUrl}")
    private String baseUrl;

    
    public void setLostItem() {
        
        int listTotalCount = Integer.parseInt(lostItemApiCall(1, 1).getList_total_count());

        LostItemVo lostItemVo = lostItemApiCall(listTotalCount - 100, listTotalCount);

        lostItemSave(lostItemVo.getRow());
    }

    private void lostItemSave(List<LostItemVo.row> row) {

        for (LostItemVo.row data : row) {
            Optional<LostItem> lostItem = lostItemRepository.findById(data.getID());

            if (lostItem.isEmpty()){
//                lostItem.get();
            }

            //lostItemRepository.save(lostItem);
        }

        LostItem lostItem = LostItem.builder().build();
    }

//    private void saveLostItem(LostItemVo lostItemVo) {
//
////        lostItemVo.getRow().stream()
////                .map(v -> {
////                    if (v.getCATE().equals("가방")) {
////                        v.setCATE(LostCategory.BAG.toString());
////                    }
////                    if (v.getCATE().equals("가방")) {
////                        v.setCATE(LostCategory.BAG.toString());
////                    }
////                    return v;
////                }).collect(Collectors.toList())
//
//        for (LostItemVo.row row : lostItemVo.getRow()) {
//
//            switch (row.getCATE()) {
//                case "가방":
//                    result = lostItemBuilder(LostCategory.BAG);
//                    break;
//                case "지갑":
//                    result = lostItemBuilder(LostCategory.WALLET);
//                    break;
//            }
//
//            LostItem result = LostItem.builder()
//                    .id()
//                    .itemName()
//                    .regDate()
//                    .category(result)
//                    .build();
//
//            (result);
//            lostItemRepository.save(result);
////            lostItemType //persist
//        }
//
//
//    }
//
//    public LostItem lostItemBuilder(LostCategory lostCategory){
//        LostItem lostItem = LostItem.builder()
//                .category(lostCategory)
//                .build();
//
//        return lostItem;
//    }
//
//    public void lostItemUpdate() {
//        // api 조회
//        List<LostItemVo.row> row = lostItemApiCall(276744, 277844).getRow();
//
//        // db 조회
//        List<LostItem> lostItems = lostItemRepository.findAll();
//
//        for(int i=0; i<row.size(); i++){
//
//            LostItem lostItem = lostItems.get(i);
//            LostItemVo.row row1 = row.get(i);
//
//            LostItem build = lostItem.builder()
//                    .status(row1.setSTATUS())
//                    .itemName(row1.setCATE())
//                    .build();
//
//            lostItemRepository.(build);
//        }
//    }

    private LostItemVo lostItemApiCall(int startIndex, int endIndex) {
        LostItemVo lostItemVo;
        baseUrl = baseUrl + startIndex + "/" + endIndex;

        try {
            URL url = new URL(baseUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();
            conn.disconnect();

            JsonObject jsonObject = new Gson().fromJson(sb.toString(), JsonObject.class);
            if ( jsonObject.has("lostArticleInfo")) {
                lostItemVo = new Gson().fromJson(jsonObject.get("lostArticleInfo"), LostItemVo.class);
            } else{
                lostItemVo = new Gson().fromJson(jsonObject, LostItemVo.class);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lostItemVo;
    }
}
