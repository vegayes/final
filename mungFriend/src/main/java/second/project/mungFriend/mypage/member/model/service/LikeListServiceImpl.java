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

	// 좋아요 목록 조회
	@Override
	public Map<String, Object> selectLikeList(int memberNo) {
		
		List<DogLike> likeList = mapper.selectLikeList(memberNo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("likeList", likeList);
		
		return map;
	}

	// 좋아요 목록 삭제
	@Override
	public int likeDelete(int dogNo, int memberNo) {
		
		System.out.println("ser dogNo::"+ dogNo);
		int result = mapper.likeDelete(dogNo, memberNo);
		
		System.out.println("result::"+ result);
		
		return result;
	}
	
	
}
