package second.project.mungFriend.mypage.admin.model.service;

import java.util.List;

import second.project.mungFriend.member.model.dto.Member;

public interface MemberAdminService {

	/** 회원리스트 조회하기
	 * @return List
	 */
	List<Member> selectMemberList();

	/** 관리자 회원 탈퇴하기
	 * @param memberNo
	 * @return int
	 */
	int memberDel(int memberNo);

}
