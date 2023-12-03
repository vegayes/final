// --------------------- 활동일지 등록하기 유효성 검사 ---------------------

// 등록하기 form 태그가 제출 되었을 때
document.getElementById("register-form").addEventListener("submit", e => {
	
	// **** 사진은 not null이고, 내용은 null 이라서 사진 업로드 했는지만 체크 ****
	
	// initCheck
	// 초기 이미지 상태를 저장하는 변수
	// false == 기본 이미지, true == DB에서 가져온 이미지
	
	// deleteCheck
	// 활동 사진이 새로 업로드 되거나 삭제 되었음을 나타내는 변수
	// -1 == 초기값 , 0 ==  활동사진 삭제(이미지 삭제 버튼) , 1 == 새 이미지 업로드
	
	let flag = true; // 제출하면 안되는 경우의 초기값 플래그 true로 지정
	
	// 이전 이미지가 없으면서, 새 이미지 업로드를 했다 -> 처음으로 이미지 추가
	if(!initCheck && deleteCheck == 1) flag = false;
	
	// 이전 이미지가 있으면서, 새 이미지 업로드를 했다 -> 새 이미지로 변경
	if(initCheck && deleteCheck == 1) flag = false;
	
	// 이전 이미지가 있으면서, 이미지 삭제 버튼을 눌렀다 -> 삭제
	if(initCheck && deleteCheck == 0) falg = false;
	
	if(flag) { // flag == true -> 제출하면 안되는 경우
		e.preventDefault(); // form 기본 이벤트 제거
		alert("활동사진 업로드 후 버튼을 클릭해주세요");
	}
	
	return true;
	
});

// ---------------------------------------------------------------

// 활동 사진 추가/변경/삭제
const activityImage = document.getElementById("noImage"); // img 태그 
const imageInput = document.getElementById("uploadInput"); // input 태그
const deleteImage = document.getElementById("img-del-btn"); // 이미지 삭제 버튼

let initCheck; // 초기 이미지 상태를 저장하는 변수
			   // false == 기본 이미지, true == DB에서 가져온 이미지
			   
let deleteCheck = -1;
// 활동 사진이 새로 업로드 되거나 삭제 되었음을 나타내는 변수
// -1 == 초기값 , 0 == 활동사진 삭제(이미지 삭제 버튼) , 1 == 새 이미지 업로드


let originalImage; // 초기 이미지 파일 경로 저장

if(imageInput != null) { // 화면에 imageInput이 있을 경우

	// 이미지가 출력되는 img 태그의 src 속성을 저장
	originalImage = activityImage.getAttribute("src");
	
	// 화면 진입 시
	// 현재 이미지 상태 확인
	if(activityImage.getAttribute("src") == "/images/PM/noImage.png") {
		// 기본 이미지인 경우
		initCheck = false;
	} else {
		initCheck = true;
	}
	
	
	// chage 이벤트 : 값이 변했을 때
	// - input type="file", "checkbox", "radio" 에서 많이 사용
	// - text/number 형식 사용 가능
	//   -> 이 때 input값 입력 후 포커스를 잃었을 때
	//		이전 값과 다르면 change 이벤트 발생
	
	imageInput.addEventListener("change", e => {
		
		// 2MB로 최대 크기 제한
		const maxSize = 1 * 1024 * 1024 * 2; // 파일 최대 크기 지정(바이트 단위)
		
		console.log(e.target); // input
		console.log(e.target.value); // 업로드된 파일 경로
		console.log(e.target.files); // 업로드된 파일의 정보가 담긴 배열
		
		const file = e.target.files[0]; // 업로드한 파일의 정보가 담긴 객체
		
		
		// 파일을 한번 선택한 후 취소했을 때 (file이 undefinde가 된다)
		if(file == undefined) {
			console.log("파일 선택이 취소됨");
			deleteCheck = -1; // 취소 == 파일 없음 == 초기상태
			
			// 취소 시 기존 이미지로 변경 (기존 이미지에서 변경되는게 없게 하겠다)
			activityImage.setAttribute("src", originalImage);
			
			return;
		}
		
		if( file.size > maxSize ) { // 선택된 파일의 크기가 최대 크기를 초과한 경우
			alert("2MB 이하의 이미지를 선택해주세요.");
			imageInput.value = "";
			// input type="file" 태그에 대입할 수 있는 value는 "" (빈칸) 뿐이다!
			deleteCheck = -1; // 취소 == 파일 없음 == 초기상태
			
			// 기존 이미지로 변경
			activityImage.setAttribute("src", originalImage);
			
			return;
		}
		
		// JS에서 파일을 읽는 객체
		// - 파일을 읽고 클라이언트 컴퓨터에 파일을 저장할 수 있음
		const reader = new FileReader();
		
		reader.readAsDataURL(file);
		// 매개변수에 작성된 파일을 읽어서 저장 후
		// 파일을 나타내는 URL을 result 속성으로 얻어올 수 있게 함.
		
		// 다 읽었을 때
		reader.onload = e => {
			// console.log(e.target);
			console.log(e.target.result); // 읽은 파일의 URL
			/* 개발자도구에서 Application탭에서 Frames > top > images 안에서 확인가능 */
			
			const url = e.target.result;
			
			// 활동사진(img) 태그에 src 속성으로 추가
			activityImage.setAttribute("src", url);
			
			deleteCheck = 1;
		}
		
	});
	
	
	// 이미지 삭제 버튼 클릭 시
	deleteImage.addEventListener('click', () => {
		imageInput.value = ""; // input type="file"의 value 삭제
		
		activityImage.setAttribute("src", "/images/PM/noImage.png");
		// 이미지를 기본 이미지로 변경
		
		deleteCheck = 0;
	});
	
	
	
}
