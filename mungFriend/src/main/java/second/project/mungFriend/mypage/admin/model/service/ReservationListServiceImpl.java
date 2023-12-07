package second.project.mungFriend.mypage.admin.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.mungFriend.adopt.model.dto.Reservation;
import second.project.mungFriend.mypage.admin.model.dao.ReservationListMapper;

@Service
public class ReservationListServiceImpl implements ReservationListService{
	
	@Autowired
	private ReservationListMapper mapper;

	// 검색기능 없는 게시글 목록 조회
	@Override
	public Map<String, Object> selectReservationList() {

		// 예약목록 조회
		List<Reservation> reservationList = mapper.selectReservationList();
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("reservationList", reservationList);
		
		return map;
	}

	// 검색기능으로 게시글 목록 조회
	@Override
	public Map<String, Object> selectReservationList(Map<String, Object> paramMap) {

		// 검색내용에 일치하는 예약목록 조회
		List<Reservation> reservationList = mapper.selectSearchReservationList(paramMap);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("reservationList", reservationList);
		
		return map;
	}
	
	
	

}
