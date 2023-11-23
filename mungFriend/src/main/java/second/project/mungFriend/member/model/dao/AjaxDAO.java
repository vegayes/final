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

}
