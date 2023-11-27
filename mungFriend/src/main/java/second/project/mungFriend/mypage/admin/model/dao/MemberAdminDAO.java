package second.project.mungFriend.mypage.admin.model.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import second.project.mungFriend.member.model.dto.Member;

@Repository
public class MemberAdminDAO {
	
	@Autowired
	private MemberAdminMapper mapper;

	/** 현재 페이지에 해당하는 부분에 대한 회원리스트 조회하기
	 * @param rowBounds
	 * @return List
	 */
	public List<Member> selectMemberList(RowBounds rowBounds) {
		
		return mapper.selectMemberList(rowBounds);
	}

	/** 관리자 회원 탈퇴하기
	 * @param memberNo
	 * @return int
	 */
	public int memberDel(int memberNo) {
		
		return mapper.memberDel(memberNo);
	}

	/** 탈퇴하지 않은 회원 리스트 수 조회
	 * @return int
	 */
	public int getListCount() {
		
		return mapper.getListCount();
	}

}
