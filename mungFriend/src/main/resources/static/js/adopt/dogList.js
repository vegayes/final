function getSelectedFilters() {
    return {
        dogBirthDay: document.getElementById('age').value,
        dogGender: document.getElementById('gender').value,
        breedName: document.getElementById('kind').value,
        dogSize: document.getElementById('size').value,
        dogNeutering: document.getElementById('surgery').value,
    };
}

function submitFilter() {

    const selectedFilters = getSelectedFilters();

    console.log(selectedFilters);
    
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

         // dog-list 업데이트
         updateDogList(data.dogList);

         // 페이지네이션 업데이트
         updatePagination(data.pagination);
    })
    .catch(error => {
        console.error('Error during filter submission:', error);
    });
}



// 유기견 목록 재로딩
function updateDogList(dogList) {
    const firstContainer = document.getElementById('firstContainer');
    const secondContainer = document.getElementById('secondContainer');
    // const dogListContainer = document.getElementById('dog-list');

    firstContainer.innerHTML = ''; // 이전에 표시된 내용을 초기화
    secondContainer.innerHTML = ''; // 이전에 표시된 내용을 초기화

    if (dogList.length === 0) {
        // 입양리스트가 존재하지 않을 때 처리
        const noResultParagraph = document.createElement('p');
        noResultParagraph.style.fontSize = '12px';
        noResultParagraph.textContent = '검색하신 결과에 맞는 입양리스트가 존재하지 않습니다.';
        
        firstContainer.appendChild(secondContainer);
        secondContainer.appendChild(noResultParagraph);

        secondContainer.style.display = 'flex';
        secondContainer.style.width = '100%';
        secondContainer.style.justifyContent = 'center';
        secondContainer.style.alignItems = 'center';
        secondContainer.style.lineHeight = '300px';
    } else {
        // 입양리스트가 존재할 때 처리
        dogList.forEach(dog => {
            const dogItem = document.createElement('div');
            dogItem.innerHTML = `
                <a href="/adopt/dogList/${dog.dogNo}">
                    <img src="${dog.imgPath + dog.imgRename}" id="dog-img">
                </a>
                <div id="dog-kind">${dog.dogName}</div>
            `;

            firstContainer.appendChild(secondContainer);
            secondContainer.appendChild(dogItem);

            secondContainer.style.display = 'grid';
            secondContainer.style.width = '85%';
            secondContainer.style.gridTemplateColumns = 'repeat(4, 1fr)';
            secondContainer.style.gridColumnGap = '0px';
            secondContainer.style.gridRowGap = '20px';

            secondContainer.style.removeProperty('justify-content');
            secondContainer.style.removeProperty('align-items');
            secondContainer.style.removeProperty('line-height');

            dogItem.style.display = 'flex';
            dogItem.style.flexDirection = 'column';
            dogItem.style.justifyContent = 'center';
            dogItem.style.alignItems = 'center';
        });
    }
}


// 페이지네이션 재로딩
function updatePagination(pagination) {
    const paginationContainer = document.querySelector('.pagination');
    paginationContainer.innerHTML = ''; // 이전에 표시된 페이지네이션 내용을 초기화

    // 첫 페이지로 이동
    paginationContainer.appendChild(createPageLink(1, '<<'));

    // 이전 목록 마지막 번호로 이동
    paginationContainer.appendChild(createPageLink(pagination.prevPage, '<'));

    // 특정 페이지로 이동
    for (let i = pagination.startPage; i <= pagination.endPage; i++) {

        if (i === pagination.currentPage) { // 현재페이지
            paginationContainer.appendChild(createCurrentPageLink(i));
        } else { // 나머지페이지
            paginationContainer.appendChild(createPageLink(i, i));
        }
    }

    // 다음 목록 시작 번호로 이동
    paginationContainer.appendChild(createPageLink(pagination.nextPage, '>'));

    // 끝 페이지로 이동
    paginationContainer.appendChild(createPageLink(pagination.maxPage, '>>'));

    // 페이지 링크에 이벤트 리스너 추가
    const pageLinks = document.querySelectorAll('.pagination a');

    pageLinks.forEach(link => {
        link.addEventListener('click', function (event) {
            event.preventDefault();
            const page = this.getAttribute('data-page');
            updateContent(page); // 페이지에 맞는 내용을 로드하는 함수 호출
            history.pushState({ page: page }, null, `/adopt/dogList?cp=${page}`);
        });
    });

}

function createPageLink(page, text) {
    const link = document.createElement('li');
    const anchor = document.createElement('a');
    anchor.setAttribute('data-page', page);
    anchor.textContent = text;
    link.appendChild(anchor);
    return link;
}

function createCurrentPageLink(page) {
    const link = document.createElement('li');
    const anchor = document.createElement('a');
    anchor.classList.add('current');
    anchor.href = `/adopt/dogList?cp=${page}`;
    anchor.textContent = page;
    link.appendChild(anchor);
    return link;
}

// History API의 상태(popstate) 변경 시 이벤트 처리
window.addEventListener('popstate', function (event) {
    const page = event.state.page;
    if (page) {
        updateContent(page); // 페이지에 맞는 내용을 로드하는 함수 호출
    }

    // if (page) {
    //     // 클라이언트가 보낸 필터 값 가져오기
    //     const selectedFilters = getSelectedFilters();

    //     // 서버에 데이터 요청
    //     fetch(`/adopt/dogList?cp=${page}`, {
    //         method: 'POST',
    //         headers: { 'Content-Type': 'application/json', },
    //         body: JSON.stringify(selectedFilters),
    //     })
    //     .then(response => response.json())
    //     .then(data => {
    //         // 서버에서 받은 데이터를 이용하여 강아지 목록과 페이지네이션 업데이트
    //         updateDogList(data.dogList);
    //         updatePagination(data.pagination);
    //     })
    //     .catch(error => {
    //         console.error('콘텐츠 업데이트 중 오류 발생:', error);
    //     });
    // }
});

// 페이지에 맞는 내용을 로드하는 함수 (updateContent 함수는 실제로 내용을 업데이트하는 함수로 교체)
function updateContent(page) {
    // 클라이언트가 보낸 필터 값 가져오기
    const selectedFilters = getSelectedFilters();

    // 서버에 데이터 요청
    fetch(`/adopt/dogList?cp=${page}`, {  // 여기서 cp를 사용하도록 수정
        method: 'POST',
        headers: { 'Content-Type': 'application/json', },
        body: JSON.stringify(selectedFilters),
    })
    .then(response => response.json())
    .then(data => {
        // 서버에서 받은 데이터를 이용하여 강아지 목록과 페이지네이션 업데이트
        updateDogList(data.dogList);
        updatePagination(data.pagination);
    })
    .catch(error => {
        console.error('Error during content update:', error);
    });
}
