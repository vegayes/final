package second.project.mungFriend.pm.model.dao;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.pm.model.dto.Pm;

@Mapper
public interface PmMapper {

	/** 활동일지 등록하기
	 * @param pm
	 * @return int
	 */
	int activityLogRegister(Pm pm);

}
