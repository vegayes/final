package second.project.mungFriend.mypage.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import second.project.mungFriend.admissionApply.model.dto.Admission;
import second.project.mungFriend.mypage.admin.model.service.ListAdminServcie;

@Controller
@RequestMapping("/mypage/admin")	
public class ListAdminController {
	
	@Autowired
	private ListAdminServcie service;
	
	
	/** 무료입소신청 내역 페이지 이동
	 * @return
	 */
	@GetMapping("/admissionList")
	public String freeAdmissionPage(Model model) {
		
		// 1) 입소신청 내역 가져오기
		List<Admission> selectAdmissionList = service.selectAdmissionList();
		
		for(Admission adm : selectAdmissionList) {
			System.out.println("출력 : " + adm);
		}
		
		model.addAttribute("admissionList", selectAdmissionList);	
		
		return "mypage/admin/admissionList_admin";
	}
	
	

}
