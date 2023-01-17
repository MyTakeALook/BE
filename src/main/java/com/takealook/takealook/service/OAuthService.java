package com.takealook.takealook.service;

import org.springframework.stereotype.Service;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.net.HttpURLConnection;
import java.lang.Exception.*;
import java.net.URL;
import java.io.*;

@Service
public class OAuthService {
    public String getKakaoAccessToken(String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요한 파라미터 스트림을 통해 전송
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            String sb = "grant_type=authorizztion_code" +
                    "&client_id=460104badd405de294a7c1e9520f4b19" +
                    "&redirect_uri=http://localhost:8080/oauth/kakao" +
                    "%code=" + code;
            bufferedWriter.write(sb);
            bufferedWriter.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("ResponseCode : "+ responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메시지 읽기
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = bufferedReader.readLine())!=null) {
                result.append(line);
            }
            System.out.println("Response body : "+ result);

            //Gson 라이브러리에 포함된 클래스로 JSON 파싱 객체 생성
            new JsonParser();
            JsonElement element = JsonParser.parseString(result.toString());

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_Token :" +refresh_Token);

            bufferedReader.close();
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return access_Token;
    }
    public void createKakaoUser(String token) {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            int id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            System.out.println("id : " + id);
            System.out.println("email : " + email);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
