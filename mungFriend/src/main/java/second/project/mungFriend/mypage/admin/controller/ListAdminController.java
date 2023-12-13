package second.project.mungFriend.mypage.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import second.project.mungFriend.admissionApply.model.dto.Admission;
import second.project.mungFriend.donation.model.dto.Donation;
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
			//System.out.println("출력 : " + adm);
		}
		
		model.addAttribute("admissionList", selectAdmissionList);	
		
		return "mypage/admin/admissionList_admin";
	}
	
	/** 입소신청 완료 값 가져오기
	 * @return
	 */
	@PostMapping("/admissionList/done")
	@ResponseBody
	public List<Admission> freeAdmissionDonePage() {
		
		// 1) 입소신청 내역 가져오기
		List<Admission> selectDoneAdmissionList = service.selectDoneAdmissionList();
		
		
		return selectDoneAdmissionList;
	}
	
	/** 입소신청 진행 값 가져오기
	 * @return
	 */
	@PostMapping("/admissionList/ing")
	@ResponseBody
	public List<Admission> freeAdmissionPage() {
		
		// 1) 입소신청 내역 가져오기
		List<Admission> selectAdmissionList = service.selectAdmissionList();
		
		
		return selectAdmissionList;
	}
	
	
	/** 후원 내역 페이지 이동
	 * @param model
	 * @return
	 */
	@GetMapping("/donationList")
	public String donationPage(Model model,
							@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
							@RequestParam(value="donationSearch", required = false)String donationSearch) {
		
		
		// 1) 후원 내역 가져오기
		// List<Donation> selectDonationList = service.selectDonationList();
		//Map<String, Object> selectDonationList = service.selectDonationList(cp);
		
		if(donationSearch == null) { // 검색어가 없을 때 (검색 X) 
			
			// 게시글 목록 조회 서비스 호출
			Map<String, Object> selectDonationList = service.selectDonationList(cp);
			
			// 조회 결과를 request scope에 세팅 후 forward
			model.addAttribute("map", selectDonationList);
				
		}else { // 검색어가 있을 떄 ( 검색 O) 
			//System.out.println("검색 내용 :" + donationSearch);
			 Map<String, Object> searchDonationList = service.searchDonationList(donationSearch, cp);
			
			 model.addAttribute("map", searchDonationList);
			
		}
		
		return "mypage/admin/donationList_admin";
	}

	/** 후원 검색 조회
	 * @param model
	 * @param donationSearch
	 * @return
	 
	@GetMapping("/donationSearch")
	public String donationSearch(Model model, 
			 					@RequestParam String donationSearch) {
		
		System.out.println("검색 내용 : " + donationSearch);
		
		// 1) 후원 내역 가져오기
		//List<Donation> searchDonationList = service.searchDonationList(donationSearch);
		
		//model.addAttribute("donationList", searchDonationList);	
		
		return "mypage/admin/donationList_admin";
	}
	*/

}
