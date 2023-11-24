package second.project.mungFriend.admissionApply.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import second.project.mungFriend.admissionApply.model.dto.Admission;
import second.project.mungFriend.admissionApply.model.service.FreeAdmissionService;
import second.project.mungFriend.member.model.dto.Member;

@Controller
@RequestMapping("/freeAdmission")
@SessionAttributes({"loginMember"})
public class FreeAdmissionController {
	
	@Autowired
	private FreeAdmissionService service; 


	@PostMapping("/insert")
	public String admissionInsert(  
			Admission admission, 
			@RequestParam(value ="admissionProfile", required = false) MultipartFile admissionProfile, 
			@SessionAttribute(value ="loginMember", required = false) Member loginMember,
			RedirectAttributes ra
			) throws IllegalStateException, IOException {
		
		// 파라미터 : 내용, 프로필 이미지 파일
		// 파일 저장 경로 : HttpSession
		System.out.println("admission 값 : " + admission);		
		System.out.println("프로필 : " +  admissionProfile);
		
		if(admission.getAdmSignificant() == null) {
			 admission.setAdmSignificant("특이사항 없음");
		}
		
		// 1. 로그인한 회원 번호를 얻어와 admission에 대입
		if(loginMember != null) {
			admission.setAdmMemberNo(loginMember.getMemberNo());			
		}
		
		// 무료 입소 신청 삽입
		int admissionCheck = service.admissionInsert(admission, admissionProfile);

		
		// 게시글 삽입 성공 시
		// -> 방금 삽입한 게시글의 상세 조회 페이지로 리다이렉트 
		// -> /board/{boardCode}/{boardNo}
		String message = null;
		String path = "redirect:";
		
		if(admissionCheck > 0) {
			message = "게시글이 등록 되었습니다.";
			path += "/" ;
		}else {
			message = "게시글 등록이 실패되었습니다.";
			path += "/";
		}
		
		
		ra.addFlashAttribute("message",message);
		
		return path;
	}
	
	
	
	
}
