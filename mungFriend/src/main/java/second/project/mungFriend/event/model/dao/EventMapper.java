package second.project.mungFriend.event.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.event.model.dto.Coupon;

@Mapper
public interface EventMapper {

	List<Coupon> selectCouponList(int memberNo);

}
