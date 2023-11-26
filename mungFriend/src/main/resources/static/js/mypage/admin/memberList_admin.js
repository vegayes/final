function memberDel(memberNo) {
	
	if(confirm("정말 탈퇴하기를 진행하시겠습니까?")) {
		location.href = "/mypage/admin/memberDel?memberNo=" + memberNo;
	} else {
		alert("탈퇴하기가 취소되었습니다.");
	}
	
}






























