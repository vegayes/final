package second.project.mungFriend.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.adopt.model.dto.Dog;

@Mapper
public interface MainMapper {

	/** 메인페이지 강아지 조회
	 * @return
	 */
	List<Dog> selectDogList();

}
