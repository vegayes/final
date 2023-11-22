package second.project.mungFriend.adopt.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import second.project.mungFriend.adopt.model.dto.Dog;

@Mapper
public interface AdoptMapper {

	// 삭제되지않은 강아지 수 조회
	public int getListCount();

	// 현재 페이지에 해당하는 부분에 대한 강아지목록 조회 
	public List<Dog> selectDogList(RowBounds rowBounds);
	
	

}
