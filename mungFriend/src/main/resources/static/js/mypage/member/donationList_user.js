/* 모달 요소 */
const modal = document.getElementById("regularModalBox");
const closeModalBtn = document.getElementById("regularClose");

// 모달 열기
function openCardInfo(merchant_uid){
    modal.style.display = "flex";

	//1) 몇 번 남았는지랑 merchant_uid 가 필요함

	// 1) 해당 결제 리스트 가져오기, 결제 남은 횟수 
	fetch('/donation/regular/detail' , {
		method:"POST",
		body: JSON.stringify(merchant_uid),
		headers: {
			"Content-Type": "application/json"
		}
	})
    .then(resp => resp.json())
    .then(function(list) {
		// 뿌려주기
		settingRegularInfo(list);
    })
    .catch(err => {
        throw err; 
    });
}


/*모달창 닫기 */
function closeCardInfo(){
    modal.style.display = "none"; 
}

closeModalBtn.addEventListener("click", function(){
    closeCardInfo();
});


function donationInfoCheck(receiptUrl, memberNo){

	console.log("영수증 :" + receiptUrl);	
	console.log("회원 정보 :" + memberNo);
	
	// 원래는 모달창 띄워서 영수증 보여주기, 정기결제 취소 
	// 1) 영수증 조회하게하기
	window.open(receiptUrl, '영수증', 'width=500, height=500, top = 250px, left = 700px, location = yes"');	
}

function regularInfo(billingKey, merchant_uid ,memberNo){

	console.log("billingkey :" + billingKey);
	console.log("merchant_uid :" + merchant_uid);
	console.log("memberNo :" + memberNo);
	
	//1) 열기
	openCardInfo(merchant_uid);
	

	// 2) 취소 




}

function settingRegularInfo(list){

	var count = 12 - list.length;

	console.log("남은개수 : " + count);

	const regularCount = document.getElementById("regularCount");
	regularCount.innerText = count + '번';

	// 1) 맨 마지막 리스트 값 가져오기
    const nextRegular = document.getElementById("nextRegular");
    console.log("next :" + (list.length - 1));
    if (list.length !== 12 && list.length > 0) {
        var next = list[0];

        console.log("next :" + JSON.stringify(next));

        var lastDate = next.paidAt;
        console.log("마지막 결제일 : " + lastDate);
    }
	

	// 가져온 list 데이터를 HTML에 추가
	const regularBody = document.querySelector(".regularBody");

	// 기존에 있는 데이터 삭제
	regularBody.innerHTML = '';

	// list 배열의 각 요소를 반복하면서 행을 생성하여 추가
	list.forEach(function (item, index) {
		var newRow = document.createElement("tr");
		newRow.classList.add("regularTheadTr");

		var td1 = document.createElement("td");
		td1.textContent = index + 1; // 예시: 리스트의 각 항목에 해당하는 속성값

		var td2 = document.createElement("td");
		td2.textContent = item.paidAt; 

		var td3 = document.createElement("td");
		td3.textContent = item.donationAmount; 

		var td4 = document.createElement("td");
		var div = document.createElement("div");
		div.textContent = "조회";
		div.classList.add("regularInfoCheck");
		div.addEventListener("click", function() {
			donationInfoCheck(item.receiptUrl,memberNo);
		}); 
		td4.appendChild(div);

		newRow.appendChild(td1);
		newRow.appendChild(td2);
		newRow.appendChild(td3);
		newRow.appendChild(td4);

		regularBody.appendChild(newRow);
	});


}



function donationCancel(billingKey,merchant_uid){

	console.log("billingkey :" + billingKey);
	console.log("merchant_uid :" + merchant_uid);
	
	//1) 취소
	fetch('/regular/schedule' , {
		method:"POST",
		body: JSON.stringify(billingKey),
		headers: {
			"Content-Type": "application/json"
		}
	})
    .then(resp => resp.json())
    .then(function(result) {
        console.log("결제 취소 성공?" + JSON.stringify(result));

		if(result.code == 0){
			console.log("결제 취소 내역이 있음.");

			// 1) 이후의 결제 내역 취소 

			


		}else if(result.code == 1){
			console.log("결제 취소 내역이 없음.");




		}else{
			console.log("오류");
		}



    })
    .catch(err => {
        throw err; 
    });
}