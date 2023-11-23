// --------------------- 회원가입 유효성 검사 ---------------------

/* 유효성 검사 진행 여부 확인용 객체 */
const checkObj = {
	"memberId" : false,
	"memberPw" : false,
	"memberPwConfirm" : false,
	"memberName" : false,
	"memberTel" : false,
	"memberEmail" : false,
	"authKey" : false,
	"memberNickname" : false,
	"memberAddress" : false,
	"memberProfile" : false
}

// --------------------- 아이디 유효성 검사 --------------------- 
const id = document.getElementById("id");
const idCheck = document.getElementById("id-center");

// 아이디가 입력 되었을 때
id.addEventListener("input", () => {
	
	// 아이디 입력이 되지 않은 경우
	if(id.value.trim() == '') {
		idCheck.innerText = "아이디를 입력해주세요.";
		idCheck.classList.remove("confirm", "error");
		checkObj.memberId = false;
		id.value = "";
		return;
	}
	
	// 정규표현식으로 유효성 검사
	const regEx = /^[\w\d]{5,10}$/;
	
	if(regEx.test(id.value)){ // 유효

        fetch("/dupCheck/id?id="+id.value)
        .then(resp => resp.text()) // 응답 객체를 text로 파싱(변환)
        .then(count => {

            if(count == 0){ // 중복 아닌 경우
                idCheck.innerText = "사용 가능한 아이디 입니다";
                idCheck.classList.add("confirm");
                idCheck.classList.remove("error");
                checkObj.memberId = true;
                
            } else { // 중복인 경우
                idCheck.innerText = "이미 사용중인 아이디 입니다";
                idCheck.classList.add("error");
                idCheck.classList.remove("confirm");
                checkObj.memberId = false;
            }
        })
        .catch(err => console.log(err));

        


    } else { // 무효
        idCheck.innerText = "아이디 형식이 유효하지 않습니다";
        idCheck.classList.add("error");
        idCheck.classList.remove("confirm");
        checkObj.memberId = false;
    }
    
});