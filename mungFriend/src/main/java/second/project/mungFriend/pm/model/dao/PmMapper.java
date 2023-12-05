package second.project.mungFriend.pm.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.pm.model.dto.Pm;

@Mapper
public interface PmMapper {

	/** 활동일지 등록하기
	 * @param pm
	 * @return int
	 */
	int activityLogRegister(Pm pm);
	
	/**활동일지 전체 리스트 수 조회
	 * @return int
	 */
	int getListCount();
	
	/** 현재 페이지에 해당하는 부분에 대한 활동일지 조회하기
	 * @param rowBounds
	 * @return List
	 */
	List<Pm> selectPmList(RowBounds rowBounds);

}
