package second.project.mungFriend.adopt.model.service;

import java.util.Map;

public interface AdoptService {

	// 강아지 목록 조회
	Map<String, Object> selectDogList(int cp);

}
