package second.project.mungFriend.mypage.member.model.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import second.project.mungFriend.common.utility.Util;
import second.project.mungFriend.member.model.dto.Member;
import second.project.mungFriend.mypage.member.model.dao.MemberUserMapper;

@Service
@PropertySource("classpath:/config.properties") // classpath -------> src/main/resource
public class MemberUserServiceImpl implements MemberUserService{
	
	@Value("${my.member.webpath}") // @Value --------> 필드에서만 사용 가능
	private String webPath;
	
	@Value("${my.member.location}")
	private String filePath;
	
	@Autowired
	private MemberUserMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	// 정보수정
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateInfoUser(MultipartFile profileImage, Member updateMember, Member loginMember) throws Exception {
		
		// 비밀번호 암호화 (Bcrypt) 후 다시 updateMember 세팅
		String encPw = bcrypt.encode(updateMember.getMemberPw());
		updateMember.setMemberPw(encPw);
		
		// 프로필 이미지 변경 실패 대비
		String temp = updateMember.getMemberProfile(); // 기존에 가지고 있던 이전 이미지 저장
		
		
		String rename = null; // 변경 이름 저장 변수
		
		if(profileImage.getSize() > 0) { // 업로드된 이미지가 있을 경우
			
			// 1) 파일 이름 변경
			rename = Util.fileRename(profileImage.getOriginalFilename());
			
			// 2) 바뀐 이름 updateMember에 세팅
			updateMember.setMemberProfile(webPath + rename);
			
		} else { // 업로드된 이미지가 없는 경우 (이미지 삭제 버튼)
			
			updateMember.setMemberProfile(null);
			
		}
		
		
		// 프로필 이미지 수정 mapper 메서드 호출
		int result = mapper.updateInfoUser(updateMember);
		
		
		if(result > 0) { // DB에 이미지 경로 업데이트 성공했다면
			
			// 업로드된 새 이미지가 있을 경우
			if(rename != null) {
				
				// 메모리에 임시 저장되어있는 파일을 서버에 진짜로 저장하는 것
				profileImage.transferTo(new File(filePath + rename));
				loginMember.setMemberProfile(webPath + rename);
			}
			
			
		} else { // 실패
			
			// 이전 이미지로 프로필 다시 세팅
			updateMember.setMemberProfile(temp);
			
		} 
		
		return result;
	}
	
	// 회원탈퇴
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int secessionUser(String memberPw, int memberNo) {
		
		// 1. 회원 번호가 일치하는 회원의 비밀번호 조회
		String encPw = mapper.selectEncPw(memberNo);
		
		// 2. 비밀번호가 일치하면
		if(bcrypt.matches(memberPw, encPw)) {
			// MEMBER_DEL_YN -> 'Y'로 바꾸고 1 반환
			return mapper.secessionUser(memberNo);
		}
		
		// 3. 비밀번호가 일치하지 않으면 -> 0 반환
		return 0;
		
	}

	
	

}
