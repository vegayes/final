package second.project.mungFriend.event.model.service;

import java.util.List;

import second.project.mungFriend.event.model.dto.Coupon;
import second.project.mungFriend.event.model.dto.Quiz;

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

	/** 게임에 참여했는지 확인
	 * @param memberNo
	 * @return
	 */
	int eventCheck(int memberNo);

	
	/** 퀴즈 문제 뽑기(5개 랜덤)
	 * @return
	 */
	List<Quiz> chooseQuiz();

	/** 쿠폰 선택 ( 점수 ) 
	 * @param totalScore
	 * @return
	 */
	int couponSelect(int totalScore);

	/** 쿠폰 증정
	 * @param newCoupon
	 * @return
	 */
	int couponInsert(Coupon newCoupon);

	
	
}
