package second.project.mungFriend.member.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import second.project.mungFriend.common.dto.Alarm;
import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.member.model.dto.MemberGoogle;
import second.project.mungFriend.member.model.dto.MemberKakao;
import second.project.mungFriend.member.model.dto.MemberLoginApi;
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
	MemberLoginApi getNaverInfo(String code) throws Exception;
	
	/** 네이버 로그인 후 가져온 회원정보로 없으면 인서트 있으면 셀렉트  
	 * @param Member(id, email,nickname,name)
	 * @return Member(VO)
	 */
	Member loginNaver(MemberLoginApi memberLoginApi);
	
	/** 카카오톡 로그인
	 * @param N/A
	 * @return String
	 */
	String getKakaoLogin();

	/** 카카오톡 로그인 후 인증번호로 토큰 및 회원정보 가져오기
	 * @param token(String)
	 * @return MemberNaver(VO)
	 */
	MemberLoginApi getKakaoInfo(String code) throws Exception;

	/** 카카오 로그인 후 가져온 회원정보로 없으면 인서트 있으면 셀렉트  
	 * @param Member(id, email,nickname,name)
	 * @return Member(VO)
	 */
	Member loginKakao(MemberLoginApi memberLoginApi);
	
	/** 구글 로그인
	 * @param N/A
	 * @return String
	 */
	Object getGoogleUrlLogin();

	/** 구글 로그인 후 인증번호로 토큰 및 회원정보 가져오기
	 * @param code(String)
	 * @return MemberKakao(VO)
	 */
	MemberLoginApi getGoogleInfo(String code) throws Exception;

	/** 구글 로그인 후 가져온 회원정보로 없으면 인서트 있으면 셀렉트  
	 * @param code(String)
	 * @return MemberGoogle(VO)
	 */
	Member loginGoogle(MemberLoginApi memberLoginApi);

	//알림 목록 조회
	List<Alarm> selectAlarm(int memberNo);

	
}
