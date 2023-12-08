package second.project.mungFriend.mypage.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import second.project.mungFriend.adopt.model.dto.Reservation;

@Mapper
public interface UserReservationListMapper {

	// 예약 내역 조회
	List<Reservation> userSelectReservationList(int memberNo);

	// 예약 목록 삭제
	int userDeleteReservation(@Param("dogNo") int dogNo, @Param("memberNo")int memberNo);
	
	

}
