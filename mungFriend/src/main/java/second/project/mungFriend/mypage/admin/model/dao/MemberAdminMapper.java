package second.project.mungFriend.mypage.admin.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.member.model.dto.Member;

@Mapper
public interface MemberAdminMapper {

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
