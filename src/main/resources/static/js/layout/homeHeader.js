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
            window.searchAndDisplayMarkers(keyword);
        }
    }
});
