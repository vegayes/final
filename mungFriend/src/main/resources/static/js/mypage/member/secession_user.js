// --------------------- 회원탈퇴 유효성 검사 ---------------------

if(memberDeleteForm != null) {
	
	const memberDeleteForm = document.getElementById("memberDeleteForm");
	const secessionPw = document.getElementById("secessionPw")
	const secessionPw2 = document.getElementById("secessionPw2");
	const checkbox = document.getElementById("checkbox");
	
	memberDeleteForm.addEventListener("submit", e=> {

		if(secessionPw.value == "") { // 비밀번호 미작성
			alert("비밀번호를 입력해주세요.");
			e.preventDefault();
			secessionPw.focus();
	        return;
		}
		
		if(secessionPw2.value == "") { // 비밀번호 확인 미작성
			alert("비밀번호를 재입력해주세요.");
			e.preventDefault();
			secessionPw2.focus();
	        return;
		}
		
		if(!checkbox.checked) { // 동의 체크가 되지 않은 경우
			alert("약관 동의 후 탈퇴하기 버튼을 눌러주세요")
			e.preventDefault();
			checkbox.focus();
			return;
		}
		
		if(secessionPw.value != secessionPw2.value) {
			alert("비밀번호가 일치하지 않습니다.");
			e.preventDefault();
			secessionPw2.focus();
	        return;
		}
		
		if(!confirm("정말 탈퇴 하시겠습니까?")) { // 취소 클릭 시
			alert("탈퇴가 취소되었습니다.");
			e.preventDefault();
			return;
		}
	
	})
}