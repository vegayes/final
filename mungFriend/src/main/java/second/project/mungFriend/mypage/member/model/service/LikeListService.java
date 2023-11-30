package second.project.mungFriend.mypage.member.model.service;

import java.util.List;
import java.util.Map;

import second.project.mungFriend.mypage.member.model.dto.DogLike;

public interface LikeListService {

	// 좋아요 목록 조회
	Map<String, Object> selectLikeList(int memberNo);


	// 좋아요 목록 삭제
	int likeDelete(int dogNo, int memberNo);


}
