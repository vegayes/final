/* ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	진행해야할 것
	1) 라디오 버튼은 하나만 선택되었을 때만 색깔 하나만 나타나게하기
	2) 라디오 버튼으로 focus 맞출 수 있으면 해보기
	3) 생일, 입소희망일 달력을 이용할 수 있다면해보기
◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆ */

/* 부모요소 선택된 값 스타일 바꾸기 */
function changeParentBorderStyle(radio) {
  var radioGroup = document.querySelectorAll('input[name="' + radio.getAttribute('name') + '"]');
  
  radioGroup.forEach(function(radioButton) {
    var parentElement = radioButton.parentElement;
    
    if (parentElement) {
      parentElement.classList.remove('selected'); // 모든 버튼의 선택 상태 제거
    }
  });
  
  var parent = radio.parentElement;
  
  if (parent) {
    parent.classList.add('selected'); // 선택된 버튼에 선택 상태 추가
  }
}

var radioButtons = document.querySelectorAll('.admissionTwoBtn input[type="radio"]');

radioButtons.forEach(function(radio) {
  radio.addEventListener('change', function() {
    changeParentBorderStyle(radio);
  });
});


/* 값 모두 입력해야 제출될 수 있게 진행하기 */
const admissionApplyForm = document.querySelector(".admissionApplyForm");

admissionApplyForm.addEventListener('submit', e => {
    var applyName = document.getElementById("applyName");
    var applyPhone = document.getElementById("applyPhone");
    var applyBreed = document.getElementById("applyBreed");
    var applyBirthday = document.getElementById("applyBirthday");
    var admissionGender = document.querySelector('input[name="admGender"]:checked');
    var admNeutering = document.querySelector('input[name="admNeuteringYN"]:checked');
    var admVaccine = document.querySelector('input[name="admVaccineYN"]:checked');
    var applyHopeDate = document.querySelector('input[name="admHopeDate"]');
    
    if(applyName.value.trim().length == 0){
        alert("이름을 작성해주세요!");
        applyName.value = "";
        applyName.focus();
        e.preventDefault(); // form 기본 이벤트 제거
        return;
    }
    
    if(applyPhone.value.trim().length == 0){
        alert("연락처를 작성해주세요!");
        applyPhone.value = "";
        applyPhone.focus();
        e.preventDefault(); // form 기본 이벤트 제거
        return;
    }
    
    if(applyBreed.value.trim().length == 0){
		alert("견종을 작성해주세요!");
        applyBreed.value = "";
        applyBreed.focus();
        e.preventDefault(); // form 기본 이벤트 제거
        return;
    }
    
    if(applyBirthday.value.trim().length == 0){
		alert("생일을 작성해주세요!");
        applyBirthday.value = "";
        applyBirthday.focus();
        e.preventDefault(); // form 기본 이벤트 제거
        return;
    }
    
    
    if(admissionGender == null){
		alert("성별을 선택해주세요!");
        //document.querySelector('input[name="admGender"]');[0].focus();
        e.preventDefault(); // form 기본 이벤트 제거
        return;
    }
 
    if(admNeutering == null){
		alert("중성화 여부를 선택해주세요!");
        //document.querySelector('input[name="admNeuteringYN"]')[0].focus();
        e.preventDefault(); // form 기본 이벤트 제거
        return;
    }
    
    if(admVaccine == null){
		alert("접종여부를 선택해주세요!");
        //document.querySelector('input[name="admVaccineYN"]')[0].focus();
        e.preventDefault(); // form 기본 이벤트 제거
        return;
    }
    
    if(applyHopeDate.value.trim().length == 0){
        alert("입소 희망일을 작성해주세요!");
        applyHopeDate.value = "";
        applyHopeDate.focus();
        e.preventDefault(); // form 기본 이벤트 제거
        return;
    }
    
    
});




/* 이미지 관련 */
const profileImage = document.getElementById("admissionProfileImg"); // img 태그
const imageInput = document.getElementById("admissionProfileInput"); // input 태그
const deleteImage = document.getElementById("deleteImage"); // x버튼


