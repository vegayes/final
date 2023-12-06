package second.project.mungFriend.event.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.event.model.dto.Coupon;
import second.project.mungFriend.event.model.dto.Quiz;

@Mapper
public interface EventMapper {

	/** 쿠폰 확인하기 ( 마이페이지 / 사용자 ) 
	 * @param memberNo
	 * @return
	 */
	List<Coupon> selectCouponList(int memberNo);

	/** 유효기간이 지난 쿠폰 개수 확인
	 * @param formatedNow
	 * @return
	 */
	int countExpiration(String formatedNow);

	/** 유효기간이 지난 쿠폰
	 * @param formatedNow
	 * @return
	 */
	int updateCountExpiration(String formatedNow);

	/** 게임에 참여했는지 확인
	 * @param memberNo
	 * @return
	 */
	int eventCheck(int memberNo);

	/** 랜덤하게 문제 뽑기
	 * @return
	 */
	List<Quiz> chooseQuiz();

	/** 쿠폰 선택 (점수)
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
