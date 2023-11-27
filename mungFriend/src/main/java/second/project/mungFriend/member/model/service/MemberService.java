package second.project.mungFriend.member.model.service;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.member.model.dto.MemberNaver;

public interface MemberService {

	/** 로그인
	 * @param inputMember
	 * @return Member
	 */
	Member login(Member inputMember);

	/** 아이디찾기
	 * @param inputMember
	 * @return String
	 */
	String findId(Member inputMember);
	
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
	Object getKakaoLogin();
	
	/** 구글 로그인
	 * @param N/A
	 * @return String
	 */
	Object getGoogleUrlLogin();
}
