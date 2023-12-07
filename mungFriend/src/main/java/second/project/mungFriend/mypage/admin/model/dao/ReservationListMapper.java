package second.project.mungFriend.mypage.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import second.project.mungFriend.adopt.model.dto.Reservation;

@Mapper
public interface ReservationListMapper {

	// 검색기능 없는 게시글 목록 조회
	List<Reservation> selectReservationList();

	// 검색기능으로 게시글 목록 조회
	List<Reservation> selectSearchReservationList(Map<String, Object> paramMap);
	
	

}
