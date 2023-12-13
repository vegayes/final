/* ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	진행해야할 것
	1) 버튼에 따라 보이는 값 다르게 하기 O
	2) 다른값에 따라 height변하게하기 O
	3) swiper 
	4) 등록버튼을 누르면 다른 페이지에 값을 저장해서 보이게하기 
◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆ */

/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
					swiper
 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/ 
/*
const mySwiper = new Swiper('.mySwiper', {
    slidesPerView: 2,
    slidesPerColumn: 3,  
    spaceBetween: 0, 
    grid: {                            
        rows: 3,
      },
    pagination: {
        el: '.swiper-pagination',
        clickable: true,
      },
    navigation: {                      
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    }
});
*/

const mySwiper = new Swiper('.mySwiper', {
    slidesPerView: 2,
    slidesPerColumn: 3,
    spaceBetween: 0,
    grid: {                            
        rows: 3,
     },
    pagination: {
        el: '.swiper-pagination',
        clickable: true,
    },
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
});

/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
					gride로 swiper
 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/ 
/*
document.addEventListener('DOMContentLoaded', function() {
    const prevButton = document.getElementById('prevBtn');
    const nextButton = document.getElementById('nextBtn');
    const gridContainer = document.querySelector('.admissionCardArea');
    const gridItems = document.querySelectorAll('.grid-item');
    let currentIndex = 0;

    prevButton.addEventListener('click', function() {
        if (currentIndex > 0) {
            currentIndex--;
            updateSliderPosition();
        }
    });

    nextButton.addEventListener('click', function() {
        if (currentIndex < gridItems.length - 1) {
            currentIndex++;
            updateSliderPosition();
        }
    });

    function updateSliderPosition() {
        const itemWidth = gridItems[currentIndex].clientWidth;
        const itemMargin = parseInt(window.getComputedStyle(gridItems[currentIndex]).marginRight);
        const newPosition = -(itemWidth + itemMargin) * currentIndex;
        gridContainer.style.transform = `translateX(${newPosition}px)`;
    }
});
*/

document.addEventListener('DOMContentLoaded', function() {
	gridLoding();
});


function gridLoding(){
	    const prevButton = document.getElementById('prevBtn');
    const nextButton = document.getElementById('nextBtn');
    const gridContainer = document.querySelector('.admissionCardArea');
    const gridItems = document.querySelectorAll('.grid-item');
    const itemsPerPage = 6; // 페이지당 표시되는 아이템 수
    let currentPage = 0;

    // 아이템의 시작과 끝 인덱스 계산
    let startIndex = currentPage * itemsPerPage;
    let endIndex = (currentPage + 1) * itemsPerPage;

    // 기본 페이지 로딩시 초기 화면 설정
    hideItems();
    showItems();

    prevButton.addEventListener('click', function() {
        if (currentPage > 0) {
            currentPage--;
            updatePage();
        }
    });

    nextButton.addEventListener('click', function() {
        if (endIndex < gridItems.length) {
            currentPage++;
            updatePage();
        }
    });

    function updatePage() {
        startIndex = currentPage * itemsPerPage;
        endIndex = (currentPage + 1) * itemsPerPage;
        hideItems();
        showItems();
    }

    function hideItems() {
        gridItems.forEach(item => {
            item.style.display = 'none';
        });
    }

    function showItems() {
        for (let i = startIndex; i < endIndex; i++) {
            if (gridItems[i]) {
                gridItems[i].style.display = 'block';
            }
        }
    }
}



/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
					입소 신청 버튼 
 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/
// 입소 신청 진행버튼 색 구별 (보이기)
const admissionNBtn = document.getElementById("admissionNBtn");
const admissionYBtn = document.getElementById("admissionYBtn");

var admissionBtnFlag = true; // 입소신청 진행중을 기본설정

/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
					입소 신청 카드 
 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/ 
//const admissionCard = document.querySelectorAll(".admissionCardBoxArea");
const admissionCard = document.querySelectorAll(".grid-item");
//const admissionCardBoxArea = document.querySelectorAll(".admissionCardBoxArea");


/* 처음에 들어왔을 때 */
document.addEventListener('DOMContentLoaded', function() {
	
	console.log("입소신청 :" + admissionArr);
    admissionNBtn.style.opacity = "1";
    admissionNBtn.style.color = "black";

    admissionYBtn.style.opacity = "0.5";
    
});


admissionNBtn.addEventListener("click" ,function(){
    console.log("입소신청 중을  누름");

    admissionNBtn.style.opacity = "1";
    admissionNBtn.style.color = "black";

    admissionYBtn.style.opacity = "0.5";

    admissionBtnFlag = false; 
    
	fetch('/mypage/admin/admissionList/ing', {
	    method: 'POST'
	})
	.then(function(response) {
	    return response.json();
	})
	.then(function(list) {

		console.log("입소신청 :"+JSON.stringify(list));
		
		settingPage(list);
		
	})
	.catch(function(error) {
	  	console.log(error);
	});
});


