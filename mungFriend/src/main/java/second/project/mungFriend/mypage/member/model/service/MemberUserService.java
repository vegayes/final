package second.project.mungFriend.mypage.member.model.service;

import second.project.mungFriend.member.model.dto.Member;

public interface MemberUserService {

	/** 정보수정
	 * @param updateMember
	 * @return int
	 */
	int updateInfoUser(Member updateMember);
	
	/** 회원탈퇴
	 * @param memberPw
	 * @param memberNo
	 * @return int
	 */
	int secessionUser(String memberPw, int memberNo);


}
