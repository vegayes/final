package second.project.mungFriend.common.service;

import java.util.List;
import java.util.Map;

import second.project.mungFriend.adopt.model.dto.Dog;
import second.project.mungFriend.common.dto.DogBreed;

public interface DogBreedService {

//	List<DogBreed> findDogBreed(String searchInput);
	
	/** 검색
	 * @param cp
	 * @param searchInput
	 * @return
	 */
	Map<String, Object> selectDogList(int cp, String searchInput);


}
