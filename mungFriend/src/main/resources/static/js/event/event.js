/* 모달 요소 */
const modal = document.getElementById("eventModalBox");
const closeModalBtn = document.getElementById("eventClose");

const subModal = document.getElementById("eventSubModalBox");

/* 모달창 띄우기 */
function openEvent(){
	
    modal.style.display = "flex";
    subModal.style.display = "flex";
}

/*모달창 닫기 */
function closeEvent(){
    modal.style.display = "none"; 
    subModal.style.display = "none";  
    resetGame();
}


closeModalBtn.addEventListener("click", function(){
    doneQuiz();
});


// JavaScript 코드
const quizBtnWraps = document.querySelectorAll('.quizBtn');

// 라디오 버튼 클릭 시 이벤트 처리
quizBtnWraps.forEach(btnWrap => {
    btnWrap.addEventListener('click', () => {
        // 모든 버튼 스타일 제거
        quizBtnWraps.forEach(wrap => {
            wrap.classList.remove('quizSelect');
        });

        // 현재 선택된 버튼 스타일 추가
        btnWrap.classList.add('quizSelect');
    });
});



// 오늘 하루 이벤트 참여하지 않음.(나중에 할 것)
function setCookie(name, value, expiredays) {
    var date = new Date();
    date.setDate(date.getDate() + expiredays);
    document.cookie = escape(name) + "=" + escape(value) + "; expires=" + date.toUTCString();
}



function eventCloseOneDay() {
	console.log("쿠키 :" + "event_"+ loginMember.memberNo);
    setCookie("event"+loginMember.memberNo, "N", 1);
    closeEvent();
}



//쿠키값이 있는지 확인하여 팝업창 show, hide 처리.
function getCookiePopup () {
	var cookiedata = document.cookie;
	if ( cookiedata.indexOf("eventdone") < 0 ){
		//$('.float-banner').show();
	}
	else {
		//$('.float-banner').hide();
	}
}






















