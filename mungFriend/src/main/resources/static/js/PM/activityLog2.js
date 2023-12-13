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

// -----------------------------------------------------------------------

// 좋아요 삭제 버튼 관련
function onLikeDeleteClick(activityLikeNo) {
	// alert(activityLikeNo);

	const fullHeart = document.getElementById("fullHeart_" + activityLikeNo);

	if(loginMemberNo == null) {
		alert("로그인 후 이용해주세요.");
		return;
	}
	
	location.href = "/pm/deleteLike?activityLikeNo=" + activityLikeNo;
	
	/*
	fetch("/pm/deleteLikeFetch?activityLikeNo=" + activityLikeNo)
    .then( resp => resp.text() ) // 응답 객체(자료형 1일때)를 문자열 형식으로 파싱
    .then( data => {
        // 데이터 가공
        console.log(data);
        
        if(data > 0) {
	    	fullHeart.classList.remove("fa-solid",  "fa-heart", "fullHeart-btn");
	    	fullHeart.classList.add("fa-regular",  "fa-heart", "emptyHeart-btn");
    	}
    })
    .catch( err => console.log(err) );
    */
}

// 좋아요 추가 버튼 관련
function onLikeInsertClick(activityNo) {
	// alert(activityNo);

	const emptyHeart = document.getElementById("emptyHeart_" + activityNo);
	
	if(loginMemberNo == null) {
		alert("로그인 후 이용해주세요.");
		return;
	}
	
	// location.href = "/pm/insertLike?activityNo=" + activityNo;
	
	/*
	fetch("/pm/insertLikeFetch?activityNo=" + activityNo)
    .then( resp => resp.text() ) // 응답 객체(자료형 1일때)를 문자열 형식으로 파싱
    .then( data => {
        // 데이터 가공
        console.log(data);
        
        if(data > 0) {
	    	emptyHeart.classList.remove("fa-regular",  "fa-heart", "emptyHeart-btn");
	    	emptyHeart.classList.add("fa-solid",  "fa-heart", "fullHeart-btn");
    	}
    })
    .catch( err => console.log(err) );
}
