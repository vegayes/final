package second.project.mungFriend.member.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.member.model.dto.MemberKakao;
import second.project.mungFriend.member.model.dto.MemberNaver;

@Repository
public class MemberDAO {
	
	@Autowired
	private MemberMapper memberMapper;

	/** 로그인
	 * @param inputMember
	 * @return Member
	 */
	public Member login(Member inputMember) {
		
		return memberMapper.login(inputMember);
	}

	/** 아이디찾기
	 * @param inputMember
	 * @return String
	 */
	public String findId(Member inputMember) {
		
		return memberMapper.findId(inputMember);
	}
	
	/** 네이버 로그인 후 가져온 회원정보로 없으면 인서트 있으면 셀렉트  
	 * @param Member(id, email,nickname,name,mobile)
	 * @return Member(VO)
	 */
	public Member loginNaver(MemberNaver naverInfo) {
		return memberMapper.loginNaver(naverInfo);
	}
	
	/** 네이버 로그인 후 가져온 회원정보로 없으면 인서트하는 로직  
	 * @param Member(id, email,nickname,name, mobile)
	 * @return int
	 */
	public int loginNaverInsert(MemberNaver naverInfo) {
		return memberMapper.loginNaverInsert(naverInfo);
	}
	
	/** 카카오톡 로그인 후 가져온 회원정보로 없으면 인서트 있으면 셀렉트  
	 * @param Member(id, email,nickname,name,mobile)
	 * @return Member(VO)
	 */
	public Member loginKakao(MemberKakao kakaoInfo) {
		return memberMapper.loginKakao(kakaoInfo);
	}
	
	/** 네이버 로그인 후 가져온 회원정보로 없으면 인서트하는 로직  
	 * @param Member(id, email,nickname,name, mobile)
	 * @return int
	 */
	public int loginKakaoInsert(MemberKakao kakaoInfo) {
		return memberMapper.loginKakaoInsert(kakaoInfo);
	}

}
