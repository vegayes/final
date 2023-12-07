package second.project.mungFriend.mypage.admin.model.service;

import java.util.Map;

import second.project.mungFriend.adopt.model.dto.Reservation;

public interface ReservationListService {

	// 검색기능 없는 게시글 목록 조회
	Map<String, Object> selectReservationList();

	// 검색기능으로 게시글 목록 조회
	Map<String, Object> selectReservationList(Map<String, Object> paramMap);

}
