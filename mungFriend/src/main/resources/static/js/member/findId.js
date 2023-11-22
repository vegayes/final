// --------------------- 아이디찾기 유효성 검사 ---------------------

if(findIdForm != null) {
	
	const findIdForm = document.getElementById("findIdForm");
	const name = document.getElementById("name");
	const email = document.getElementById("email");
	
	findIdForm.addEventListener("submit", e=> {
		
		if(name.value == "") {
			alert("이름을 입력해주세요.");
			e.preventDefault();
			name.focus();
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