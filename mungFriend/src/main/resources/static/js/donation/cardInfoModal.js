/* 모달 요소 */
const modal = document.getElementById("cardModalBox");
const closeModalBtn = document.getElementById("cardClose");

const subModal = document.getElementById("cardContentArea");

/* 모달창 띄우기 */
function openCardInfo(){
    modal.style.display = "flex";
    subModal.style.display = "flex";
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

    cardInfo = {
        card1 : cardNum[0].value,
        card2 : cardNum[1].value,
        card3 : cardNum[2].value,
        card4 : cardNum[3].value,

        expiry1 : expiry[0].value,
        expiry2 : expiry[1].value,

        cardpw : cardpw,
        birth : birth,
    }

    return cardInfo;

}

