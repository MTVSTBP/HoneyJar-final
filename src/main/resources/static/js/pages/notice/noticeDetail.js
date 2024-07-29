document.addEventListener('DOMContentLoaded', function() {
    var backButton = document.getElementById('back-button');
    
    backButton.addEventListener('click', function(event) {
        event.preventDefault(); // 기본 링크 동작 방지
        window.history.back(); // 브라우저의 뒤로 가기 동작 수행
    });
});