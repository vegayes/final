/* ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	진행해야할 것
	1) 버튼에 따라 보이는 값 다르게 하기 O
	2) 다른값에 따라 height변하게하기 O
	3) swiper 
	4) 등록버튼을 누르면 다른 페이지에 값을 저장해서 보이게하기 
◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆ */



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
const admissionCard = document.querySelectorAll(".admissionCardBoxArea");
const admissionCardBoxArea = document.querySelectorAll(".admissionCardBoxArea");


/* 처음에 들어왔을 때 */
document.addEventListener('DOMContentLoaded', function() {
    admissionNBtn.style.opacity = "1";
    admissionNBtn.style.color = "black";

    admissionYBtn.style.opacity = "0.5";
    
    /* 입소신청내역 진행중만 display */
	for(let i = 0; i < admissionArr.length; i++){
	
		var admYN = admissionArr[i].admYN;
		if(admYN == 'Y'){
			admissionCard[i].style.display = "none";
		}
	}
});


admissionNBtn.addEventListener("click" ,function(){
    console.log("입소신청 중을  누름");

    admissionNBtn.style.opacity = "1";
    admissionNBtn.style.color = "black";

    admissionYBtn.style.opacity = "0.5";

    admissionBtnFlag = false; 
    
    /* 입소신청내역 진행중만 display */
	for(let i = 0; i < admissionArr.length; i++){
	
		var admYN = admissionArr[i].admYN;
		if(admYN == 'Y'){
			admissionCard[i].style.display = "none";
		}else{
			admissionCard[i].style.display = "block";
			admissionCardBoxArea[i].style.height  = "240px";
		}
	}
});


admissionYBtn.addEventListener("click" ,function(){
    console.log("입소신청 진행완료를 누름");

    admissionNBtn.style.opacity = "0.5";

    admissionYBtn.style.opacity = "1";
    admissionYBtn.style.color = "black";


    admissionBtnFlag = true; 
    
    /* 입소신청내역 완료만 display */
	for(let i = 0; i < admissionArr.length; i++){
	
		var admYN = admissionArr[i].admYN;
		if(admYN == 'Y'){
			admissionCard[i].style.display = "block";
			admissionCardBoxArea[i].style.height  = "220px";
			
		}else{
			admissionCard[i].style.display = "none";
		}
	}
});






























