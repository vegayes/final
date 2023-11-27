package second.project.mungFriend.mypage.admin.model.service;

import java.util.List;
import java.util.Map;

import second.project.mungFriend.member.model.dto.Member;

public interface MemberAdminService {

	
	/** 회원리스트 조회하기
	 * @param cp
	 * @return Map
	 */
	Map<String, Object> selectMemberList(int cp);

	/** 관리자 회원 탈퇴하기
	 * @param memberNo
	 * @return int
	 */
	int memberDel(int memberNo);

}
