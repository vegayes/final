package second.project.mungFriend.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AjaxMapper {

	/** 아이디 중복검사
	 * @param id
	 * @return int
	 */
	int checkId(String id);

}
