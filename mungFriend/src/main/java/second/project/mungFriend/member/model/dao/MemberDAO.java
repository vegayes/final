package second.project.mungFriend.member.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import second.project.mungFriend.member.model.dto.Member;

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

}
