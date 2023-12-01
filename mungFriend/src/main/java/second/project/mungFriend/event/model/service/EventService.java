package second.project.mungFriend.event.model.service;

public interface EventService {

	/**유효기간 지난 값이 몇개인지 확인하기
	 * @param formatedNow
	 * @return
	 */
	int countExpiration(String formatedNow);

	/** 유효기간 지난 값 바꾸기
	 * @param formatedNow
	 * @return
	 */
	int updateCountExpiration(String formatedNow);
	
	
}
