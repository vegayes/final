/* 모달 요소 */
const modal = document.getElementById("cardModalBox");
const closeModalBtn = document.getElementById("cardClose");

const subModal = document.getElementById("cardContentArea");

/* 모달창 띄우기 */
function openCardInfo(amount){
    modal.style.display = "flex";
    subModal.style.display = "flex";

    const regularAmount = document.getElementById("regularAmount");
    regularAmount.innerText = amount + "원";
}

/*모달창 닫기 */
function closeCardInfo(){
    modal.style.display = "none"; 
    subModal.style.display = "none";  
}


closeModalBtn.addEventListener("click", function(){
    closeCardInfo();
});


const regularBtn = document.getElementById("regularBtn");

function regularCardInfoCheck() {
	var regularFlag = true;

    var cardNumFlag = true;
    var expiryFlag = true;
	
	var cardNum = document.querySelectorAll('input[name="cardNum"]'); // 카드번호
	var expiry = document.querySelectorAll('input[name="expiry"]'); // 유효기간 
	var cardPw = document.querySelector('input[name = "cardPw"]').value; // 비밀번호 앞 2자리
	var birth = document.querySelector('input[name = "birth"]').value; // 생년월일
	var regularCheckBox = document.querySelector('.regularCheckBox input[type="checkbox"]:checked'); // 동의
    
    console. log(cardNum)
    console.log("cardPw :" + cardPw);

    for(let i = 0; i<cardNum.length; i++){
        if(cardNum[i].value.length != 4){ // 다 입력하지 않은거 
            cardNumFlag = false;
        }
    }

    for(let i = 0; i<expiry.length; i++){
        if(expiry[i].value.length != 2){ // 다 입력하지 않은거 
            expiryFlag = false;
        }
    }

    if(cardNumFlag == false){
        alert("카드 번호를 입력해주세요!");
        regularFlag = false;
        return;
    }

    if(expiryFlag == false){
        alert("유효기간을 입력해주세요!");
        regularFlag = false;
        return;
    }

    if(cardPw == ''){
        alert("카드 비밀번호를 입력해주세요!");
        regularFlag = false;
        return;
    }

    if(birth == ''){
        alert("생년월일을 입력해주세요!");
        regularFlag = false;
        return;
    }

    if(regularCheckBox == null){
        alert("정기결제 동의에 체크해주세요~");
		regularFlag = false;
		return;
    }
    
    if(regularFlag){
        cardInfo = {
            card1 : cardNum[0].value,
            card2 : cardNum[1].value,
            card3 : cardNum[2].value,
            card4 : cardNum[3].value,
    
            expiry1 : expiry[0].value,
            expiry2 : expiry[1].value,
    
            cardPw : cardPw,
            birth : birth,
        }

        return cardInfo;
    }
}


// 빌링키 발급 진행
function billingKeyCreate(cardInfo,memberNo){
    var customer_uid = 'Billing' + memberNo 
                                 + cardInfo.card1 + cardInfo.card4
                                 + cardInfo.card2 + cardInfo.card3;
                                 // 카드번호랑 사용자 번호로 빌링키 만들어도되나? 몰라
    
    console.log("customer_uid :" + customer_uid);

	var billing = {
		pg : 'html5_inicis',
        customer_uid : customer_uid,
		cardNumber : `${cardInfo.card1}-${cardInfo.card2}-${cardInfo.card3}-${cardInfo.card4}`,
		cardExpiry : `20${cardInfo.expiry2}-${cardInfo.expiry1}`,
		birth : cardInfo.birth,
		pwd2Digit : cardInfo.cardPw,
    }
    return billing;
}


// 빌링키 조회 진행
function billingKeyCheck(customer_uid){

    return fetch("/billingkey/" + customer_uid)
        .then(resp => resp.json())
        .then(function(billing) {
            return billing.code; // Promise 체인을 따라 최종적으로 코드 반환
        })
        .catch(err => {
            throw err; // 에러를 호출자에게 다시 전달할 수 있습니다.
        });
}


// 정기결제 1회 결제 실행
function regularOnePay(paymentData, donationContent, donationType){
    console.log("1회결제 :" + JSON.stringify(paymentData));
    return fetch('/payment/one/' + paymentData.merchantUid, {
        method:"POST",
        body: JSON.stringify(paymentData),
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(resp => resp.json())
        .then(function(onePay) {
            alert("정기 결제 첫 회 성공")
            console.log("정기결제 1회 ");
            console.log(onePay);

            var donation = {
							
                impUid: onePay.response.impUid,
                applyNum :onePay.response.applyNum,
                merchantUid: onePay.response.merchantUid,
                cardName :onePay.response.cardName,
                paidAt: onePay.response.paidAt,
                receiptUrl : onePay.response.receiptUrl,
                status : onePay.response.status,

                memberName : onePay.response.buyerName,
                memberEmail : onePay.response.buyerEmail,
                
                donationContent : donationContent.value,
                donationType : donationType.value,
                donationAmount : onePay.response.amount,
                
                billingKey : onePay.response.customerUid,
                
            };

            paymentDBInput(donation);

        })
        .catch(err => {
            console.log(err);
            throw err; 
        });

}