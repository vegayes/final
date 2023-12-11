package second.project.mungFriend.adopt.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import second.project.mungFriend.admissionApply.model.dto.Admission;
import second.project.mungFriend.admissionApply.model.service.FreeAdmissionService;
import second.project.mungFriend.adopt.model.dto.Dog;
import second.project.mungFriend.adopt.model.dto.DogImage;
import second.project.mungFriend.adopt.model.dto.Reservation;
import second.project.mungFriend.adopt.model.service.AdoptService;
import second.project.mungFriend.common.utility.Util;
import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.mypage.admin.model.service.ListAdminServcie;

@Controller
@RequestMapping("/adopt")
@SessionAttributes("{loginMember}")
public class AdoptController {
	
	@Autowired
	private AdoptService service;
	
	@Autowired
	private ListAdminServcie admService;
	
	// 강아지 목록 조회 (일반 전체 조회)
	@GetMapping("/dogList")
	public String selectDogList(
			@RequestParam(value="cp", required= false, defaultValue="1") int cp,
			Model model) {
		
		Map<String, Object> map = service.selectDogList(cp);
		
		model.addAttribute("map", map);
		
		System.out.println("map::" + map);
		
		return "adopt/dogList";
	}

	// 강아지 목록 조회 (필터 조회)
	@PostMapping("/dogList")
	@ResponseBody
	public Map<String, Object> selectDogList(
	        @RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
	        @RequestBody Dog selectedFilters) {
		
		System.out.println("selectedFilters::" + selectedFilters);
		
	    Map<String, Object> map = service.selectDogList(cp, selectedFilters);
	    
	    System.out.println("searchMap::" + map);

	    return map;
	}
	
	
	// 강아지 상세 조회
	@GetMapping("/dogList/{dogNo}") 
	public String dogDetail(
			@PathVariable("dogNo") int dogNo,
			Model model,
			HttpSession session,
			RedirectAttributes ra,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dogNo", dogNo);
		
		Dog dog = service.selectDogDetail(map);
	
		String path = null;
		
		if(dog != null) { // 조회한 dogList가 있을 때
			
			if(loginMember != null) { // 회원인 경우
				
				map.put("memberNo", loginMember.getMemberNo());
				
				// 좋아요 여부 확인
				int result = service.dogLikeCheck(map);				
				if(result > 0) model.addAttribute("likeCheck", "on");
				
				// 강아지 예약 여부 확인
				int reserveCheck = service.reserveCheck(map);				
				if(reserveCheck > 0) model.addAttribute("reserveCheck", "on");
				
			}
			
			path = "adopt/dogDetail";
			
			System.out.println("상세조회한 개 정보 : " + dog);
			model.addAttribute("dog", dog); 
			session.setAttribute("dog", dog); 
			
			if(dog.getImageList().size() != 0) { // 강아지 이미지가 있을 경우				
				
				System.out.println("개 이미지 있음");
				DogImage thumbnail = null;
				
				if(dog.getImageList().get(0).getImageOrder() == 0) {
					
					thumbnail = dog.getImageList().get(0);
				}
				
				model.addAttribute("thumbnail", thumbnail);
				model.addAttribute("start", thumbnail != null ? 1 : 0);
								
			}
		
		}else { // 조회결과가 없을 경우
			
			path = "redirect:/adopt/dogList";
			ra.addFlashAttribute("message", "해당 게시글이 존재하지 않습니다");
			
		}
		
	
		return path;
		
	}
	
	// 좋아요 처리
	@PostMapping("/like")
	@ResponseBody // 반환되는 값이 비동기 요청한 곳으로 돌아가게 함
	public int like(@RequestBody Map<String, Integer> paraMap) {
		
		return service.like(paraMap);
	}
	
	
	
//	**********************************************************************************************
	
	// 게시글 작성 화면 전환
	@GetMapping("/dogRegistration")
	public String dogInsert() {
		
		return "adopt/dogRegistration";
	}
	
	

	/** 입소 신청 정보 가지고 게시글 작성 화면으로 전환
	 * @return
	 */
	@GetMapping("/dogRegistration/{admNo}")
	public String infoRegistration(@PathVariable("admNo") int admNo, Model model) {
		
		Admission admissionInfo = admService.selectAdmissionInfo(admNo);
		
		model.addAttribute("admissionInfo", admissionInfo);	
		
		return "adopt/dogRegistration";
	}
	

