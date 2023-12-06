// 결제 진행 정보 확인 (일반 결제 , 정기 결제 구분)
function paymentInfoCheck(){
	var donationFlag = true;
	
	var donationType = document.querySelector('input[name="donationType"]:checked'); // 후원 주기
	var donationAmount = document.querySelector('input[name="donationAmount"]:checked'); // 후원 금액 
	var donationCheck = document.querySelectorAll('.donationProcessCheck input[type="checkbox"]:checked'); // 후원 동의
	var donationContent = document.querySelector('.donationContent'); // 후원 내용
	
	var donationName = ''; // 후원자 이름
	var donationEmail = ''; // 후원자 이메일
	
	
	
	if(loginMember == null){
		var donationInputName = document.querySelector('input[name="donationName"]').value; // 후원자 입력 이름 (비회원)
    	var donationInputEmail = document.querySelector('input[name="donationEmail"]').value; // 후원자 입력 이메일 (비회원)
		console.log("로그인안됨");
		
		// 입력값 유효성?
		if(donationInputName.length == 0 ){
			alert("후원자 이름을 작성해주세요!")
			document.querySelector('input[name="donationName').focus();
			return;
		}
		if(donationInputEmail.length == 0){
			alert("후원자 이메일을 작성해주세요!")
			document.querySelector('input[name="donationEmail').focus();
			return;
		}
	
		donationName = donationInputName;
		donationEmail = donationInputEmail;
		console.log("로그인안됨 이룸 : " + donationName);
		console.log("로그인안됨 이메일 : " + donationInputEmail);
	}else{
		console.log("로그인됨");		
		donationName =  loginMember.memberName;
		donationEmail =  loginMember.memberEmail;
	}
		
	/* 후원하기의 체크된 내용 확인하기*/
	if(donationType == null){ // 후원하기 주기가 체크가 되지 않았다면 다시 실행하게 하기
		alert("후원 주기를 체크해주세요~");
		donationFlag = false;
		return;
	}else if(donationAmount == null){
		alert("후원 금액을 선택해주세요~");
		donationFlag = false;
		return;
	}else if (donationCheck.length != 2){
		alert("동의해주세요");
		donationFlag = false;
/*		const donation
		donationCheck.focus();
*/
		return;	
	}
	
	/* 직접입력의 경우*/	
	if(donationAmount.value == 'input'){
		console.log("금액이 input으로 눌림");	
		
		/* 재사용 */
		donationAmount = document.querySelector('#inputAmount'); // 입력된 후원 금액 
		
		console.log("입력된 후원 금액 : " + donationAmount.value);	
		
		if(donationAmount == 0){
			alert("후원 금액을 입력해주세요!");
			donationAmount.focus();
			return ;
		}
		
	}	
	
	if(!donationFlag){	// 결제창 못감. 
		return;
	}else{
        // 결제정보를 결제api 진행하면서 넣어야 하는거 아니야? 왜 미리 넣어?
        // => 미리 넣는다고 했을 때 loginMember의 정보를 넣음
	    console.log("type : " + donationType.value);
	    console.log("amount : " + donationAmount.value);
	    
	    // 결제 정보
	    var paymentData = {
	            pg: "html5_inicis",		//KG이니시스 pg파라미터 값
	            pay_method: "card",		//결제 방법
	            merchant_uid: "donation_" + new Date().getTime(),//주문번호 전달
		        // 라디오 버튼에서 선택한 값을 결제 정보에 추가
		        name: '멍프랜드 ' + (donationType.value === '일시' ? '일시 후원' : '정기 후원'),
		        amount: parseInt(donationAmount.value), // 문자열을 숫자로 변환하여 저장	  
		        buyer_name : donationName,
		        buyer_email : donationEmail,
		};
		
	if(donationType.value === '일시'){
		requestPay(paymentData);
	}else if(donationType.value === '정기'){
		console.log("정기는 카드 번호 입력해야함");

		var codeHH = billingKeyCreate();

		console.log("codeHH" + JSON.stringify(codeHH));


		customer_uid = 'CUSTOMER_UID' + loginMember.memberNo;

		


		var cardData = {
			pg : 'html5_inicis',
			customer_uid : customer_uid,
			cardNumber : "5107-3792-7333-9589",
			cardExpiry : "2028-08",
			birth : "010328",
			pwd2Digit : "65",
		}

		
		

		


		console.log(JSON.stringify(cardData));
					
		// 결제검증
		fetch('/billingkey/' + customer_uid, {
			method:"POST",
			body: JSON.stringify(cardData),
			headers: {
				"Content-Type": "application/json"
			}
		})
		.then(function(response) {
			return response.json();
		})
		.then(function(data) {
				console.log("들어옴");
				console.log(data);
		})
		.catch(function(error) {
			alert("결제에 실패하였습니다.", "에러 내용: " + error, "error");
		});


	}else {
		console.log("뭔가 잘못됨.");
	}
	
	
	
}


// 빌링키 발급 진행
function billingKeyCreate(){

	var hh = {
		pg : 'html5_inicis',
		cardNumber : "5107-3792-7333-9589",
		cardExpiry : "2028-08",
		birth : "010328",
		pwd2Digit : "65",
	}

	return hh;
}







// 결제 진행 
function requestPay(paymentData) {
	var IMP = window.IMP;
	    IMP.init("imp82107782");
	
	    IMP.request_pay(paymentData,
	        function (rsp) {
				
		        if (rsp.success) { // 결제 성공
					alert("결제 성공");
					
					console.log("결제 성공 후 imp_uid 값 : " + rsp.imp_uid);
					
					fetch('/verifyIamport/' + rsp.imp_uid, {
					    method: 'POST'
					})
					.then(function(response) {
					    return response.json();
					})
					.then(function(data) {
							
			            var donation = {
							
	                        impUid: data.response.impUid,
	                        applyNum :data.response.applyNum,
	                        merchantUid: data.response.merchantUid,
	                        cardName :data.response.cardName,
	                        paidAt: data.response.paidAt,
	                        receiptUrl : data.response.receiptUrl,
	                        status : data.response.status,
							//billingKey : rsp.customer_uid
	
							memberName : data.response.buyerName,
							memberEmail : data.response.buyerEmail,
	                        
	                        donationContent : donationContent.value,
							donationType : donationType.value,
							donationAmount : data.response.amount,
							
			            };
				
						
			            // 컨트롤러에 데이터를 전달하여 DB에 입력하는 로직
			            fetch("/donation/donationPay", {
			                method: "POST",
			                body: JSON.stringify(donation),
			                headers: {
			                    "Content-Type": "application/json"
			                }
			            })
			            .then(response => response.text())
			            .then(result => {
			                if (result === "y") {
								console.log("안녕 나 결제 DB 성공하고 들어옴.")
			                
			                    if(loginMember != null){
									console.log("넘어갈거임.")
									location.href = '/mypage/member/donationList';
									 // 로그인되면 후원 내역으로 넘어가기
									 return; 
								}
			                    location.href = '/';
			                   
			                } else {
			                    alert("DB 후원 정보 입력 실패");
			                    return false;
			                }
			            })
			            .catch(function(error) {
					    	alert("오류가 발생하였습니다.", "에러 내용: " + error, "error");
						});				
				
					})
					.catch(function(error) {
					    alert("결제에 실패하였습니다.", "에러 내용: " + error, "error");
					});
				}
			});

	  }
}