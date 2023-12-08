package second.project.mungFriend.mypage.member.model.service;

import java.util.Map;

public interface UserReservationListService {

	// 예약 내역 조회
	Map<String, Object> userSelectReservationList(int memberNo);

	// 예약 목록 삭제
	int userDeleteReservation(int dogNo, int memberNo);

}
