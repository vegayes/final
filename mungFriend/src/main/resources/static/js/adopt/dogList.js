function submitFilter() {
    // 선택된 필터 값 가져오기
    const selectedFilters = {
        dogBirthDay: document.getElementById('age').value,
        dogGender: document.getElementById('gender').value,
        breedName: document.getElementById('kind').value,
        dogSize: document.getElementById('size').value,
        dogNeutering: document.getElementById('surgery').value,
    };

    // 서버로 데이터 전송 (AJAX 사용 예시)
    fetch('/adopt/dogList', {
        method: 'POST',
        headers: {'Content-Type': 'application/json',},
        body: JSON.stringify(selectedFilters),
    })
    .then(response => response.json())
    .then(data => {
        // 서버에서 받은 데이터를 처리하는 로직 작성
        console.log(data);
        console.log(selectedFilters);
        // window.location.reload();
    })
    .catch(error => {
        console.error('Error during filter submission:', error);
    });
}