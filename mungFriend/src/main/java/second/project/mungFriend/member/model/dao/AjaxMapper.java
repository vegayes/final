package second.project.mungFriend.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AjaxMapper {

	/** 아이디 중복검사
	 * @param id
	 * @return int
	 */
	int checkId(String id);

	/** 이메일 중복검사
	 * @param email
	 * @return int
	 */
	int checkEmail(String email);

	/** 닉네임 중복검사
	 * @param nickname
	 * @return int
	 */
	int checkNickname(String nickname);

}
