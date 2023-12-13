package second.project.mungFriend.mypage.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import second.project.mungFriend.adopt.model.dto.Reservation;

@Mapper
public interface AdminReservationListMapper {

	// 검색기능 없는 게시글 목록 조회
	List<Reservation> selectReservationList();

	// 검색기능으로 게시글 목록 조회
	List<Reservation> selectSearchReservationList(Map<String, Object> paramMap);

	// 예약 내역 삭제
	int cancelReservation(
			@Param("reserveNo") int reserveNo, 
			@Param("memberEmail") String memberEmail);


}