admissionYBtn.addEventListener("click" ,function(){
    console.log("입소신청 진행완료를 누름");

    admissionNBtn.style.opacity = "0.5";

    admissionYBtn.style.opacity = "1";
    admissionYBtn.style.color = "black";


    admissionBtnFlag = true; 
    
	fetch('/mypage/admin/admissionList/done', {
	    method: 'POST'
	})
	.then(function(response) {
	    return response.json();
	})
	.then(function(list) {

		console.log("입소신청 진행완료 :"+JSON.stringify(list));
		
		settingDonePage(list);
		
	})
	.catch(function(error) {
	  	console.log(error);
	});

    

});

/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
					입소 신청 완료 setting
 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/ 
function settingDonePage(list){
	
	console.log("setting Page :" + JSON.stringify(list));
	
	const items = document.querySelectorAll(".grid-item");

    if(items != null){
	    items.forEach(item => {
	        item.remove();
	    });
    }
	
	const admissionCardArea = document.querySelector('.admissionCardArea');

	list.forEach(admission => {
	    // 새로운 grid-item 요소 생성
	    const gridItem = document.createElement('div');
	    gridItem.classList.add('grid-item');
	
	    // 카드의 내용을 추가하는 HTML 생성
	    const cardContent = `
	        <div class="admissionCardBox">
	            <div class="cardProfile">
	                <div class="cardProfileWrapper">
	                    <img src="${admission.admProfile}" alt="Profile Image">
	                </div>
	            </div>
	            <div class="cardInfo">
	                <div class="cardInfoContent">
	                    이름 : ${admission.admName}
	                </div>
	                <div class="cardInfoContent">
	                    연락처 : ${admission.admPhone}
	                </div>
	                <div class = "cardInfoContent">
	                    견종 : ${admission.admBreed}
	                </div>
	
	                <div class = "cardInfoContent">
	                    성별 : ${admission.admGender}
	                </div>
	
	                <div class = "cardInfoContent">
	                    나이 : ${admission.age}
	                </div>
	
	                <div class = "cardInfoContent">
	                    특이사항 : ${admission.admSignificant}
	                </div>
	
	                <div class = "cardInfoContent">
	                    입소희망일 : ${admission.admHopeDate}
	                </div>
	            </div>
	        </div>
	    `;
	
	    // 생성한 HTML을 grid-item 요소 안에 추가
	    gridItem.innerHTML = cardContent;
	
	    // grid-item을 admissionCardArea에 추가
	    admissionCardArea.appendChild(gridItem);
	});
	
	
	gridLoding();
	
	
}

/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
					입소 신청 중 setting
 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/ 
function settingPage(list){
	
	console.log("setting Page :" + JSON.stringify(list));
	
	const items = document.querySelectorAll(".grid-item");

    if(items != null){
	    items.forEach(item => {
	        item.remove();
	    });
    }
	
	const admissionCardArea = document.querySelector('.admissionCardArea');

	
	
	list.forEach(admission => {
	    // 새로운 grid-item 요소 생성
	    const gridItem = document.createElement('div');
	    gridItem.classList.add('grid-item');
	
	    // 카드의 내용을 추가하는 HTML 생성
	    const cardContent = `
	        <div class="admissionCardBox">
	            <div class="cardProfile">
	                <div class="cardProfileWrapper">
	                    <img src="${admission.admProfile}">
	                </div>
	            </div>
	            <div class="cardInfo">
	                <div class="cardInfoContent">
	                    이름 : ${admission.admName}
	                </div>
	                <div class="cardInfoContent">
	                    연락처 : ${admission.admPhone}
	                </div>
	                <div class = "cardInfoContent">
	                    견종 : ${admission.admBreed}
	                </div>
	
	                <div class = "cardInfoContent">
	                    성별 : ${admission.admGender}
	                </div>
	
	                <div class = "cardInfoContent">
	                    나이 : ${admission.age}
	                </div>
	
	                <div class = "cardInfoContent">
	                    특이사항 : ${admission.admSignificant}
	                </div>
	
	                <div class = "cardInfoContent">
	                    입소희망일 : ${admission.admHopeDate}
	                </div>

                    <div class = "cardInfoContent registArea">
						
						<a href="/adopt/dogRegistration/${admission.admNo}">
							<div class = "admissionRegist">
								등록하기
							</div>														
						</a>

						
					</div>
	            </div>
	        </div>
	    `;
	
	    // 생성한 HTML을 grid-item 요소 안에 추가
	    gridItem.innerHTML = cardContent;
	
	    // grid-item을 admissionCardArea에 추가
	    admissionCardArea.appendChild(gridItem);
	});
	
	
	
	gridLoding();
	
}

