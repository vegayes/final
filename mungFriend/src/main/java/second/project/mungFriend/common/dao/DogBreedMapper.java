package second.project.mungFriend.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import second.project.mungFriend.adopt.model.dto.Dog;
import second.project.mungFriend.common.dto.DogBreed;

@Mapper
public interface DogBreedMapper {

	public List<DogBreed> findDogBreed(String searchInput);

	public int getListCount(String searchInput);

	public List<Dog> selectDogList1(RowBounds rowBounds);

	
}
