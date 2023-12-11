package second.project.mungFriend.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.member.model.dto.MemberGoogle;
import second.project.mungFriend.member.model.dto.MemberKakao;
import second.project.mungFriend.member.model.dto.MemberNaver;

@Mapper
public interface MemberMapper {

	/** 로그인
	 * @param inputMember
	 * @return Member
	 */
	Member login(Member inputMember);
	
	/** 회원가입
	 * @param inputMember
	 * @return int
	 */
	int signUp(Member inputMember);

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

	/** 네이버 로그인 후 가져온 회원정보로 셀렉트  
	 * @param Member(id, email,nickname,name)
	 * @return Member(VO)
	 */
	Member loginNaver(MemberNaver naverInfo);

	/** 네이버 로그인 후 가져온 회원정보로 없으면 인서트하는 로직  
	 * @param naverInfo
	 * @return int
	 */
	int loginNaverInsert(MemberNaver naverInfo);
	

	/** 카카오 로그인 후 가져온 회원정보로 셀렉트  
	 * @param Member(id, email,nickname,name)
	 * @return Member(VO)
	 */
	Member loginKakao(MemberKakao kakaoInfo);

	/** 카카오 로그인 후 가져온 회원정보로 없으면 인서트하는 로직  
	 * @param kakaoInfo
	 * @return int
	 */
	int loginKakaoInsert(MemberKakao kakaoInfo);

	/** 구글 로그인 후 가져온 회원정보로 셀렉트  
	 * @param Member(id, email,nickname,name)
	 * @return Member(VO)
	 */
	Member loginGoogle(MemberGoogle googleInfo);

	/** 구글 로그인 후 가져온 회원정보로 없으면 인서트하는 로직  
	 * @param googleInfo
	 * @return int
	 */
	int loginGoogleInsert(MemberGoogle googleInfo);

}
