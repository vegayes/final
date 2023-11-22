// --------------------- 로그인 유효성 검사 ---------------------

if(loginForm != null) {
	
	const loginForm = document.getElementById("loginForm");
	const id = document.getElementById("id");
	const pw = document.getElementById("pw");
	
	loginForm.addEventListener("submit", e=> {
		
		if(id.value == "") {
			alert("아이디를 입력해주세요.");
			e.preventDefault();
			id.focus();
	        return;
		}
		
		if(pw.value == "") {
			alert("비밀번호를 입력해주세요.");
			e.preventDefault();
			pw.focus();
	        return;
		}
	
	})
}