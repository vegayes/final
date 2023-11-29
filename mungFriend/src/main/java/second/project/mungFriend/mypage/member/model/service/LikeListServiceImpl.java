package second.project.mungFriend.mypage.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.mypage.member.model.dao.LikeListMapper;
import second.project.mungFriend.mypage.member.model.dto.DogLike;

@Service
public class LikeListServiceImpl implements LikeListService{

	@Autowired
	private LikeListMapper mapper;
//
//	@Override
//	public List<DogLike> selectLikeList() {
//		
//		return mapper.selectLikeList();
//		
//	}
//	

	@Override
	public Map<String, Object> selectLikeList() {
		
		List<DogLike> likeList = mapper.selectLikeList();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("likeList", likeList);
		
		return map;
	}
	
	
}
