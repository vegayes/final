package second.project.mungFriend.mypage.admin.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import second.project.mungFriend.member.model.dto.Member;

@Mapper
public interface MemberAdminMapper {

	/** 현재 페이지에 해당하는 부분에 대한 회원리스트 조회하기
	 * @param rowBounds
	 * @return List
	 */
	List<Member> selectMemberList(RowBounds rowBounds);

	/** 관리자 회원 탈퇴하기
	 * @param memberNo
	 * @return int
	 */
	int memberDel(int memberNo);

	/** 탈퇴하지 않은 회원 리스트 수 조회
	 * @return int
	 */
	int getListCount();

	/** 탈퇴하지 않은 회원 리스트 수 조회 (검색)
	 * @param memberSearch
	 * @return int
	 */
	int getSearchListCount(String memberSearch);

	/** 회원 리스트 조회 (검색)
	 * @param memberSearch
	 * @param rowBounds
	 * @return List
	 */
	List<Member> selectSearchMemberList(String memberSearch, RowBounds rowBounds);

}
