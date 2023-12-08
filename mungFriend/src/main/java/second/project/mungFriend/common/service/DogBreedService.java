package second.project.mungFriend.common.service;

import java.util.List;
import java.util.Map;

import second.project.mungFriend.common.dto.DogBreed;

public interface DogBreedService {

	List<DogBreed> findDogBreed(String searchInput);

	Map<String, Object> selectDogList1(String searchInput, int cp);

	

}
