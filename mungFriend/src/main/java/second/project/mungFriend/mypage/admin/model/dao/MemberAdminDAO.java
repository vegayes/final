package second.project.mungFriend.mypage.admin.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import second.project.mungFriend.member.model.dto.Member;

@Repository
public class MemberAdminDAO {
	
	@Autowired
	private MemberAdminMapper mapper;

	/** 회원리스트 조회하기
	 * @return List
	 */
	public List<Member> selectMemberList() {
		
		return mapper.selectMemberList();
	}

	/** 관리자 회원 탈퇴하기
	 * @param memberNo
	 * @return int
	 */
	public int memberDel(int memberNo) {
		
		return mapper.memberDel(memberNo);
	}

}
