///////////////////////////////////////////////////////////////////////
// --------------------- 비밀번호찾기2 유효성 검사 --------------------
///////////////////////////////////////////////////////////////////////

const findPwForm2 = document.getElementById("findPwForm2");

if(findPwForm2 != null) {
	
	const newPw = document.getElementById("newPw");
	const newPwConfirm = document.getElementById("newPw-confir");
	
	findPwForm2.addEventListener("submit", e=> {
		
		if(newPw.value == "") {
			alert("새 비밀번호를 입력해주세요.");
			e.preventDefault();
			newPw.focus();
			return;
		}
		
		if(newPwConfirm.value == "") {
			alert("새 비밀번호를 재입력해주세요.");
			e.preventDefault();
			newPwConfirm.focus();
			return;
		}
		
		if(checkObj.memberPw == false || checkObj.memberPwConfirm == false) {
			
			alert("새 비밀번호 또는 새 비밀번호 확인이 유효하지 않습니다");
				
			e.preventDefault(); // form 태그 기본 이벤트 제거
			return; // 함수 종료
			
		}
		
		/*
		for(let key in checkObj) {
			
			alert(checkObj[key]);
			
			if(!checkObj[key]) { // 각 key에 대한 value(true/false)를 얻어와
								 // false인 경우 == 유효하지 않다!
				
				switch(key) {
				
				case "memberPw":
					alert("비밀번호가 유효하지 않습니다");
					document.getElementById(newPw).focus();
					break;
					
				case "memberPwConfirm":
					alert("비밀번호가 확인되지 않았습니다");
					document.getElementById(newPw-confir).focus();
					break;
					
				}
				
				e.preventDefault(); // form 태그 기본 이벤트 제거
				return; // 함수 종료
				
			}
			
		}
		*/
		
	})
}

// 새 비밀번호/새 비밀번호 확인 유효성 검사

/* 유효성 검사 진행 여부 확인용 객체 */
const checkObj = {
	"memberPw" : false,
	"memberPwConfirm" : false
};


const memberPw = document.getElementById("newPw");
const memberPwConfirm = document.getElementById("newPw-confir");
const pwMessage = document.getElementById("newPw-effect-sec");

// 비밀번호 입력 시 유효성 검사
memberPw.addEventListener("input", () => {
	
	// 비밀번호가 입력되지 않은 경우
	if(memberPw.value.trim().length == 0) {
		
		memberPw.value = ""; // 사용자가 공백만 입력한 경우 비밀번호란 비우기
		
		pwMessage.innerText = "영어,숫자,특수문자(!,@,#) 6~20글자 사이";
		pwMessage.classList.remove("confirm", "error"); // 검정 글씨
		
		checkObj.memberPw = false;
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
memberPwConfirm.addEventListener("input", () => {
	
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
			pwMessage.classList.revmremove(confirm);
			checkObj.memberPwConfirm = false;
			
		}
		
	} else { // 비밀번호가 유효하지 않은 경우
	
		checkObj.memberPwConfirm = false;
		
	}
	
});






