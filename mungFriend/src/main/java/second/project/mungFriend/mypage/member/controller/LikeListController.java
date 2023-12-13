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
import second.project.mungFriend.mypage.member.model.service.LikeListService;

@Controller
@RequestMapping("/mypage/member")
@SessionAttributes("{loginMember}")
public class LikeListController {
	
	@Autowired
	private LikeListService service;
	
	// 좋아요 목록 조회
	@GetMapping("/myPageLikeList")
	public String selectLikeList(
			Model model,
			@SessionAttribute("loginMember") Member loginMember) {
		
		Map<String, Object> map = service.selectLikeList(loginMember.getMemberNo());
		
		model.addAttribute("map", map);

		//System.out.println("map:::" + map);
		
		return "mypage/member/myPageLikeList";
		
	}
	
	// 좋아요 목록 삭제
	@GetMapping("/myPageLikeList/delete")
	@ResponseBody
	public int likeDelete(
			@RequestParam("dogNo") int dogNo,
			@SessionAttribute("loginMember") Member loginMember) {
		
		int memberNo = loginMember.getMemberNo();
		
		//System.out.println("dogNo"+dogNo);
		//System.out.println("memberNo:::" + memberNo);
		
		int result = service.likeDelete(dogNo, memberNo);

		return result;
		
	}

}
