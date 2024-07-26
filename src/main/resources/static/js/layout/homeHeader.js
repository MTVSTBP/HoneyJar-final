document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.querySelector('.search input');
    const searchIcon = document.querySelector('.search img');
    const map = document.getElementById('map');
    const searchResults = document.getElementById('searchResults');

    // 검색창 클릭 시 블러 효과 적용
    searchInput.addEventListener('click', function(e) {
        map.classList.add('blur');
        e.stopPropagation(); // 이벤트 버블링 방지
    });

    // 검색 아이콘 클릭 시 검색 결과 표시
    searchIcon.addEventListener('click', performSearch);

    // Enter 키 입력 시 검색 실행
    searchInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            performSearch();
        }
    });

    // 문서 전체에 클릭 이벤트 리스너 추가
    document.addEventListener('click', function(e) {
        // 클릭된 요소가 검색창이나 검색 결과가 아니면 블러 효과 제거
        if (!searchInput.contains(e.target) && !searchResults.contains(e.target)) {
            map.classList.remove('blur');
            searchResults.style.display = 'none';
        }
    });

    function performSearch() {
        const keyword = searchInput.value;
        if (keyword.trim() !== '') {
            // 카카오 장소 검색 API 사용
            const ps = new kakao.maps.services.Places();
            ps.keywordSearch(keyword, placesSearchCB);
        }
    }

    function placesSearchCB(data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {
            // 검색 결과 표시
            displayPlaces(data);
        } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
            alert('검색 결과가 존재하지 않습니다.');
        } else if (status === kakao.maps.services.Status.ERROR) {
            alert('검색 중 오류가 발생했습니다.');
        }
    }

    function displayPlaces(places) {
        searchResults.innerHTML = ''; // 기존 결과 초기화
        places.forEach((place, index) => {
            const item = document.createElement('div');
            item.className = 'search-item';
            item.textContent = place.place_name;
            item.addEventListener('click', function() {
                // 선택된 장소로 지도 이동 등의 처리
                console.log('Selected place:', place);
                searchResults.style.display = 'none';
                map.classList.remove('blur');
            });
            searchResults.appendChild(item);
        });
        searchResults.style.display = 'block';
    }
});