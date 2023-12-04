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
