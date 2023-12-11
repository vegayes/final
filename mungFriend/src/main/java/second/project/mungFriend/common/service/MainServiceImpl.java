package second.project.mungFriend.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.adopt.model.dto.Dog;
import second.project.mungFriend.common.dao.MainMapper;

@Service
public class MainServiceImpl implements MainService {
	
	@Autowired
	private MainMapper mapper;

	@Override
	public List<Dog> selectDogList() {
		return mapper.selectDogList();
	}

}
