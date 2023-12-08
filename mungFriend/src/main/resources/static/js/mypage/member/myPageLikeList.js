// 페이지 슬라이드 넘김 처리
document.addEventListener("DOMContentLoaded", function() {
    
    var currentPageIndex = 0; // 현재 페이지의 인덱스를 추적

    function showItemsByPage() {
        var likeDogElements = document.querySelectorAll('.like-dog');
        var itemsPerPage = 6;
        var startIndex = currentPageIndex * itemsPerPage;

        for (var i = 0; i < likeDogElements.length; i++) {
            if (i >= startIndex && i < startIndex + itemsPerPage) {
                likeDogElements[i].style.display = 'block';
            } else {
                likeDogElements[i].style.display = 'none';
            }
        }
    }

    // 페이지 로드 시 처음 6개의 항목을 보이도록 설정
    showItemsByPage();

    // 이전 버튼 클릭 시
    document.getElementById('prevBtn').addEventListener('click', function() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            showItemsByPage();
        }
    });

    // 다음 버튼 클릭 시
    document.getElementById('nextBtn').addEventListener('click', function() {
        var likeDogElements = document.querySelectorAll('.like-dog');
        var itemsPerPage = 6;

        // 현재 페이지가 마지막 페이지가 아닌 경우에만 다음 페이지로 이동
        if (currentPageIndex < Math.ceil(likeDogElements.length / itemsPerPage) - 1) {
            currentPageIndex++;
            showItemsByPage();
        }
    });
});


//좋아요 목록 조회
function selectLikeList(){
    
    fetch("/mypage/member/myPageLikeList")
    .then(response => response.json())
    .then(lList => {

        // 화면에 출력되어 있는 목록 삭제
        const likeList = document.querySelector("like-dog");
        like.innerHTML = "";

        // 게시글 삭제 버튼 얻어오기
        const deleteBtn = document.createElement("deleteBtn");

        // 삭제 버튼에 onclick 이벤트 속성 추가
        deleteBtn.setAttribute("onclick", "deleteLikeDog("+like.dogNo+")");
        
    })
    
} 

function deleteLikeDog(dogNo){

    if( confirm("찜한 목록에서 삭제하시겠습니까?")){

        fetch("/mypage/member/myPageLikeList/delete?dogNo="+dogNo)
        .then(resp => resp.text())
        .then(result => {
            
            console.log("result::", result);
            if(result > 0){
                alert("찜한 목록이 삭제되었습니다");
                selectLikeList(); // 목록을 다시 조회해서 삭제된 글을 제거
                window.location.reload();
            }else{
                alert("찜한 목록 삭제를 실패하였습니다");
                selectLikeList(); 
            }
        })
        .catch( err => console.log(err) );

    }

}