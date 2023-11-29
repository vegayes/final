package second.project.mungFriend.common.scheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import second.project.mungFriend.event.model.service.EventService;

// 스프링이 일정 시간마다 해당 객체를 이용해서 코드를 수행
// == 스프링이 해당 클래스를 객체로 만들어서 관리를 해야 함. 
// == Bean 등록 

// @Controller, @Service, @Repository의 부모 어노테이션
@Component // Bean 등록을 하겠다고 명시하는 어노테이션
public class expirationScheduling {
	
	@Autowired
	private EventService service;

	
	//cron = "초 분 시 일 월 요일 [년도]" - 요일 : 1(SUN) ~ 7(SAT)
	@Scheduled(cron = "0,30 * * * * *") // 매 분 0초, 30초 마다 수행
//	@Scheduled(cron = "0 0 * * * *") // 매 정시 (*시 0분 0초)
	public void couponExpiration() { 
			
		//System.out.println("=======DB 스케쥴링 진행 ==========");
        // 현재 날짜/시간
        Date now = new Date();
       
        // 현재 날짜/시간 출력
        //System.out.println("현재 시간 :" + now); // Thu May 03 14:43:32 KST 2022
        
        // 포맷팅 정의
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        // 포맷팅 적용
        String formatedNow = formatter.format(now);
        
        // 포맷팅 현재 날짜/시간 출력
        //System.out.println("포맷팅 시간 :" +formatedNow); // 2022년 05월 03일 14시 43분 32초
		
		// 1) DB에서 유효기간 지나면 update진행해야하는 사항이 있는지 파악
		int updateCount = service.countExpiration(formatedNow);
		
		//System.err.println("updateCount : " + updateCount);
		
		if(updateCount > 0) { // DB에서 유효기간이 지난 쿠폰
			int updateCountExpiration = service.updateCountExpiration(formatedNow);
			
		}
		

		
		// 4) 서버에 파일 목록이 있을 경우에 비교 시작
		/*
		if(!serverImageList.isEmpty()) {
			
			// 5) 서버 파일 목록을 순차 접근
			for(File server : serverImageList) {
				
				// 6) 서버에 존재하는 파일이
				// DB(dbImageList)에 없다면 삭제
				
				// server.toString();
				//~~~~~~~~~~~~\이미지명.jpg
				
				// List.indexOf(객체) = 객체가 List에 있으면 해당 인덱스 반환, 없으면 -1반환
				if(dbImageList.indexOf(server.getName()) == -1) {
					
					// 서버에 있는 이미지 명이랑 DB에 있는 리스트 명이랑 비교해서 없으면 -1 
					
					System.out.println(server.getName() + "삭제");
					
					server.delete(); // File.delete() : 파일 삭제 
					
					
					
				}
				
			}
			
			
		}
		*/
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

/*
 * @Scheduled
 * 
 * * Spring에서 제공하는 스케줄러 - 스케줄러 : 시간에 따른 특정 작업(Job)의 순서를 지정하는 방법.
 * 
 * 설정 방법 
 * 1) servlet-context.xml -> Namespaces 탭 -> task 체크 후 저장
 * 2) servlet-context.xml -> Source 탭 -> <task:annotation-driven/> 추가
 * 
 *
 * @Scheduled 속성
 *  - fixedDelay : 이전 작업이 끝난 시점으로 부터 고정된 시간(ms)을 설정.
 * 	  @Scheduled(fixedDelay  = 10000) // 이전 작업이 끝난 후 10초 뒤에 실행
 *    
 *    
 *  - fixedRate : 이전 작업이 수행되기 시작한 시점으로 부터 고정된 시간(ms)을 설정.
 *    @Scheduled(fixedRate = 10000) // 이전 작업이 시작된 후 10초 뒤에 실행
 *    
 *    
 * * cron 속성 : UNIX계열 잡 스케쥴러 표현식으로 작성 - cron="초 분 시 일 월 요일 [년도]" - 요일 : 1(SUN) ~ 7(SAT) 
 * ex) 2019년 9월 16일 월요일 10시 30분 20초 cron="20 30 10 16 9 2 " // 연도 생략 가능
 * 
 * - 특수문자 * : 모든 수. 
 * - : 두 수 사이의 값. ex) 10-15 -> 10이상 15이하 
 * , : 특정 값 지정. ex) 3,4,7 -> 3,4,7 지정 
 * / : 값의 증가. ex) 0/5 -> 0부터 시작하여 5마다 
 * ? : 특별한 값이 없음. (월, 요일만 해당) 
 * L : 마지막. (월, 요일만 해당)
 * @Scheduled(cron="0 * * * * *") // 매 분마다 실행
 * 
 * 
 * 
 * 
 * * 주의사항 - @Scheduled 어노테이션은 매개변수가 없는 메소드에만 적용 가능.
 * 
 */

	