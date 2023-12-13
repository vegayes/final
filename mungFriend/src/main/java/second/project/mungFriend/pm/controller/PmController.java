package second.project.mungFriend.pm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.pm.model.dto.Pm;
import second.project.mungFriend.pm.model.service.PmService;

@Controller
@RequestMapping("/pm")
@SessionAttributes({"loginMember"})
public class PmController {
	
	@Autowired
	private PmService service;
	
	// 무료입양 절차 화면 전환
	@GetMapping("/adoptProcedure")
	public String adoptProcedurePage() {
		return "pm/adoptProcedure";
	}
	
	// 활동일지 화면 전환 
	// 화면 전환 시 데이터를 가져와서 뿌려줌
	@GetMapping("/activityLog")
	public String activityLogPage(int memberNo, Model model, @RequestParam(value="cp", required = false, defaultValue = "1") int cp) {
		
//		int memberNo = 0;
//		
//		if(loginMember == null) {
//			memberNo = -1;
//		} else {
//			memberNo = loginMember.getMemberNo();			
//		}
		
		// 활동일지 조회하기(페이지네이션과 활동일지 목록을 가져와야 되기때문에 Map으로 받음 => admin 회원관리 참조)
		Map<String, Object> map = service.selectPmList(memberNo, cp);
		
		//System.out.println(map);
		
		model.addAttribute("map", map);
		
		return "pm/activityLog";
	}
	
	// 활동일지 등록하기 화면 전환
	@GetMapping("/activityLogRegister")
	public String activityLogRegisterPage() {
		return "pm/activityLogRegister_admin";
	}
	
	// 활동일지 등록하기
	@PostMapping("/activityLogRegister")
	public String activityLogRegister(@SessionAttribute("loginMember") Member loginMember,
									  @RequestParam("uploadImage") MultipartFile uploadImage, // 업로드 파일
									  String activityContent,
									  Pm pm,
									  RedirectAttributes ra,
									  @RequestParam(value="cp", required = false, defaultValue = "1") int cp,
									  Model model) throws Exception {
		
		pm.setMemberNo(loginMember.getMemberNo());
		pm.setActivityContent(activityContent);
		
		// DB 활동일지 등록(insert) 서비스 호출
		int result = service.activityLogRegister(uploadImage, pm);
		
		String message = null;
		String path = "redirect:";
		
		// 결과값으로 성공
		if(result > 0) {
			message = "활동일지 등록 성공";
			path = "pm/activityLog";
			
			int memberNo = loginMember.getMemberNo();
			
			// 활동일지 재조회하기
			Map<String, Object> map = service.selectPmList(memberNo, cp);
			model.addAttribute("map", map);
			
		} else {
			// 실패에 따른 처리
			message = "활동일지 등록 실패";
			path += "activityLogRegister";
			
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
		
	}
	
	// 활동일지 상세 조회하기
	@GetMapping("/activityLogSelectDetail")
	public String activityLogSelectDetail(int activityNo, Model model) {
		
		// System.out.println("activityNo :" + activityNo);
		Pm pm = service.activityLogSelectDetail(activityNo);
		//System.out.println("pm : " + pm);
		
		if(pm != null) { // 활동일지 상세 조회 성공
			
			//System.out.println("활동일지 상세 조회 성공");
			model.addAttribute("pm", pm);
			
		} else { // 활동일지 상세 조회 실패
			
			//System.out.println("활동일지 상세 조회 실패");
			
		}
		
		return "pm/activityLogUpdate_admin";
	}
	
	// 활동일지 수정하기
	@PostMapping("/activityLogUpdate")
	public String activityLogUpdate(@SessionAttribute("loginMember") Member loginMember,
									@RequestParam("updateImage") MultipartFile updateImage, // 수정된 파일
									Pm pm,
									RedirectAttributes ra,
									Model model) throws Exception {
		
		pm.setMemberNo(loginMember.getMemberNo());
		// System.out.println("들어옴?");
		// System.out.println(updateImage);
		// System.out.println("pm : " + pm);
		
		// DB 활동일지 수정(update) 서비스 호출
		int result = service.activityLogUpdate(updateImage, pm);
		
		String message = null;
		
		if(result > 0) { // 활동일지 수정 성공
			
			//System.out.println("활동일지 수정 성공");
			message = "활동일지 수정 성공";
			
			// 활동일지 상세 재조회하기
			Pm pm1 = service.activityLogSelectDetail(pm.getActivityNo());
			
			//System.out.println("pm1:" + pm1);
			model.addAttribute("pm", pm1);
			
		} else { // 활동일지 수정 실패
			
			System.out.println("활동일지 수정 실패");
			message = "활동일지 수정 실패";
			
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:activityLogSelectDetail?activityNo=" + pm.getActivityNo();
	}
	
	// 활동일지 삭제하기
	@GetMapping("/activityLogDelete")
	public String activityLogDelete(@SessionAttribute("loginMember") Member loginMember, int activityNo, Model model, RedirectAttributes ra) {
		
		int result = service.activityLogDelete(activityNo);
		
		String message = "";
		
		if(result > 0) { // 활동일지 삭제 성공
			
			System.out.println("활동일지 삭제 성공");
			
			message = "활동일지 삭제 성공";
			
			int memberNo = loginMember.getMemberNo();
			
			// 활동일지 재조회하기
			Map<String, Object> map = service.selectPmList(memberNo, 1);
			
			//System.out.println(map);
			
			model.addAttribute("map", map);

			ra.addFlashAttribute("message", message);
			
			return "pm/activityLog";
			
		} else { // 활동일지 삭제 실패
			
			//System.out.println("활동일지 삭제 실패");
			
			message = "활동일지 삭제 실패";
			
			ra.addFlashAttribute("message", message);
			
			return "pm/activityLog";
			
		}
	}
	
	// 좋아요 추가 버튼 관련
	@GetMapping("/insertLike")
	public String insertLike(@SessionAttribute("loginMember") Member loginMember, int activityNo, Model model) {

		// System.out.println(1);
		int loginMemberNo = loginMember.getMemberNo();
		// System.out.println(loginMemberNo);
		
		int result = service.insertLike(loginMemberNo, activityNo);
		// System.out.println(result);
		
		if(result > 0) { // 좋아요 추가 성공
			//System.out.println("좋아요 추가 성공");
			
			Map<String, Object> map = service.selectPmList(loginMember.getMemberNo(), 1);
			
			model.addAttribute("map", map);
			
		} else { // 좋아요 추가 실패
			//System.out.println("좋아요 추가 실패");
			
		}
		
		return "pm/activityLog";
		
	}
	
	// 좋아요 삭제 버튼 관련
	@GetMapping("/deleteLike")
	public String deleteLike(@SessionAttribute("loginMember") Member loginMember, int activityLikeNo, Model model) {
		
		int result = service.deleteLike(activityLikeNo);
		
		if(result > 0) { // 좋아요 삭제 성공
			//System.out.println("좋아요 삭제 성공");
			
			Map<String, Object> map = service.selectPmList(loginMember.getMemberNo(), 1);
			
			model.addAttribute("map", map);
			
		} else { // 좋아요 삭제 실패
			//System.out.println("좋아요 삭제 실패");
			
		}
		
		return "pm/activityLog";
		
	}

}
