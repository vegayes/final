package second.project.mungFriend.donation.model.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


@Service
public class PayServiceImpl implements PayService {
	
	@Service
	public class PayService {

		//---------------------환불, 결제 토큰생성
//			@Value("impKey")
//			private String impKey;
//
//			@Value("impSecretKey")
//			private String impSecret;
		
			private String impKey = "3671062186288787";
			private String impSecret = "r6GTetsqPbnI0fLkQvIO2WHufe2vrOdzI8AbvaWINf6pFnph0tFDCkAxKxtRydIzK7Kenx9lHGdVxUy1";

			public String getToken() throws Exception {

				HttpsURLConnection conn = null;
				URL url = new URL("https://api.iamport.kr/users/getToken");

				conn = (HttpsURLConnection) url.openConnection();

				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				conn.setDoOutput(true);
				JsonObject json = new JsonObject();

				json.addProperty("imp_key", impKey);
				json.addProperty("imp_secret", impSecret);
				
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
				
				bw.write(json.toString());
				bw.flush();
				bw.close();

				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

				Gson gson = new Gson();

				String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();


				String token = gson.fromJson(response, Map.class).get("access_token").toString();

				br.close();
				conn.disconnect();

				return token;
			}

			
	        //결제 정보 불러오기
			public String paymentInfo(String imp_uid, String access_token) throws IOException, ParseException {
				HttpsURLConnection conn = null;

				URL url = new URL("https://api.iamport.kr/payments/" + imp_uid);

				conn = (HttpsURLConnection) url.openConnection();

				conn.setRequestMethod("GET");
				conn.setRequestProperty("Authorization", access_token);
				conn.setDoOutput(true);

				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

				JSONParser parser = new JSONParser();

				JSONObject p = (JSONObject) parser.parse(br.readLine());
				
				String response = p.get("response").toString();
				
				p = (JSONObject) parser.parse(response);
				
				String amount = p.get("amount").toString();
				return amount;
			}
			
	}

}
