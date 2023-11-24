// --------------------- 정보수정 유효성 검사 ---------------------

/* 유효성 검사 진행 여부 확인용 객체 */
const checkObj = {
	"memberProfile" : false,
	"memberPw" : false,
	"memberPwConfirm" : false,
	"memberTel" : false,
	"memberEmail" : false,
	"authKey" : false,
	"memberNickname" : false,
	"memberAddress" : false
}

// --------------------- 비밀번호 ---------------------

// 비밀번호/비밀번호 확인 유효성 검사
const memberPw = document.getElementById("updatePw");
const memberPwConfirm = document.getElementById("updatePwCheck");
const pwMessage = document.getElementById("pwMessage");

// 비밀번호 입력 시 유효성 검사
memberPw.addEventListener("input", () => {
	
	// 비밀번호가 입력되지 않은 경우
	if(memberPw.value.trim().length == 0) {
		memberPw.value = ""; // 사용자가 공백만 입력한 경우 비밀번호란 비우기
		
		pwMessage.innerText = "영어,숫자,특수문자(!,@,#) 6~20글자 사이";
		pwMessage.classList.remove("confirm", "error"); // 검정 글씨
		
		checkObj.memberPw = false; // 빈칸 == 유효 X
		return;
	}
	
	// 정규 표현식을 이용한 비밀번호 유효성 검사
	
	// 영어,숫자,특수문자(!,@,#) 6~20글자 사이
	const regEx = /^[a-zA-Z0-9\!\@\#]{6,20}$/;
	
	// 입력한 비밀번호가 유효한 경우
	if(regEx.test(memberPw.value)) {
		checkObj.memberPw = true;
		
		// 비밀번호가 유효하게 작성된 상태에서
		// 비밀번호 확인이 입력되지 않았을 때
		if(memberPwConfirm.value.trim().length == 0) {
			
			pwMessage.innerText = "유효한 비밀번호 형식입니다";
			pwMessage.classList.add("confirm");
			pwMessage.classList.remove("error");
			
		} else {
			// 비밀번호가 유효하게 작성된 상태에서
			// 비밀번호 확인이 입력되어 있을 때
			
			// 비밀번호 == 비밀번호 확인 (같을 경우)
			if(memberPw.value == memberPwConfirm.value) {
				pwMessage.innerText = "비밀번호가 일치합니다";
				pwMessage.classList.add("confirm");
				pwMessage.classList.remove("error");
				checkObj.memberPwConfirm = true;
				
			} else { // 다를 경우
				pwMessage.innerText = "비밀번호가 일치하지 않습니다";
				pwMessage.classList.add("error");
				pwMessage.classList.remove("confirm");
				checkObj.memberPwConfirm = false;
							
			}
		}
	} else { // 유효하지 않은 경우
		pwMessage.innerText = "비밀번호 형식이 유효하지 않습니다";
		pwMessage.classList.add("error");
		pwMessage.classList.remove("confirm");
		checkObj.memberPw = false;
		
	}
	
});

// 비밀번호 확인 유효성 검사
memberPwConfirm.addEventListener('input', () => {
	
	if(checkObj.memberPw) { // 비밀번호가 유효하게 작성된 경우에
	
		// 비밀번호 == 비밀번호 확인 (같을 경우)
		if(memberPw.value == memberPwConfirm.value) {
			pwMessage.innerText = "비밀번호가 일치합니다";
			pwMessage.classList.add("confirm");
			pwMessage.classList.remove("error");
			checkObj.memberPwConfirm = true;
			
		} else { // 다를 경우
			pwMessage.innerText = "비밀번호가 일치하지 않습니다";
			pwMessage.classList.add("error");
			pwMessage.classList.remove("confirm");
			checkObj.memberPwConfirm = false;
			
		}
		
	} else { // 비밀번호가 유효하지 않은 경우
		checkObj.memberPwConfirm = false;
		
	}
});

// ---------------------  전화번호 ---------------------

// 전화번호 유효성 검사
const memberTel = document.getElementById("updatePhone");
const telMessage = document.getElementById("telMessage");

// 전화번호가 입력 되었을 때
memberTel.addEventListener("input", () => {
	
	// 전화번호가 입력이 되지 않은 경우
	if(memberTel.value.trim() == '') {
		telMessage.innerText = "'-' 없이 숫자만 입력";
		telMessage.classList.remove("confirm", "error");
		checkObj.memberTel = false;
		memberTel.value = "";
		return;
	}
	
	// 정규표현식으로 유효성 검사
	const regEx = /^0(1[01679]|2|[3-6][1-5]|70)[1-9]\d{2,3}\d{4}$/;
	
	if(regEx.test(memberTel.value)) { // 유효
		telMessage.innerText = "유효한 전화번호 형식입니다";
		telMessage.classList.add("confirm");
		telMessage.classList.remove("error");
		checkObj.memberTel = true;
		
	} else { // 무효
		telMessage.innerText = "전화번호 형식이 유효하지 않습니다";
		telMessage.classList.add("error");
		telMessage.classList.remove("confirm");
		checkObj.memberTel = false;
		
	}
	
});

// ---------------------  이메일 ---------------------

// 이메일 유효성 검사
const memberEmail = document.getElementById("updateEmail");
const emailMessage = document.getElementById("emailMessage");

// 이메일이 입력될 때 마다
memberEmail.addEventListener("input", () => {
	
	// 입력된 이메일이 없을 경우
	if(memberEmail.value.trim().length == 0) {
		memberEmail.value = "";
		
		emailMessage.innerText = "메일을 받을 수 있는 이메일 입력";
		
		// confirm, error 클래스 삭제해서 검정 글씨로 만들기
		emailMessage.classList.remove("confirm", "error");
		
		checkObj.memberEmail = false; // 빈칸 == 유효 X
		return;
	}
	
	// 정규 표현식을 이용해서 유효한 형식인지 판별
	// 1) 정규표현식 객체 생성
	const regEx = /^[A-Za-z\d\-\_]{4,}@[가-힣\w\-\_]+(\.\w+){1,3}$/;
	
	// 2) 입력 받은 이메일과 정규식 일치 여부 판별
	if(regEx.test(memberEmail.value)) { // 유효한 경우
		
		/* fetch() API를 이용한 ajax(비동기 통신) : 이메일 중복*/
		
		// GET 방식
		fetch("/dupCheck/email?email=" + memberEmail.value)
		.then(res => res.text())
		.then(count => {
			
			// count : 중복되면 1, 중복 아니면 0
			if(count == 0) {
				emailMessage.innerText = "사용 가능한 이메일입니다";
				emailMessage.classList.add("confirm"); // .confirm 스타일 적용
				emailMessage.classList.remove("error"); // .error 스타일 제거
				checkObj.memberEmail = true;
				
			} else {
				emailMessage.innerText = "이미 사용중인 이메일입니다";
				emailMessage.classList.add("error"); // .error 스타일 적용
				emailMessage.classList.remove("confirm"); // .confirm 스타일 제거
				checkObj.memberEmail = false;
				
			}
			
		})
		.catch(err => console.log(err));
		
	} else { // 유효하지 않은 경우(무효인 경우)
		emailMessage.innerText = "이메일 형식이 유효하지 않습니다";
		emailMessage.classList.add("error"); // .error 스타일 적용
		emailMessage.classList.remove("confirm"); // .confirm 스타일 제거
		
		checkObj.memberEmail = false; // 유효 X
		
	}
	
});

// ---------------------  닉네임 ---------------------

// 닉네임 유효성 검사
const memberNickname = document.getElementById("updateNickname");
const nickMessage = document.getElementById("nickMessage");

// 닉네임이 입력이 되었을 때
memberNickname.addEventListener("input", () => {
	
	// 닉네임 입력이 되지 않은 경우
	if(memberNickname.value.trim() == '') {
		nickMessage.innerText = "한글,영어,숫자로만 2~10글자";
		nickMessage.classList.remove("confirm", "error");
		checkObj.memberNickname = false;
		memberNickname.value = "";
		return;
		
	}
	
	// 정규표현식으로 유효성 검사
	const regEx = /^[가-힣\w\d]{2,10}$/;
	
	if(regEx.test(memberNickname.value)) { // 유효
		
		fetch("/dupCheck/nickname?nickname=" + memberNickname.value)
		.then(resp => resp.text()) // 응답 객체를 text로 파싱(변환)
		.then(count => {
			
			if(count == 0) { // 중복 아닌 경우
				nickMessage.innerText = "사용 가능한 닉네임 입니다";
				nickMessage.classList.add("confirm");
				nickMessage.classList.remove("error");
				checkObj.memberNickname = true;
				
			} else { // 중복인 경우
				nickMessage.innerText = "이미 사용중인 닉네임 입니다";
				nickMessage.classList.add("error");
				nickMessage.classList.remove("confirm");
				checkObj.memberNickname = false;
				
			}
		})
		
		.catch(err => console.log(err));
		
	} else { // 무효
		nickMessage.innerText = "닉네임 형식이 유효하지 않습니다";
		nickMessage.classList.add("error");
		nickMessage.classList.remove("confirm");
		checkObj.memberNickname = false;
		
	}
	
});










































