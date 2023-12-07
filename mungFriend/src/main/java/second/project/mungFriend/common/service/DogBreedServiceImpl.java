package second.project.mungFriend.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.common.dao.DogBreedMapper;
import second.project.mungFriend.common.dto.DogBreed;

@Service
public class DogBreedServiceImpl implements DogBreedService {

	@Autowired
	private DogBreedMapper mapper;

	@Override
	public List<DogBreed> findDogBreed(String searchInput) {
		return mapper.findDogBreed(searchInput);
	}
	
	
  
}
