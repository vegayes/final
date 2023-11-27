package second.project.mungFriend.mypage.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.member.model.dto.Member;

@Mapper
public interface MemberUserMapper {

	/** 정보수정
	 * @param updateMember
	 * @return int
	 */
	int updateInfoUser(Member updateMember);
	
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
