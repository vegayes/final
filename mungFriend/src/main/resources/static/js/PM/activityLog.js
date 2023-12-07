// 활동일지 상세 조회 및 수정하기 관련 js
function onUpdateClick(activityNo) {
	
	// alert(activityNo);
	
	location.href = "/pm/activityLogSelectDetail?activityNo=" + activityNo;
	
}
	
// 활동일지 삭제하기 관련 js
function onDeleteClick(activityNo) {
	
	// alert(activityNo);
	
	if(!confirm("정말 삭제하시겠습니까?")) { // 취소
		alert("삭제가 취소되었습니다.");
		return;
	}
	
	location.href = "/pm/activityLogDelete?activityNo=" + activityNo;
	
}