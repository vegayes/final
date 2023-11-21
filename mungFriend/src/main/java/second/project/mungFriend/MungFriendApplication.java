package second.project.mungFriend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude=SecurityAutoConfiguration.class) 
//@SpringBootApplication
public class MungFriendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MungFriendApplication.class, args);
	}

}


/*★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
 *                       수정 필요 
 *                          
 *  1) config.properties
 *     1-1) DB 연결 정보
 * 	   1-2) 이메일 인증
 *     1-3) 이미지 경로 

 * 
 *  2) 라이브러리 추가할 거 있으면 build.gradle의 dependencies에 추가
 *  
 *  3) 별칭 , mapper 추가 
 *  
 *  
 * ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
 */





