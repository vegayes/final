// --------------------- 비밀번호찾기 유효성 검사 ---------------------

const findPwForm = document.getElementById("findPwForm");

if(findPwForm != null) {
	
	const id = document.getElementById("id");
	const email = document.getElementById("email");
	
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
		
	})
}

// --------------------- 비밀번호찾기2 유효성 검사 ---------------------

const findPwForm2 = document.getElementById("findPwForm2");

if(findPwForm2 != null) {
	
	const authNumber = document.getElementById("authNumber");
	
	findPwForm2.addEventListener("submit", e=> {
		
		if(authNumber.value == "") {
			alert("인증번호를 입력해주세요.");
			e.preventDefault();
			authNumber.focus();
			return;
		}
		
	})
}

// --------------------- 비밀번호찾기3 유효성 검사 ---------------------

const findPwForm3 = document.getElementById("findPwForm3");

if(findPwForm3 != null) {
	
	const newPw = document.getElementById("newPw");
	const newPwConfirm = document.getElementById("newPw-confir");
	
	findPwForm3.addEventListener("submit", e=> {
		
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
		
	})
}

// --------------------- 이메일 인증 ---------------------

// 인증번호 발송
const sendAuthKeyBtn = document.getElementById("emailAuth-btn");
const authKeyMessage = document.getElementById("time-section");
const memberEmail = document.getElementById("email");

let authTimer;
let authMin = 4;
let authSec = 59;

// 인증번호를 발송한 이메일 저장
let tempEmail;

sendAuthKeyBtn.addEventListener("submit", function() {
	
	authMin = 4;
	authSec = 59;
	
	/* fetch() API 방식 ajax */
	fetch("/sendEmail/signUp?email=" + memberEmail.value)
	.then(resp => resp.text())
	.then(result => {
		
		if(result > 0) {
			console.log("인증 번호가 발송되었습니다.")
			tempEmail = memberEmail.value;
		} else {
			console.log("인증번호 발송 실패")
		}
		
	})
	.catch(err => {
		console.log("이메일 발송 중 에러 발생");
		console.log(err);
	})
	
	authKeyMessage.innerText = "05:00";
	authKeyMessage.classList.remove("confirm");
	
	authTimer = window.setInterval(() =>{
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
const authKey = document.getElementById("authNumber");
const checkAuthKeyBtn = document.getElementById("next-btn");

checkAuthKeyBtn.addEventListener("submit", function() {
	
	if(authMin > 0 || authSec > 0) { // 시간 제한이 지나지 않은 경우에만 인증번호 검사 진행
		
		/* fetch API */
		const obj = {"inputKey":authKey.value, "email":tempEmail}
		const query = new URLSearchParams(obj).toString()
		
		fetch("/sendEmail/checkAuthKey?" + query)
		.then(resp => resp.text())
		.then(result => {
			
			if(result > 0) {
				clearInterval(authTimer);
				authKeyMessage.innerText = "인증되었습니다.";
				authKeyMessage.classList.add("confirm");
			} else {
				alert("인증번호가 일치하지 않습니다.")
			}
			
		})
		.catch(err => console.log(err));
		
	} else {
		alert("인증 시간이 만료되었습니다. 다시 시도해주세요.");
	}
	
});



