	// 강아지 insert
	@PostMapping("/dogRegistration/insert")
	public String dogRegiInsert(
			Dog dog,
			@RequestParam(value="images", required = false) List<MultipartFile> images, 
			@RequestParam(value="admFile", required = false) String admFile, 
			@RequestParam(value="admNo", required = false, defaultValue= "0") int admNo, 
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra) throws IllegalStateException, IOException {

		
		if(admFile != null) {
			String[] parts = admFile.split("/", 4);
			String imgPath = "/" + parts[1] + "/" + parts[2] + "/" ;
			String imgRename = parts[3]; 
		
		// 세 번째 '/' 이후의 부분 가져오기
		System.out.println("주소:" + imgPath);
		System.out.println("이름:" + imgRename);
		
		dog.setImgPath(imgPath);
		dog.setImgRename(imgRename);
		// ==========================================
		//   입소 신청 내역을 가져왔지만 프로필을 바꾼 경우
		if(admFile != null  && images != null) { // 입소 신청 이미지 내역을 가져왔을 때 
			
			// 0번째 요소에 업로드된 파일이 있다면
			if(images.get(0).getSize() > 0) {
				
				dog.setImgPath(null);
				dog.setImgRename(null);
				
				System.out.println("setImgPath" + dog.getImgPath());
			}
			
		}
		// ==========================================
		
		}
	
		int dogNo = service.dogRegiInsert(dog, images);
		
		String message = null;
		String path = "redirect:";
		
		if(dogNo != 0) {
			
			if(admNo > 0) {
				System.out.println("adm에서 가져온 값인경우 성공");
				int updateAdm = admService.updateAdm(admNo);
				
				if(updateAdm >0) {
					System.out.println("admNo 성공");
				}else {
					System.out.println("admNo 실패");
				}
			}
			
			
			message = "게시글 등록이 완료되었습니다.";
			path += "/adopt/dogList/" + dogNo;
			
		}else {
			message = "게시글 등록을 실패하였습니다.";
			path += "insert";
		}
		
		ra.addFlashAttribute("message", message);
		return path;
		
	}
	
	// 강아지 update 화면 전환 및 상세페이지 재조회
	@GetMapping("/dogList/{dogNo}/update")
	public String dogUpdate(
			@PathVariable("dogNo")int dogNo,
			Model model) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dogNo", dogNo);
		
		// 수정화면 띄우기용 상세조회
		Dog dog = service.selectDogDetailForUpdate(map);		
		
		model.addAttribute("dog", dog);
		
		return "adopt/dogUpdate";
		
	}
	
	// 강아지 update
	@PostMapping("/dogList/{dogNo}/update")
	public String dogUpdate(
			@PathVariable("dogNo")int dogNo,
			Dog dog,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp, // 쿼리스트링 유지
			@RequestParam(value="images", required = false) List<MultipartFile> images, // 업로드된 파일 리스트
			@RequestParam(value="deleteList", required = false) String deleteList, // 삭제할 이미지 순서
			RedirectAttributes ra // 리다이렉트 시 값 전달용
			) throws IllegalStateException, IOException {
		
		// dogNo을 커멘드 객체(dog)에 셋팅
		dog.setDogNo(dogNo);
		
		// 게시글 수정 서비스 호출
		int rowCount = service.dogUpdate(dog, images, deleteList);
		
		// 결과에 따라 분기처리
		
		String message = null;
		String path = "redirect:";
		
		if(rowCount > 0) {
			
			message = "게시글이 수정되었습니다";
			path += "/adopt/dogList/" + dogNo;
			// 경로 맞는지 확인하기
		}else {
			
			message = "게시글 수정 실패";
			path += "update";
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
	

	// 강아지 delete
	@GetMapping("/dogList/{dogNo}/delete")
	public String dogDelete(
			@PathVariable("dogNo") int dogNo,
			RedirectAttributes ra) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dogNo", dogNo);
		
		int result = service.dogDelete(map);
		
		String message = null;
		String path = "redirect:";
		
		if(result > 0) {
			
			message = "게시글이 삭제되었습니다";
			path += "/adopt/dogList";
			
			// 강아지 입양 시 예약했던 회원번호 조회
			List<Object> memberNoList = service.selectReservation(dogNo);
			System.out.println("예약한 회원번호 : " + memberNoList);
			
			if(memberNoList != null) {
				int resultResult = service.insertAlarm(memberNoList,dogNo);
				System.out.println("알림테이블 삽입결과 : " + resultResult);
			}
			
		}else {
			
			message = "게시글 삭제 실패";
			path += "/adopt/dogList/" + dogNo;
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
		
	}
	
	
//	**********************************************************************************************

	// 예약하기
	@PostMapping("/dogReservation")
	@ResponseBody
	public Map<String, Object> dogReservation(
			@RequestBody Reservation reservationData,
			@SessionAttribute("loginMember") Member loginMember,
			HttpSession session,
			RedirectAttributes ra) {
		
		// 세션에서 dog 객체 가져오기
		Dog dog = (Dog) session.getAttribute("dog");
	    
		int dogNo = dog.getDogNo();
		int memberNo = loginMember.getMemberNo();
		
		int result = service.dogReservation(reservationData, dogNo, memberNo); 
		
		Map<String, Object> response = new HashMap<>();
		response.put("result", result);
		
		return response;
		
	}	
	


}
