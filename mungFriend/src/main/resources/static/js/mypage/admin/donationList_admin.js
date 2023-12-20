/* 모달 요소 */
const modal = document.getElementById("regularModalBox");
const closeModalBtn = document.getElementById("regularClose");

// 모달 열기
function openCardInfo(merchant_uid){
    modal.style.display = "flex";

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

		settingRegularInfo(list);
		

		// merchant  마지막 값 가져오기
		const mechantList = [];
		const terminationBtn = document.getElementById("terminationBtn");
		const merchantLast = list[0].merchantUid;
		const billingKey = list[0].billingKey;

		const parts = merchantLast.split('_');
		const lastPart = parts[parts.length - 1];

		
		console.log(lastPart);
	    
		if(lastPart != 12){
			console.log("들어옴:");
			const startIndex = parseInt(lastPart) + 1;
			const endIndex = 12;
		  
			for (let i = startIndex; i <= endIndex; i++) {
			  const newMerchant = parts.slice(0, parts.length - 1).join('_') + '_' + i;
			  mechantList.push(newMerchant);
			}
		  
			console.log(mechantList); 
		}

	
		if(terminationBtn != null){
			terminationBtn.addEventListener("click", function(){
	
				donationCancel(billingKey, mechantList);
	
			})
		}
	

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


// 선택한 후원자 정보 가져오기
function adminRegular(donationNo){
	// 후원 정보 가져오기
	fetch("/donation/memberInfo?donationNo="+donationNo)
    .then(resp => resp.json())
    .then(function(donation) {
		console.log("후원자 정보 :" + donation); 
		regularInfo(donation.billingKey, donation.merchantUid, donation.memberName)
    })
    .catch(err => {
        throw err; 
    });
}


function regularInfo(billingKey, merchant_uid ,memberName){

	console.log("billingkey :" + billingKey);
	console.log("merchant_uid :" + merchant_uid);
	console.log("memberNo :" + memberName);
	
	//1) 열기
	openCardInfo(merchant_uid);
	
	const donationName = document.getElementById("donationName");
	donationName.innerText = memberName +"님의 정기결제 내역";
	
}





function settingRegularInfo(list){
	
	//1) 남은 정기 일수 
	var count = list.length;

	const regularCount = document.getElementById("regularCount");
	const leftTitle = document.getElementById("leftTitle");
	leftTitle.innerText = '결제된 횟수';
	regularCount.innerText = count + '번';


	// 2) 다음 결제일
    const nextRegular = document.getElementById("nextRegular");
    console.log("next :" + (list.length - 1));
    if (list.length !== 12 && list.length > 0) {
        var next = list[0];

		console.log("next :" + JSON.stringify(next));

		var lastDate = next.paidAt;
		console.log("마지막 결제일 : " + lastDate);
	
		// 문자열을 Date 객체로 변환
		var currentDate = new Date(lastDate);
	
		// 날짜를 다음 달로 업데이트
		currentDate.setMonth(currentDate.getMonth() + 1);
	
		// 새로운 월과 연도 값을 가져오기
		var newMonth = currentDate.getMonth() + 1; // getMonth()는 0부터 시작하기 때문에 1을 더합니다.
		var newYear = currentDate.getFullYear();
	
		// "yyyy-mm-dd" 형식의 새로운 날짜 문자열 생성 (시간 부분을 제거)
		var nextMonthDate = newYear + '-' + (newMonth < 10 ? '0' : '') + newMonth + '-' + (currentDate.getDate() < 10 ? '0' : '') + currentDate.getDate();
	
		nextRegular.innerText = nextMonthDate;
    }
	
	
	// 3) 정기 결제 기간
	const regularPeriod = document.getElementById("regularPeriod");
	if (list.length > 0) {
		var first = list[list.length - 1];
	
		console.log("first :" + JSON.stringify(first));
	
		var firstString = first.paidAt;
		console.log("첫번 째 : " + firstString);
	
		// 문자열을 Date 객체로 변환
		var firstDate = new Date(firstString);
	
		// 첫 결제일을 "yyyy-mm-dd" 형식의 문자열로 변환
		var firstYear = firstDate.getFullYear();
		var firstMonth = (firstDate.getMonth() + 1 < 10 ? '0' : '') + (firstDate.getMonth() + 1);
		var firstDay = (firstDate.getDate() < 10 ? '0' : '') + firstDate.getDate();
		var firstDateFormatted = firstYear + '-' + firstMonth + '-' + firstDay;
	
		console.log("첫 결제일 (yyyy-mm-dd) : " + firstDateFormatted);
	
		// 첫 결제일에서 11개월 후의 날짜를 계산
		firstDate.setMonth(firstDate.getMonth() + 11);
	
		// 11개월 후의 연도, 월, 일 값을 가져오기
		var nextYear = firstDate.getFullYear();
		var nextMonth = (firstDate.getMonth() + 1 < 10 ? '0' : '') + (firstDate.getMonth() + 1);
		var nextDay = (firstDate.getDate() < 10 ? '0' : '') + firstDate.getDate();
	
		// 11개월 후의 날짜를 "yyyy-mm-dd" 형식의 문자열로 변환
		var periodDateFormatted = nextYear + '-' + nextMonth + '-' + nextDay;
	
		console.log("11개월 후 결제일 (yyyy-mm-dd) : " + periodDateFormatted);
	
		// 결과를 엘리먼트에 할당
		regularPeriod.innerText = ` 정기 결제 기간 : ${firstDateFormatted} ~ ${periodDateFormatted}`;
	}
	

	// 4) 결제 수단 / 정기 결제 금액 
	const regularAmount = document.getElementById("regularAmount");
	regularAmount.innerText =  '정기 결제 금액 : ' + list[0].donationAmount + '원';
	
	const regularMethod = document.getElementById("regularMethod");
	regularMethod.innerText = '결제 수단 : ' + list[0].cardName ;


	// 6) 정기 결제 내역
	// 새로운 tbody 요소 생성
	var newTbody = document.createElement("tbody");
	newTbody.classList.add("regularBody");

	// list 배열의 각 요소를 반복하면서 행을 생성하여 추가
	list.forEach(function (item, index) {
		var newRow = document.createElement("tr");
		newRow.classList.add("regularTheadTr");

		var td1 = document.createElement("td");
		td1.textContent = index + 1; // 번호

		var td2 = document.createElement("td");
		td2.textContent = item.paidAt; // 결제 일시

		var td3 = document.createElement("td");
		td3.textContent = item.donationAmount; // 후원 금액


		newRow.appendChild(td1);
		newRow.appendChild(td2);
		newRow.appendChild(td3);

		newTbody.appendChild(newRow);
	});

	// 기존의 tbody 요소를 새로운 tbody로 교체하는 코드
	var oldTbody = document.querySelector('.regularBody');
	var parentDiv = oldTbody.parentElement;
	parentDiv.replaceChild(newTbody, oldTbody);

	// 총 후원 금액 계산
	var totalDonation = list.reduce(function (total, item) {
		return total + item.donationAmount;
	}, 0);

	// HTML에 총 후원 금액을 업데이트
	var sumAmount = document.getElementById("sumAdminAmount");
	sumAmount.innerText = `후원 총 금액 :  ${totalDonation} 원`;


	if(list.length == 12 || list[list.length - 1].cancelYN == 'Y'){
		if(list[list.length - 1].cancelYN == 'Y'){			
			nextRegular.innerText = `취소됨`;
		}else if (list.length == 12){
			nextRegular.innerText = `정기결제 완료`;
		}
		
		//cancelCheck(merchant);
		
		var countDonation = list.length;
		
		leftTitle.innerText = '결제 된 횟수';
		regularCount.innerText = countDonation + '번';
	}

	
}

/*
function cancelCheck(merchant){
	console.log("merchant" + merchant);
	const parts = merchant.split('_');
	const merchantData = parts.slice(0, parts.length - 1).join('_');
	console.log("merchantData" + merchantData);

	fetch('/regular/cancelCheck' , {
		method:"POST",
		body: JSON.stringify(merchantData),
		headers: {
			"Content-Type": "application/json"
		}
	})
    .then(resp => resp.text())
    .then(function(result) {

		if(result >0){
			const nextRegular = document.getElementById("nextRegular");
			nextRegular.innerText = `취소됨`;
			
			const leftTitle = document.getElementById("leftTitle");
			leftTitle.innerText = '결제 된 횟수';
			
			const lastParts = merchant.split('_');
			const lastPart = parts[parts.length - 1];
			
			regularCount.innerText = (parseInt(lastPart) - 1) + '번';
		}
    })
    .catch(err => {
        throw err; 
    });

}
*/