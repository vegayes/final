package second.project.mungFriend.mypage.member.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.mypage.member.model.dto.DogLike;
import second.project.mungFriend.mypage.member.model.service.LikeListService;

@Controller
@RequestMapping("/mypage/member")
@SessionAttributes("{loginMember}")
public class LikeListController {
	
	@Autowired
	private LikeListService service;
	
	@GetMapping("/myPageLikeList")
	public String selectLikeList(
			Model model,
			@SessionAttribute("loginMember") Member loginMember) {		
		
		Map<String, Object> map = service.selectLikeList();
		
		model.addAttribute("map", map);
		
//		List<DogLike> likeList = service.selectLikeList();
//		
//		model.addAttribute("likeList", likeList);

		System.out.println("map:::" + map);
		
		return "mypage/member/myPageLikeList";
		
	}

}
