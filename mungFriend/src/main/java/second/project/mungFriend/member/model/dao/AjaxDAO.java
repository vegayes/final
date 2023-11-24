package second.project.mungFriend.member.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AjaxDAO {
	
	@Autowired
	private AjaxMapper mapper;

	/** 아이디 중복검사
	 * @param id
	 * @return int
	 */
	public int checkId(String id) {
		return mapper.checkId(id);
	}

	/** 이메일 중복검사
	 * @param email
	 * @return int
	 */
	public int checkEmail(String email) {
		return mapper.checkEmail(email);
	}

	/** 닉네임 중복검사
	 * @param nickname
	 * @return int
	 */
	public int checkNickname(String nickname) {
		return mapper.checkNickname(nickname);
	}

}
