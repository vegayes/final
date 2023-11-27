// --------------------- 비밀번호찾기 유효성 검사 ---------------------

if(findPwForm != null) {
	
	const findPwForm = document.getElementById("findPwForm");
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