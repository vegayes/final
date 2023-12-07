package second.project.mungFriend.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.common.dto.DogBreed;

@Mapper
public interface DogBreedMapper {

	public List<DogBreed> findDogBreed(String searchInput);

	
}
