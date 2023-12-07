package second.project.mungFriend.mypage.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import second.project.mungFriend.mypage.admin.model.service.ReservationListService;

@Controller
@RequestMapping("/mypage/admin")
public class ReservationListController {
	
	@Autowired
	private ReservationListService service;
	
	// 예약내역 조회
	@GetMapping("/reservationList_admin")
	public String selectReservationList(
			Model model,
			@RequestParam(name = "query", required = false) String query) {
			  
		if(query == null) { // 검색어가 없을 때
			
			Map<String, Object> map = service.selectReservationList();
			
			model.addAttribute("map", map);
			
			System.out.println("map:::" + map);
			
		}else { // 검색어가 있을 때
			
			Map<String, Object> paramMap = new HashMap<>();
	        paramMap.put("query", query);
			
			Map<String, Object> map = service.selectReservationList(paramMap);			
			model.addAttribute("map", map);
			
			System.out.println("Searchmap:::" + map);
		}
		
		return "mypage/admin/reservationList_admin";
		
	}
	
//	
//	// 예약 내역 삭제
//	@GetMapping("/reservationList_admin/{dogNo}/delete")
//	public String Delete(
//			@PathVariable("dogNo") int dogNo,
//			
//			) {
//		
//		
//		return null;
//	}
	
	

}