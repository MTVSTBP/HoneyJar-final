document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.querySelector('.search input');
    const searchButton = document.querySelector('.search img');

    searchButton.addEventListener('click', performSearch);
    searchInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            performSearch();
        }
    });

    function performSearch() {
        const keyword = searchInput.value.trim();
        if (keyword !== '') {
            // home.js의 searchAndDisplayMarkers 함수 호출
            if (typeof searchAndDisplayMarkers === "function") {
                searchAndDisplayMarkers(keyword);
            }
        }
    }
});