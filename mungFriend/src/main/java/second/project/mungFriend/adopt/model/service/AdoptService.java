package second.project.mungFriend.adopt.model.service;

import java.util.List;
import java.util.Map;

import second.project.mungFriend.adopt.model.dto.Dog;

public interface AdoptService {

	// 강아지 목록 조회
	Map<String, Object> selectDogList(int cp);

	// 강아지 상세 조회
	Dog selectDogDetail(Map<String, Object> map);

	// DB이미지 파일 목록 조회
	List<String> selectImageList();
	
	// 좋아요 여부 확인
	int dogLikeCheck(Map<String, Object> map);

	// 좋아요 처리
	int like(Map<String, Integer> paraMap);

	
//	**********************************************************************************************

	
	// 게시글 삽입
	int dogRegiInsert(int memberNo);


}
