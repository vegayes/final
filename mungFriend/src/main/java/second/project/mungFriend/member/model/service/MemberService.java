package second.project.mungFriend.member.model.service;

import second.project.mungFriend.member.model.dto.Member;

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

}
