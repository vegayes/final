package second.project.mungFriend.member.model.service;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import second.project.mungFriend.common.dto.Alarm;
import second.project.mungFriend.common.utility.Util;
import second.project.mungFriend.member.model.dao.AlarmMapper;
import second.project.mungFriend.member.model.dao.MemberDAO;
import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.member.model.dto.MemberGoogle;
import second.project.mungFriend.member.model.dto.MemberKakao;
import second.project.mungFriend.member.model.dto.MemberNaver;

@Service
@PropertySource("classpath:/config.properties") // classpath -------> src/main/resource
public class MemberServiceImpl implements MemberService{
	
	@Value("${my.member.webpath}") // @Value --------> 필드에서만 사용 가능
	private String webPath;
	
	@Value("${my.member.location}")
	private String filePath;
	
	@Autowired
	private MemberDAO dao;
	
	@Autowired
	private AlarmMapper mapper; 
	
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
	@Value("${google.client.id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${google.client.secret}")
    private String GOOGLE_CLIENT_SECRET;
    
    @Value("${google.redirect.url}")
    private String GOOGLE_REDIRECT_URL;

    @Value("${google.auth.url}")
    private String GOOGLE_AUTH_URL;
    
    @Value("${google.login.url}")
    private String GOOGLE_LOGIN_URL;

	// 로그인
	@Override
	public Member login(Member inputMember) {
		
		Member loginMember = dao.login(inputMember);
		
		System.out.println(bcrypt.encode(inputMember.getMemberPw()));
		
		if(loginMember != null) {
			
			if(bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
				
				loginMember.setMemberPw(null);
				
			} else {
				
				loginMember = null;
				
			}
		}
		
		return loginMember;
	}
	
	// 회원가입
	@Transactional(rollbackFor = Exception.class) // 해당 메서드에서 데이터베이스 작업을 수행하는 동안 어떠한 종류의 예외라도 발생하면,
													// 그 메서드에서 이루어진 모든 데이터베이스 변경사항이 취소되도록 보장하는 것 -> 데이터의 일관성과 무결성을 유지할 수 있음!
	@Override
	public int signUp(MultipartFile profileImage, Member inputMember) throws Exception{
		
		// 비밀번호 암호화 (Bcrypt) 후 다시 inputMember 세팅
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);
		
		// 프로필 이미지 변경 실패 대비
		String temp = inputMember.getMemberProfile(); // 기존에 가지고 있던 이전 이미지 저장
		
		String rename = null; // 변경 이름 저장 변수
		
		if(profileImage.getSize() > 0) { // 업로드된 이미지가 있을 경우
			
			// 1) 파일 이름 변경
			rename = Util.fileRename(profileImage.getOriginalFilename());
			
			// 2) 바뀐 이름 inputMember에 세팅
			inputMember.setMemberProfile(webPath + rename);
			
		} else { // 업로드된 이미지가 없는 경우 (이미지 삭제 버튼)
			
			inputMember.setMemberProfile(null);
			
		}
		
		
		// 프로필 이미지 수정 dao 메서드 호출
		int result = dao.signUp(inputMember);
		
		
		if(result > 0 ) { // DB에 이미지 경로 업데이트 성공했다면
			
			// 업로드된 새 이미지가 있을 경우
			if(rename != null) {
				
				// 메모리에 임시 저장되어있는 파일을 서버에 진짜로 저장하는 것
				profileImage.transferTo(new File(filePath + rename));
				inputMember.setMemberProfile(webPath + rename);
			}
			
		} else { // 실패
			
			// 이전 이미지로 프로필 다시 세팅
			inputMember.setMemberProfile(temp);
			
		}
		
		return result;
	}

	// 아이디찾기
	@Override
	public String findId(Member inputMember) {
		
		return dao.findId(inputMember);
	}
	
	@Transactional(rollbackFor = Exception.class)
	// 비밀번호찾기
	@Override
	public int findPw(Member updateMember) {
		
		// 넘어온 새 비밀번호 암호화
		String encPw = bcrypt.encode(updateMember.getMemberPw());
		updateMember.setMemberPw(encPw);
		
		return dao.findPw(updateMember);
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
		String googleUrl = GOOGLE_LOGIN_URL + "?client_id=" + GOOGLE_CLIENT_ID + "&redirect_uri=" + GOOGLE_REDIRECT_URL
                + "&response_type=code&scope=email%20profile%20openid&access_type=offline";
		
		return googleUrl;
	}
	
	//구글 로그인 후 구글 회원 정보로 없으면 인서트 있으면 셀렉트
	@Override
	public Member loginGoogle(MemberGoogle googleInfo) {
		Member loginMember = dao.loginGoogle(googleInfo);
		
		// 구글 정보로 조회 후 로그인 데이터가 없을때..
		// 인서트를 하는데 패스워드는 없이 들어가야 된다.
		if(loginMember == null) {

			// 초기암호 설정
			String encPw = bcrypt.encode("1234");
			googleInfo.setGooglePw(encPw);
			
			int result = dao.loginGoogleInsert(googleInfo);
			
			if(result > 0) {
				loginMember = dao.loginGoogle(googleInfo);
			}
			else {
				loginMember = null;
			}
		}
		
		return loginMember;
	}
	
	
	//구글 로그인 후 구글 서버에서 토큰 및 회원정보 가져오기 
	@Override
	public MemberGoogle getGoogleInfo(String code) throws Exception {
		if (code == null) throw new Exception("Failed get authorization code");

	    String accessToken = "";
	    String refreshToken = "";
	        
	    try {
            HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-type", "application/x-www-form-urlencoded");

	        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	        params.add("grant_type"   , "authorization_code");
	        params.add("client_id"    , GOOGLE_CLIENT_ID);
	        params.add("client_secret", GOOGLE_CLIENT_SECRET);
	        params.add("code"         , code);
	        params.add("redirect_uri" , GOOGLE_REDIRECT_URL);

	        RestTemplate restTemplate = new RestTemplate();
	        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

	        ResponseEntity<String> response = restTemplate.exchange(
	        		GOOGLE_AUTH_URL+"/token",
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

        return getUserInfoWithTokenGoogle(accessToken);
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

	//구글 로그인 후 토큰을 이용한 회원정보를 가져오기 위한 메소드에 호출 상세 메소드
	private MemberGoogle getUserInfoWithTokenGoogle(String accessToken) throws Exception {
        //HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
        		GOOGLE_AUTH_URL+"/tokeninfo",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        //Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());
        //JSONObject account    = (JSONObject) jsonObj.get("kakao_account");
        //JSONObject profile    = (JSONObject) account.get("profile");

        System.out.println(jsonObj.toJSONString());
        

	    String id = String.valueOf(jsonObj.get("sub"));
	    String email = String.valueOf(jsonObj.get("email"));
	    String name = "google guest";
	    String nickName = "google guest";
	    String mobile = "010-1111-2222";

	    MemberGoogle memberGoogle = new MemberGoogle();

	    memberGoogle.setGoogleId(id);
	    memberGoogle.setGoogleEmail(email);
	    memberGoogle.setGoogleName(name);
	    memberGoogle.setGoogleNickName(nickName);
	    memberGoogle.setGoogleMobile(mobile);
	    
        return memberGoogle;
    }
	
	
	
	/**
	 * 로그인 시 알림목록 얻어오기
	 */
	@Override
	public List<Alarm> selectAlarm(int memberNo) {
		
		return mapper.selectAlarm(memberNo);
	}
	
	
	
	

}
