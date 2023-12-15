package second.project.mungFriend.member.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.member.model.dto.MemberGoogle;
import second.project.mungFriend.member.model.dto.MemberKakao;
import second.project.mungFriend.member.model.dto.MemberLoginApi;
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
	
	/** 회원가입
	 * @param inputMember
	 * @return int
	 */
	public int signUp(Member inputMember) {
		return memberMapper.signUp(inputMember);
	}

	/** 아이디찾기
	 * @param inputMember
	 * @return String
	 */
	public String findId(Member inputMember) {
		
		return memberMapper.findId(inputMember);
	}
	
	/** 비밀번호찾기
	 * @param updateMember
	 * @return int
	 */
	public int findPw(Member updateMember) {
		
		return memberMapper.findPw(updateMember);
	}
	
	/** 네이버 로그인 후 가져온 회원정보로 셀렉트  
	 * @param Member(id, email,nickname,name,mobile)
	 * @return Member(VO)
	 */
	public Member loginNaver(MemberLoginApi memberLoginApi) {
		return memberMapper.loginNaver(memberLoginApi);
	}
	
	/** 카카오톡 로그인 후 가져온 회원정보로 셀렉트  
	 * @param Member(id, email,nickname,name,mobile)
	 * @return Member(VO)
	 */
	public Member loginKakao(MemberLoginApi memberLoginApi) {
		return memberMapper.loginKakao(memberLoginApi);
	}
	
	/** 구글 로그인 후 가져온 회원정보로 셀렉트  
	 * @param Member(id, email,nickname,name,mobile)
	 * @return Member(VO)
	 */
	public Member loginGoogle(MemberLoginApi memberLoginApi) {
		return memberMapper.loginGoogle(memberLoginApi);
	}
}
