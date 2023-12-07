package second.project.mungFriend.common.service;

import java.util.List;

import second.project.mungFriend.common.dto.DogBreed;

public interface DogBreedService {

	List<DogBreed> findDogBreed(String searchInput);

	

}
