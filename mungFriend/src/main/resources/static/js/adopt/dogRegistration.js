// function openFileInput() {
//     // 파일 입력 엘리먼트를 클릭하여 파일 선택 창 열기
//     document.getElementById('img0').click();
// }



// 미리보기 관련 요소 모두 얻어오기

// img 5개
const preview = document.getElementsByClassName("preview"); 
// file 5개
const inputImage = document.getElementsByClassName("inputImage"); 
// x버튼 5개
const deleteImage = document.getElementsByClassName("delete-image"); 

// -> 위에 얻어온 요소들의 개수가 같음 == 인덱스가 일치함

for(let i=0 ; i< inputImage.length ; i++){

    // 파일이 선택되거나, 선택 후 취소 되었을 때
    inputImage[i].addEventListener('change', e => {

        const file = e.target.files[0]; // 선택된 파일의 데이터

        if(file != undefined){ // 파일이 선택 되었을 때

            const reader = new FileReader(); // 파일을 읽는 객체

            reader.readAsDataURL(file);
            // 지정된 파일을 읽은 후 result 변수에 URL 형식으로 저장

            reader.onload = e => { // 파일을 다 읽은 후 수행
                preview[i].setAttribute("src", e.target.result);

                preview[i].style.width = '100%';
                preview[i].style.height = '100%';
                preview[i].style.objectFit = 'cover';
            }

        } else { // 선택 후 취소 되었을 때
                // -> 선택된 파일 없음 -> 미리보기 삭제
            preview[i].removeAttribute("src");
        }
    });


    // 미리보기 삭제 버튼(X버튼)
    deleteImage[i].addEventListener('click', () => {

        // 미리보기 이미지가 있을 경우
        if(preview[i].getAttribute("src") != ""){

            // 미리보기 삭제
            preview[i].removeAttribute("src");

            // input type="file" 태그의 value를 삭제
            // **  input type="file" 의 value는 ""(빈칸)만 대입 가능 **
            inputImage[i].value = "";
        }

        // 기본이미지로 재설정
        preview[i].setAttribute("src", "/images/adopt/카메라.png");
        preview[i].style.width = '9%';
        preview[i].style.height = '10%';
        preview[i].style.objectFit = 'cover';

    });
}



// info에 부족한 정보 있을 시 알림창 띄우기
document.getElementById('submitBtn').addEventListener('click', function(event) {
    
    // 필수 입력 필드들을 확인
    var name = document.getElementsByName('dogName')[0].value;
    var birthday = document.getElementsByName('dogBirthDay')[0].value;
    var gender = document.getElementsByName('dogGender')[0].value;
    var kind = document.getElementsByName('breedName')[0].value;
    var size = document.getElementsByName('dogSize')[0].value;
    var personality = document.getElementsByName('dogPersonality')[0].value;
    var neutering = document.getElementsByName('dogNeutering')[0].value;
    var vaccine = document.getElementsByName('dogVaccine')[0].value;
    var likeThings = document.getElementsByName('dogLike')[0].value;
    var hateThings = document.getElementsByName('dogHate')[0].value;
    var dogSpecialNote = document.getElementsByName('dogSpecialNote')[0].value;
    

    
    // 필수 입력 필드 중 하나라도 비어있으면 알림창 표시
    if (!name || !birthday || !gender || !kind || !size || !personality || !neutering || !vaccine || !likeThings || !hateThings || !dogSpecialNote) {
        
        // 이벤트 기본 동작 중단 (폼이 실제로 제출되지 않도록 함)
        event.preventDefault();
        
        alert('입력사항들을 모두 입력해주세요.');
    } 

	var sizeOption = document.getElementById('size').value;
	
	console.log(sizeOption);
	
	if (sizeOption === 'none') {
	    event.preventDefault();
	    alert('크기를 선택해주세요!');
	}
    
    
});


/*■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
        		입소신청 객체
■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■*/

if(admissionInfo != null){
	
	console.log("입소신청 등록하기에서 온 경우");
	console.log("admissionInfo.admProfile :" +admissionInfo.admProfile);

   // 썸네일 등록
   preview[0].setAttribute("src", admissionInfo.admProfile);
   preview[0].style.width = '100%';
   preview[0].style.height = '100%';
   preview[0].style.objectFit = 'cover'; 
   
   
   var textInput = document.querySelectorAll('input[type="text"]');
    console.log(admissionInfo.admBirthday);
    console.log(textInput[0]);
   // 이름 등록
   textInput[1].value = admissionInfo.admName;
   // 생일 등록
   textInput[2].value = admissionInfo.admBirthday.substr(0, 10);
   // 견종 등록
   textInput[3].value = admissionInfo.admBreed;
   
   //특이사항
   if(admissionInfo.admSignificant != '특이사항 없음'){
	  textInput[7].value = admissionInfo.admSignificant;
   }

   // 성별
   if(admissionInfo.admGender == '여아'){
	    var genderSelect = document.getElementById("gender");
	    
	    genderSelect.options[1].selected = true;
   }
   
  // 중성화
  if(admissionInfo.admNeuteringYN == 'Y'){
	    var surgerySelect = document.getElementById("surgery");
	    
	    surgerySelect.options[1].selected = true;
   }
   
   // 접종 유무
  if(admissionInfo.admVaccineYN == 'Y'){
	    var vaccineSelect = document.getElementById("Vaccine");
	    
	    vaccineSelect.options[1].selected = true;
   }
   
}












