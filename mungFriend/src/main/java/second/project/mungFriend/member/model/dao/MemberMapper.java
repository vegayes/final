package second.project.mungFriend.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.member.model.dto.Member;

@Mapper
public interface MemberMapper {

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
