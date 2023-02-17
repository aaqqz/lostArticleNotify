package project.toy.api.scheduler.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
import java.io.UnsupportedEncodingException;
import java.net.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final LostItemRepository lostItemRepository;

    @Value("${publicData.lostItem.baseUrl}")
    private String baseUrl;

    public void lostItemUpdate() {
        LostItemVo lostItemVo = lostItemApiCall(1, 1);
        log.info("lostItemUpdate={}", lostItemVo);
        // todo start, end index logic

        saveLostItem(lostItemVo);
    }

    private void saveLostItem(LostItemVo lostItemVo) {
        // todo lostItem save
    }

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
