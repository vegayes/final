// 예약 삭제버튼 클릭 시
function deleteReserve(dogNo){

    if( confirm("예약 내역에서 삭제하시겠습니까?")){

        fetch("/mypage/member/reservationList_user/delete?dogNo="+dogNo)
        .then(resp => resp.text())
        .then(result => {
            
            console.log("result::", result);
            if(result > 0){
                alert("예약 내역이 삭제되었습니다");
                selectLikeList(); // 목록을 다시 조회해서 삭제된 글을 제거
                window.location.reload();
            }else{
                alert("예약 내역 삭제를 실패하였습니다");
                selectLikeList(); 
            }
        })
        .catch( err => console.log(err) );

    }

}

// 예약내역 조회
function selectLikeList(){
    
    fetch("/mypage/member/reservationList_user")
    .then(response => response.json())
    .then(dList => {

        // 화면에 출력되어 있는 목록 삭제
        const deleteList = document.querySelector("list-bottom");
        like.innerHTML = "";

        // 게시글 삭제 버튼 얻어오기
        const deleteBtn = document.createElement("cancle-btn");

        // 삭제 버튼에 onclick 이벤트 속성 추가
        deleteBtn.setAttribute("onclick", "deleteReserve("+reserve.dogNo+")");
        
    })
    
} 