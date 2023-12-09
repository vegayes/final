function donationInfoCheck(receiptUrl, memberNo){

	console.log("영수증 :" + receiptUrl);	
	console.log("회원 정보 :" + memberNo);
	
	// 원래는 모달창 띄워서 영수증 보여주기, 정기결제 취소 
	// 1) 영수증 조회하게하기
	window.open(receiptUrl, '영수증', 'width=500, height=500, top = 250px, left = 700px, location = yes"');	
	
	
	
	
	
}


function donationCancel(billingKey,receiptUrl,memberNo){

	console.log("billingkey :" + billingKey);
	
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