function memberDel(memberNo) {
	
	if(confirm("정말 탈퇴하기를 진행하시겠습니까?")) {
		location.href = "/mypage/admin/memberDel?memberNo=" + memberNo;
	} else {
		alert("탈퇴하기가 취소되었습니다.");
	}
	
}

// --------------------- 회원검색 창 유효성 검사 ---------------------

if(memberSearchForm != null) {
	
	const memberSearchForm = document.getElementById("memberSearchForm");
	const memberSearch = document.getElementById("memberSearch");
	
	memberSearchForm.addEventListener("submit", e => {
		
		if(memberSearch.value == "") { // 회원검색 창 미작성
			alert("검색어 입력 후 검색버튼을 눌러주세요.");
			e.preventDefault();
			memberSearch.focus();
			return;
		}
		
	});
	
}





























