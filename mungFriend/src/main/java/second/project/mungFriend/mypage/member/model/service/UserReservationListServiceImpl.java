package second.project.mungFriend.mypage.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.adopt.model.dto.Reservation;
import second.project.mungFriend.mypage.admin.model.dao.AdminReservationListMapper;
import second.project.mungFriend.mypage.member.model.dao.UserReservationListMapper;

@Service
public class UserReservationListServiceImpl implements UserReservationListService{

	@Autowired
	private UserReservationListMapper mapper;

	// 예약 내역 조회
	@Override
	public Map<String, Object> userSelectReservationList(int memberNo) {
		
		List<Reservation> reservationList = mapper.userSelectReservationList(memberNo);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("reservationList", reservationList);
		
		return map;
	}

	// 예약 목록 삭제
	@Override
	public int userDeleteReservation(int dogNo, int memberNo) {

		int result = mapper.userDeleteReservation(dogNo, memberNo);
		
		return result;
	}
	
	
	
}
