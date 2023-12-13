package second.project.mungFriend.common.service;

import java.util.List;

import second.project.mungFriend.adopt.model.dto.Dog;

public interface MainService {

	/**메인페이지 강아지 목록 조회
	 * @return
	 */
	List<Dog> selectDogList();

}
