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

// -----------------------------------------------------------------------

// 마우스 호버시 내용 나타나도록
function onMouseOver(activityNo) {
	const activityContent = document.getElementById("check_" + activityNo);
	activityContent.style.display = "flex";
	activityContent.style.transitionDelay = "0s";
	/*activityContent.style.position = "absolute";
	activityContent.style.color = "white";
	activityContent.style.fontSize = "13px";
	activityContent.style.padding = "10px";
	activityContent.style.width = "150px";
	activityContent.style.height = "150px";*/
}

// 마우스가 이미지 영역 밖으로 나가면 내용 보이지 않도록
function onMouseOut(activityNo) {
	const activityContent = document.getElementById("check_" + activityNo);
	activityContent.style.display = "none";
}
