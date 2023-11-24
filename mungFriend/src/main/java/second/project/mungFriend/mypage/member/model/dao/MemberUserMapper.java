package second.project.mungFriend.mypage.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberUserMapper {

	/** 회원 비밀번호 조회
	 * @param memberNo
	 * @return string
	 */
	String selectEncPw(int memberNo);

	/** 회원탈퇴
	 * @param memberNo
	 * @return int
	 */
	int secessionUser(int memberNo);

}
