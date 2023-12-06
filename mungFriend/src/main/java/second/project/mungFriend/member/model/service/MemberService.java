package second.project.mungFriend.member.model.service;

import org.springframework.web.multipart.MultipartFile;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.member.model.dto.MemberKakao;
import second.project.mungFriend.member.model.dto.MemberNaver;

public interface MemberService {

	/** 로그인
	 * @param inputMember
	 * @return Member
	 */
	Member login(Member inputMember);
	
	/** 회원가입
	 * @param profileImage
	 * @param inputMember
	 * @return int
	 */
	int signUp(MultipartFile profileImage, Member inputMember) throws Exception;

	/** 아이디찾기
	 * @param inputMember
	 * @return String
	 */
	String findId(Member inputMember);
	
	/** 비밀번호찾기
	 * @param updateMember
	 * @return int
	 */
	int findPw(Member updateMember);
	
	/** 네이버 로그인
	 * @param N/A
	 * @return String
	 */
	String getNaverLogin();
	
	/** 네이버 로그인 후 인증번호로 토큰 및 회원정보 가져오기
	 * @param token(String)
	 * @return MemberNaver(VO)
	 */
	MemberNaver getNaverInfo(String code) throws Exception;
	
	/** 네이버 로그인 후 가져온 회원정보로 없으면 인서트 있으면 셀렉트  
	 * @param Member(id, email,nickname,name)
	 * @return Member(VO)
	 */
	Member loginNaver(MemberNaver naverInfo);
	
	/** 카카오톡 로그인
	 * @param N/A
	 * @return String
	 */
	String getKakaoLogin();

	/** 카카오톡 로그인 후 인증번호로 토큰 및 회원정보 가져오기
	 * @param token(String)
	 * @return MemberNaver(VO)
	 */
	MemberKakao getKakaoInfo(String code) throws Exception;

	/** 카카오 로그인 후 가져온 회원정보로 없으면 인서트 있으면 셀렉트  
	 * @param Member(id, email,nickname,name)
	 * @return Member(VO)
	 */
	Member loginKakao(MemberKakao kakaoInfo);
	
	/** 구글 로그인
	 * @param N/A
	 * @return String
	 */
	Object getGoogleUrlLogin();
	
}
