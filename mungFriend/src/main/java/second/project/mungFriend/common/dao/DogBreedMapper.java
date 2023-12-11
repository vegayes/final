package second.project.mungFriend.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import second.project.mungFriend.adopt.model.dto.Dog;
import second.project.mungFriend.common.dto.DogBreed;

/**
 * 
 */
@Mapper
public interface DogBreedMapper {

//	public List<DogBreed> findDogBreed(String searchInput);

	/** 견종 검색 값 나오기
	 * @param searchInput
	 * @return
	 */
	public int getListCount(String searchInput);

	/** 검색한 견종 나오기
	 * @param rowBounds
	 * @return
	 */
	public List<Dog> selectDogList(String searchInput, RowBounds rowBounds);

//	public List<Dog> selectDogList1(RowBounds rowBounds);

	
}
