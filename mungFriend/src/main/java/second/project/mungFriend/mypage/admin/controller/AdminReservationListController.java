package second.project.mungFriend.mypage.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import second.project.mungFriend.adopt.model.dto.Reservation;
import second.project.mungFriend.mypage.admin.model.service.AdminReservationListService;

@Controller
@RequestMapping("/mypage/admin")
@SessionAttributes("{loginMember}")
public class AdminReservationListController {
	
	@Autowired
	private AdminReservationListService service;
	
	// 예약내역 조회
	@GetMapping("/reservationList_admin")
	public String selectReservationList(
			Model model,
			@RequestParam(name = "query", required = false) String query) {
			  
		if(query == null) { // 검색어가 없을 때
			
			Map<String, Object> map = service.selectReservationList();
			
			model.addAttribute("map", map);
			
			//System.out.println("map:::" + map);
			
		}else { // 검색어가 있을 때
			
			Map<String, Object> paramMap = new HashMap<>();
	        paramMap.put("query", query);
			
			Map<String, Object> map = service.selectReservationList(paramMap);			
			model.addAttribute("map", map);
			
			//System.out.println("Searchmap:::" + map);
		}
		
		return "mypage/admin/reservationList_admin";
		
	}
	
	// 예약 내역 삭제
	@PostMapping("/cancelReservations")
	@ResponseBody
	public ResponseEntity<Map<String, String>> reservationDelete(
	        RedirectAttributes ra,
	        @RequestBody List<Reservation> reservationData
	) {
	    String message = null;

	    try {
	        int rows = 0;

	        for (Reservation reservation : reservationData) {
	            rows = service.cancelReservation(reservation.getReserveNo(), reservation.getMemberEmail());
	        }

	        if (rows > 0) {
	            message = "예약이 정상적으로 취소 완료되었습니다.";
	            ra.addFlashAttribute("message", message);

	            Map<String, String> response = new HashMap<>();
	            response.put("result", "success");
	            response.put("message", message);
	            return ResponseEntity.ok(response);
	        }

	        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(null);
	    } catch (Exception e) {
	        message = "예약 취소를 실패하였습니다.";
	        e.printStackTrace();

	        Map<String, String> response = new HashMap<>();
	        response.put("result", "error");
	        response.put("message", message);
	        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(response);
	    }
	}

}