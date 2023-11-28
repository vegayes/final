package second.project.mungFriend.event.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.event.model.dto.Coupon;

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

}
