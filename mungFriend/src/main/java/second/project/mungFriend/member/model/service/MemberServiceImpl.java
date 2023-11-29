package second.project.mungFriend.member.model.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import second.project.mungFriend.member.model.dao.MemberDAO;
import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.member.model.dto.MemberKakao;
import second.project.mungFriend.member.model.dto.MemberNaver;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDAO dao;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	// 네이버 프로퍼티 불러오기
	@Value("${naver.client.id}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.client.secret}")
    private String NAVER_CLIENT_SECRET;
    
    @Value("${naver.redirect.url}")
    private String NAVER_REDIRECT_URL;

    @Value("${naver.auth.url}")
    private String NAVER_AUTH_URL;
    
    @Value("${naver.auth.token}")
    private String NAVER_AUTH_TOKEN;

    @Value("${naver.api.url}")
    private String NAVER_API_URL;

	// 카카오톡 프로퍼티 불러오기
    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;
    
    @Value("${kakao.auth.url}")
    private String KAKAO_AUTH_URL;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;
    
    @Value("${kakao.auth.token}")
    private String KAKAO_AUTH_TOKEN;

    @Value("${kakao.api.url}")
    private String KAKAO_API_URL;

	// 구글 프로퍼티 불러오기

	// 로그인
	@Override
	public Member login(Member inputMember) {
		
		Member loginMember = dao.login(inputMember);
		
		// System.out.println(bcrypt.encode(inputMember.getMemberPw()));
		
		if(loginMember != null) {
			
			if(bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
				
				loginMember.setMemberPw(null);
				
			} else {
				
				loginMember = null;
				
			}
		}
		
		return loginMember;
	}

	// 아이디찾기
	@Override
	public String findId(Member inputMember) {
		
		return dao.findId(inputMember);
	}

	//네이버 로그인
	@Override
	public String getNaverLogin() {
		String naverUrl = NAVER_AUTH_URL + "?client_id=" + NAVER_CLIENT_ID+ "&redirect_uri=" + NAVER_REDIRECT_URL+ "&response_type=code";
		
				
		return naverUrl;
	}

	//네이버 로그인 에이버 회원 정보로 없으면 인서트 있으면 셀렉트  
	@Override
	public Member loginNaver(MemberNaver naverInfo) {
		Member loginMember = dao.loginNaver(naverInfo);
		
		// 네이버 정보로 조회 후 로그인 데이터가 없을때..
		// 인서트를 하는데 패스워드는 없이 들어가야 된다.
		if(loginMember == null) {

			// 초기암호 설정
			String encPw = bcrypt.encode("1234");
			naverInfo.setNaverPw(encPw);
			
			int result = dao.loginNaverInsert(naverInfo);
			
			if(result > 0) {
				loginMember = dao.loginNaver(naverInfo);
			}
			else {
				loginMember = null;
			}
		}
		
		return loginMember;
	}

	//네이버 로그인 후 네이버 서버에서 토큰 및 회원정보 가져오기 
	@Override
	public MemberNaver getNaverInfo(String code) throws Exception {
		if (code == null) throw new Exception("Failed get authorization code");

	    String accessToken = "";
	    String refreshToken = "";
	        
	    try {
            HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-type", "application/x-www-form-urlencoded");

	        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	        params.add("grant_type"   , "authorization_code");
	        params.add("client_id"    , NAVER_CLIENT_ID);
	        params.add("client_secret", NAVER_CLIENT_SECRET);
	        params.add("code"         , code);
	        params.add("redirect_uri" , NAVER_REDIRECT_URL);

	        RestTemplate restTemplate = new RestTemplate();
	        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

	        ResponseEntity<String> response = restTemplate.exchange(
	        		NAVER_AUTH_TOKEN,
	                HttpMethod.POST,
	                httpEntity,
	                String.class
	        );

	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

            accessToken  = (String) jsonObj.get("access_token");
            refreshToken = (String) jsonObj.get("refresh_token");
        } catch (Exception e) {
            throw new Exception("API call failed");
        }

        return getUserInfoWithToken(accessToken);
	}

	//카카오톡 로그인
	@Override
	public String getKakaoLogin() {
		String kakaoUrl = KAKAO_AUTH_URL + "?client_id=" + KAKAO_CLIENT_ID+ "&redirect_uri=" + KAKAO_REDIRECT_URL+ "&response_type=code";
		
		return kakaoUrl;
	}
	
	//카카오 로그인 카카오 회원 정보로 없으면 인서트 있으면 셀렉트  
	@Override
	public Member loginKakao(MemberKakao kakaoInfo) {
		Member loginMember = dao.loginKakao(kakaoInfo);
		
		// 네이버 정보로 조회 후 로그인 데이터가 없을때..
		// 인서트를 하는데 패스워드는 없이 들어가야 된다.
		if(loginMember == null) {

			// 초기암호 설정
			String encPw = bcrypt.encode("1234");
			kakaoInfo.setKakaoPw(encPw);
			
			int result = dao.loginKakaoInsert(kakaoInfo);
			
			if(result > 0) {
				loginMember = dao.loginKakao(kakaoInfo);
			}
			else {
				loginMember = null;
			}
		}
		
		return loginMember;
	}

	
	//카카오 로그인 후 카카오 서버에서 토큰 및 회원정보 가져오기 
	@Override
	public MemberKakao getKakaoInfo(String code) throws Exception {
		if (code == null) throw new Exception("Failed get authorization code");

	    String accessToken = "";
	    String refreshToken = "";
	        
	    try {
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-type", "application/x-www-form-urlencoded");
	
	        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	        params.add("grant_type"   , "authorization_code");
	        params.add("client_id"    , KAKAO_CLIENT_ID);
	        params.add("code"         , code);
	        params.add("redirect_uri" , KAKAO_REDIRECT_URL);
	
	        RestTemplate restTemplate = new RestTemplate();
	        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
	
	        ResponseEntity<String> response = restTemplate.exchange(
	        		KAKAO_AUTH_TOKEN,
	                HttpMethod.POST,
	                httpEntity,
	                String.class
	        );
	
	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
	
	        accessToken  = (String) jsonObj.get("access_token");
	        refreshToken = (String) jsonObj.get("refresh_token");
	    } catch (Exception e) {
	        throw new Exception("API call failed");
	    }
	
	    return getUserInfoWithTokenKakao(accessToken);
	}

	//구글 로그인
	@Override
	public Object getGoogleUrlLogin() {
		String googleUrl = "";
		
		return googleUrl;
	}
	
	//네이버 로그인 후 토큰을 이용한 회원정보를 가져오기 위한 메소드에 호출 상세 메소드
	private MemberNaver getUserInfoWithToken(String accessToken) throws Exception {
        //HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                NAVER_API_URL,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        //Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());
        JSONObject account = (JSONObject) jsonObj.get("response");
        
        System.out.println(account.toJSONString());

        String id = String.valueOf(account.get("id"));
        String email = String.valueOf(account.get("email"));
        String name = String.valueOf(account.get("name"));
        String nickName = String.valueOf(account.get("nickname"));
        String mobile = String.valueOf(account.get("mobile"));


        MemberNaver memberNaver = new MemberNaver();

        memberNaver.setNaverId(id);
        memberNaver.setNaverEmail(email);
        memberNaver.setNaverName(name);
        memberNaver.setNaverNickName(nickName);
        memberNaver.setMobile(mobile);
        
        return memberNaver;
    }
	
	//카카오 로그인 후 토큰을 이용한 회원정보를 가져오기 위한 메소드에 호출 상세 메소드
	private MemberKakao getUserInfoWithTokenKakao(String accessToken) throws Exception {
        //HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                KAKAO_API_URL,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        //Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());
        JSONObject account    = (JSONObject) jsonObj.get("kakao_account");
        JSONObject profile    = (JSONObject) account.get("profile");

        //System.out.println(jsonObj.toJSONString());
        //System.out.println(account.toJSONString());
        
        //{"id":3202316099,"connected_at":"2023-11-29T13:31:27Z","kakao_account":{"email_needs_agreement":false,"profile_nickname_needs_agreement":false,
        //  "profile":{"is_default_image":false,"thumbnail_image_url":"http:\/\/k.kakaocdn.net\/dn\/eALxIe\/btszGLaIZfP\/N085h7vFIKLsuF3kSNkw20\/img_110x110.jpg",
        //  "nickname":"101동 금빛늑대1","profile_image_url":"http:\/\/k.kakaocdn.net\/dn\/eALxIe\/btszGLaIZfP\/N085h7vFIKLsuF3kSNkw20\/img_640x640.jpg"},"is_email_valid":true,
        //  "is_email_verified":true,"has_email":true,"email":"wnsrhdo@hanmail.net","profile_image_needs_agreement":false},
        //  "properties":{"profile_image":"http:\/\/k.kakaocdn.net\/dn\/eALxIe\/btszGLaIZfP\/N085h7vFIKLsuF3kSNkw20\/img_640x640.jpg",
        //  "nickname":"101동 금빛늑대1","thumbnail_image":"http:\/\/k.kakaocdn.net\/dn\/eALxIe\/btszGLaIZfP\/N085h7vFIKLsuF3kSNkw20\/img_110x110.jpg"}}

        String id = String.valueOf(jsonObj.get("id"));
        String email = String.valueOf(account.get("email"));
        String name = String.valueOf(profile.get("nickname"));
        String nickName = String.valueOf(profile.get("nickname"));
        String mobile = "010-1111-2222";

        MemberKakao memberKakao = new MemberKakao();

	        memberKakao.setKakaoId(id);
	        memberKakao.setKakaoEmail(email);
	        memberKakao.setKakaoName(name);
	        memberKakao.setKakaoNickName(nickName);
	        memberKakao.setKakaoMobile(mobile);
        
        return memberKakao;
    }
}