/* 이미지 제출하면 변하게 하기 및 프로필과 관련된 체크 확인 */
let initCheck; 
let deleteCheck = -1; 

let originalImage; // 초기 프로필 이미지 파일 경로 저장

if(imageInput != null){ // 화면에 imageInput이 있을 경우 ( if 굳이 안해도 되긴 함 ) 
    // 프로필 이미지가 출력되는 img태그의 src 속성을 저장
    originalImage = profileImage.getAttribute("src"); 

    // 회원 프로필 화면 진입 시 
    // 현재 회원의 프로필 이미지 상태를 확인
    if(profileImage.getAttribute("src") == "/images/admissionApply/dog.svg"){
        initCheck = false;
    }else{
        initCheck = true;
    }


    imageInput.addEventListener("change", e => {
        // 2MB로 최대 크기 제한 
        const maxSize = 1 * 1024 * 1024 * 2; 
        const file = e.target.files[0]; 

        // 파일을 한번 선택한 후 취소했을 때 ( file이 undefined가 된다 ) 
        if(file == undefined){ 
            console.log("파일 선택이 취소됨");
            deleteCheck = -1; // 취소 == 파일 없음 == 초기상태

            profileImage.setAttribute("src", originalImage);

            return;
        }

        if( file.size > maxSize){ // 선택된 파일의 크기가 최대 크기를 초과한 경우
            alert("2MB 이하의 이미지를 선택해주세요.");
            imageInput.value = ""; 
            // input type="file" 태그에 대입할 수 있는 value는 "" (빈칸) 뿐이다!
            deleteCheck = -1; // 취소 == 파일 없음 == 초기상태

            // 기존 프로필 이미지로 변경
            profileImage.setAttribute("src", originalImage);
            return;
        }

	
        const reader = new FileReader();

        reader.readAsDataURL(file);

        reader.onload = e => {
            console.log(e.target.result); // 읽은 파일의 URL 

            const url = e.target.result;
            profileImage.setAttribute("src", url);
            profileImage.style.height = '100%';
            deleteCheck = 1;
        }
    });


    // x버튼 클릭 시
    deleteImage.addEventListener('click', () => {
        imageInput.value = ""; 
        profileImage.setAttribute("src", "/images/admissionApply/dog.svg");
        profileImage.style.height = '65%'; /*프로필 이미지가 dog.svg인경우는 height 변경하기  */

        deleteCheck = 0;
    });


    // #profileFrm이 제출 되었을 때
    document.getElementById("admissionSubmitBtn").addEventListener("submit", e => {

        let flag = true; 

        // 이전 프로필 이미지가 없으면서, 새 이미지 업로드를 했다 -> 처음으로 이미지 추가
        if(!initCheck && deleteCheck == 1)  flag = false;
        // 이전 프로필 이미지가 있으면서, 새 이미지 업로드를 했다 -> 새 이미지로 변경
        if(initCheck && deleteCheck == 1)   flag = false;
        // 이전 프로필 이미지가 있으면서, 프로필 삭제 버튼을 눌렀다 -> 삭제
        if(initCheck && deleteCheck == 0)   flag = false;
        if(flag){ // flag == true -> 제출하면 안되는 경우
            e.preventDefault(); // form 기본 이벤트 제거
            alert("이미지 변경 후 클릭하세요");
        }
        
	    return true;
    });
}


// 현재 날짜를 가져오는 함수
function getCurrentDate() {
    const now = new Date();
    const year = now.getFullYear();
    let month = now.getMonth() + 1;
    let day = now.getDate();

    // 월이나 일이 한 자리 수인 경우 두 자리로 표시하기 위해 0을 추가
    month = month < 10 ? '0' + month : month;
    day = day < 10 ? '0' + day : day;

    return `${year}-${month}-${day}`;
}

// input 요소의 max 속성에 현재 날짜를 설정
const applyBirthdayInput = document.getElementById('applyBirthday');
const applyHopeDateInput = document.getElementById('applyHopeDate');
applyBirthdayInput.setAttribute('max', getCurrentDate());
applyHopeDateInput.setAttribute('min', getCurrentDate());








