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

// --------------------- 이메일 인증 ---------------------

// 인증번호 발송
const sendAuthKeyBtn = document.getElementById("sendAuthKeyBtn");
const authKeyMessage = document.getElementById("authKeyMessage");

let authTimer;
let authMin = 4;
let authSec = 59;

// 인증번호를 발송한 이메일 저장
let tempEmail;

sendAuthKeyBtn.addEventListener("click", function() {
	
	authMin = 4;
	authSec = 59;
	
	checkObj.authKey = false;
	
	if(checkObj.memberEmail) { // 중복이 아닌 이메일인 경우
	
		/* fetch() API 방식 ajax */
		fetch("/sendEmail/signUp?email=" + memberEmail.value)
		.then(resp => resp.text())
		.then(result => {
			
			if(result > 0) {
				console.log("인증 번호가 발송되었습니다.");
				tempEmail = memberEmail.value;
			} else {
				console.log("인증번호 발송 실패");
			}
			
		})
		.catch(err => {
			console.log("이메일 발송 중 에러 발생");
			console.log(err);
		});
		
		
		alert("인증번호가 발송 되었습니다.");
		
		
		authKeyMessage.innerText = "05:00";
		authKeyMessage.classList.remove("confirm");
		
		authTimer = window.setInterval(() => {
												// 삼항연산자  :  조건 	   ?   	 true      : false
			authKeyMessage.innerText = "0" + authMin + ":" + (authSec < 10 ? "0" + authSec : authSec);
			
			// 남은 시간이 0분 0초인 경우
			if(authMin == 0 && authSec == 0) {
				checkObj.authKey = false;
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
		
	} else {
		alert("기존에 사용하고 있는 이메일은 인증이 불필요합니다");
		memberEmail.focus();
	}
	
});


// 인증 확인
const authKey = document.getElementById("updateEmailCheck");
const checkAuthKeyBtn = document.getElementById("checkAuthKeyBtn");

checkAuthKeyBtn.addEventListener("click", function() {
	
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
				checkObj.authKey = true;
				
			} else {
				alert("인증번호가 일치하지 않습니다.");
				checkObj.authKey = false;
				
			}
			
		})
		.catch(err => console.log(err));
		
	} else {
		alert("인증 시간이 만료되었습니다. 다시 시도해주세요.")
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

// 정보수정 form 태그가 제출 되었을 때
document.getElementById("updateForm").addEventListener("submit", e=> {
	
	const updatePw = document.getElementById("updatePw");
	const updatePwCheck = document.getElementById("updatePwCheck");
	
	if(updatePw.value.trim().length == 0) {
		alert("비밀번호를 입력해주세요");
		e.preventDefault();
		updatePw.focus();
		return;
	}
	
	if(updatePwCheck.value.trim().length == 0) {
		alert("비밀번호를 재입력해주세요");
		e.preventDefault();
		updatePwCheck.focus();
		return;
	}
	
	// initCheck
    // 초기 프로필 이미지 상태를 저장하는 변수
    // false == 기본 이미지,  true == DB에서 가져온 이미지

    // deleteCheck
    // 프로필 이미지가 새로 업로드 되거나 삭제 되었음을 나타내는 변수
    // -1 == 초기값 ,  0 == 프로필 삭제(이미지 삭제 버튼),  1 == 새 이미지 업로드

    let flag = true; // 제출하면 안되는 경우의 초기값 플래그 true로 지정

    // 이전 프로필 이미지가 없으면서, 새 이미지 업로드를 했다 -> 처음으로 이미지 추가
    if(!initCheck && deleteCheck == 1)  flag = false;

    // 이전 프로필 이미지가 있으면서, 새 이미지 업로드를 했다 -> 새 이미지로 변경
    if(initCheck && deleteCheck == 1)   flag = false;
    
    // 이전 프로필 이미지가 있으면서, 프로필 삭제 버튼을 눌렀다 -> 삭제
    if(initCheck && deleteCheck == 0)   flag = false;

    
    if(flag){ // flag == true -> 제출하면 안되는 경우
        e.preventDefault(); // form 기본 이벤트 제거
        alert("이미지 변경 후 클릭하세요");
    }

    return true;
    
});

// ---------------------------------------------------------------

// 프로필 이미지 추가/변경/삭제
const profileImage = document.getElementById("profileImage"); // img 태그
const imageInput = document.getElementById("updateProfile"); // input 태그
const deleteImage = document.getElementById("imgDelBtn"); // 이미지 삭제 버튼

let initCheck; // 초기 프로필 이미지 상태를 저장하는 변수
				// false == 기본 이미지, true == DB에서 가져온 이미지
				
let deleteCheck = -1;
// 프로필 이미지가 새로 업로드 되거나 삭제 되었음을 나타내는 변수
// -1 == 초기값 , 0 == 프로필 삭제(이미지 삭제 버튼) , 1 == 새 이미지 업로드


let originalImage; // 초기 프로필 이미지 파일 경로 저장

if(imageInput != null) { // 화면에 imageInput이 있을 경우

	// 프로필 이미지가 출력되는 img태그의 src 속성을 저장
	originalImage = profileImage.getAttribute("src");
	
	// 회원 프로필 화면 진입 시
	// 현재 회원의 프로필 이미지 상태를 확인
	if(profileImage.getAttribute("src") == "/images/member/profile_noImage.png") {
		// 기본 이미지인 경우
		initCheck = false;
	} else {
		initCheck = true;		
	}
	
	
	// change 이벤트 : 값이 변했을 때
	// - input type="file", "checkbox", "radio" 에서 많이 사용
    // - text/number 형식 사용 가능
    //   -> 이 때 input값 입력 후 포커스를 잃었을 때 
    //      이전 값과 다르면 change 이벤트 발생
    
    imageInput.addEventListener("change", e => {
		
		// 2MB로 최대 크기 제한
		const maxSize = 1 * 1024 * 1024 * 2; // 파일 최대 크기 지정(바이트 단위)
		
		console.log(e.target); // input
        console.log(e.target.value); // 업로드된 파일 경로
        console.log(e.target.files); // 업로드된 파일의 정보가 담긴 배열
        
        const file = e.target.files[0]; // 업로드한 파일의 정보가 담긴 객체
        
        
        // 파일을 한번 선택한 후 취소했을 때 (file이 undefined가 된다)
		if(file == undefined) {
			console.log("파일 선택이 취소됨");
			deleteCheck = -1; // 취소 == 파일 없음 == 초기상태
			
			// 취소 시 기존 프로필 이미지로 변경 (기존 이미지에서 변경되는게 없게 하겠다)
			profileImage.setAttribute("src", originalImage);
			
			return;
		}
		
		if( file.size > maxSize){ // 선택된 파일의 크기가 최대 크기를 초과한 경우
            alert("2MB 이하의 이미지를 선택해주세요.");
            imageInput.value = ""; 
            // input type="file" 태그에 대입할 수 있는 value는 "" (빈칸) 뿐이다!
            deleteCheck = -1; // 취소 == 파일 없음 == 초기상태

            // 기존 프로필 이미지로 변경
            profileImage.setAttribute("src", originalImage);

            return;
        }
        
        // JS에서 파일을 읽는 객체
        // - 파일을 읽고 클라이언트 컴퓨터에 파일을 저장할 수 있음 
        const reader = new FileReader();

        reader.readAsDataURL(file);
        // 매개변수에 작성된 파일을 읽어서 저장 후
        // 파일을 나타내는 URL을 result 속성으로 얻어올 수 있게 함.

        // 다 읽었을 때
        reader.onload = e => {
            //console.log(e.target);
            console.log(e.target.result); // 읽은 파일의 URL 
            /* 개발자도구에서 Application탭에서 Frames > top > images 안에서 확인가능 */

            const url = e.target.result;

            // 프로필이미지(img) 태그에 src 속성으로 추가
            profileImage.setAttribute("src", url);

            deleteCheck = 1;
        }
		
	});
    
    
	// 이미지 삭제 버튼 클릭 시
	deleteImage.addEventListener('click', () => {
		imageInput.value = ""; // input type="file"의 value 삭제
		
		profileImage.setAttribute("src", "/images/member/profile_noImage.png");
		// 프로필 이미지를 기본 이미지로 변경
		
		deleteCheck = 0;
	});

}










































