///////////////////////////////////////////////////////////////////////
// --------------------- 비밀번호찾기 유효성 검사 ---------------------
///////////////////////////////////////////////////////////////////////

const findPwForm = document.getElementById("findPwForm");

if(findPwForm != null) {
	
	const id = document.getElementById("id");
	const email = document.getElementById("email");
	const authNum = document.getElementById("authNum");
	const checkAuthKeyBtn = document.getElementById("checkAuthKey-btn");
	
	let isAuthKeyChecked = false;
	
	checkAuthKeyBtn.addEventListener("click", function() {
		
		// 인증번호 확인 로직을 추가
		// 확인되면 isAuthKeyChecked 값을 true로 설정
		// 확인되지 않으면 false로 설정
		isAuthKeyChecked = true;
		
	});
	
	findPwForm.addEventListener("submit", e=> {
		
		if(id.value == "") {
			alert("아이디를 입력해주세요.");
			e.preventDefault();
			id.focus();
			return;
		}
		
		if(email.value == "") {
			alert("이메일을 입력해주세요.");
			e.preventDefault();
			email.focus();
			return;
		}
		
		if(authNum.value == "") {
			alert("인증번호를 입력해주세요.");
			e.preventDefault();
			authNum.focus();
			return;
		}
		
		if(!isAuthKeyChecked) {
			
			// 만약 인증번호 확인 버튼을 클릭하지 않았다면
			alert("인증번호를 확인하세요.");
			e.preventDefault();
			return;
			
		}
		
	})
}

// --------------------- 이메일 인증 ---------------------

// 인증번호 발송
const sendAuthKeyBtn = document.getElementById("sendAuthKey-btn");
const authKeyMessage = document.getElementById("authKeyMessage");
const memberEmail = document.getElementById("email");

let authTimer;
let authMin = 4;
let authSec = 59;

// 인증번호를 발송한 이메일 저장
let tempEmail;

sendAuthKeyBtn.addEventListener("click", function() {
	
	authMin = 4;
	authSec = 59;
	
	/* fetch() API 방식 ajax */
	fetch("/sendEmail/signUp?email=" + memberEmail.value)
	.then(resp => resp.text())
	.then(result => {
		
		if(result > 0) {
			console.log("인증 번호가 발송되었습니다.");
			tempEmail = memberEmail.value;
		} else {
			console.log("인증번호 발송 실패")
		}
		
	})
	.catch(err => {
		console.log("이메일 발송 중 에러 발생");
		console.log(err);
	});
	
	if(memberEmail.value == "") {
		alert("이메일 입력 후 인증번호 받기 버튼을 클릭해주세요");
		memberEmail.focus();
		return;
	}
	
	alert("인증번호가 발송 되었습니다.");
	
	
	authKeyMessage.innerText = "05:00";
	authKeyMessage.classList.remove("confirm");
	
	authTimer = window.setInterval(() => {
											// 삼항연산자  :  조건 	   ?   	  true     : false
		authKeyMessage.innerText = "0" + authMin + ":" + (authSec < 10 ? "0" + authSec : authSec);
		
		// 남은 시간이 0분 0초인 경우
		if(authMin == 0 && authSec == 0) {
			clearInterval(authTimer);
			return;
		}
		
		// 0초인 경우
		if(authSec == 0) {
			authSec = 60;
			authMin--;
		}
		
		
		authSec--; // 1초 감소
		
	}, 1000)
	
});

// 인증 확인
const authKey = document.getElementById("authNum");
const checkAuthKeyBtn = document.getElementById("checkAuthKey-btn");

checkAuthKeyBtn.addEventListener("click", function() {
	
	if(authMin > 0 || authSec > 0) { // 시간 제한이 지나지 않은 경우에만 인증번호 검사 진행
	
		/* fetch API */
		const obj = {"inputKey":authKey.value, "email" : tempEmail}
		const query = new URLSearchParams(obj).toString()
		
		fetch("/sendEmail/checkAuthKey?" + query)
		.then(resp => resp.text())
		.then(result => {
			
			if(result > 0) {
				clearInterval(authTimer);
				authKeyMessage.innerText = "인증되었습니다.";
				authKeyMessage.classList.add("confirm");
				
			} else {
				alert("인증번호가 일치하지 않습니다.");
				
			}
			
		})
		.catch(err => console.log(err));
				
	} else {
		alert("인증 시간이 만료되었습니다. 다시 시도해주세요.");
	}
	
});





















