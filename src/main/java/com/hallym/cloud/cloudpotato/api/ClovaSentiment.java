package com.hallym.cloud.cloudpotato.api;
//import com.hallym.cloud.cloudpotato.dto.bookdetail.ClovaApiKey;
import com.hallym.cloud.cloudpotato.dto.Result;
import com.hallym.cloud.cloudpotato.dto.bookdetail.ClovaApiKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
public class ClovaSentiment {
    ClovaApiKey clovaApiKey = new ClovaApiKey();

    @GetMapping("/sentiment")
    public String sentiment(@RequestParam("content") String reviewContent) {
        try {
            String apiURL = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clovaApiKey.getClientId());
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clovaApiKey.getClientSecret());
            con.setRequestProperty("Content-Type", "application/json");

            String postParams = "{\"content\":\"" + reviewContent + "\"}";;
            StringBuilder response = new StringBuilder();

            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os = con.getOutputStream();
            byte[] postData = postParams.getBytes(StandardCharsets.UTF_8);
            os.write(postData);
            os.flush();
            os.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            System.out.println(response.toString());
            return response.toString();
        } catch(Exception e) {
            System.out.println(e);
        }
        return "sentiment is null";
    }
}
