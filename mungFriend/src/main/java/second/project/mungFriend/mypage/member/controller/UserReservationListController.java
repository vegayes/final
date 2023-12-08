package second.project.mungFriend.mypage.member.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.mypage.member.model.service.UserReservationListService;

@Controller
@RequestMapping("/mypage/member")
@SessionAttributes("{loginMember}")
public class UserReservationListController {
	
	@Autowired
	private UserReservationListService service;
	
	// 예약 내역 조회
	@GetMapping("/reservationList_user")
	public String userSelectReservationList(
			Model model,
			@SessionAttribute("loginMember") Member loginMember) {
		
		Map<String, Object> map = service.userSelectReservationList(loginMember.getMemberNo());
		
		model.addAttribute("map", map);

		System.out.println("map:::" + map);
		
		return "mypage/member/reservationList_user";
		
	}
	
	// 예약 목록 삭제
	@GetMapping("/reservationList_user/delete")
	@ResponseBody
	public int userDeleteReservation(
			@RequestParam("dogNo") int dogNo,
			@SessionAttribute("loginMember") Member loginMember) {
		
		int memberNo = loginMember.getMemberNo();
		
		int result = service.userDeleteReservation(dogNo, memberNo);

		return result;
		
	}

}
