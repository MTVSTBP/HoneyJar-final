document.addEventListener('DOMContentLoaded', function() {
    // 페이지가 로드될 때 모든 링크의 상태를 확인
    document.querySelectorAll('.notice_link').forEach(function(link) {
        var item = link.querySelector('.item');
        var itemId = link.getAttribute('href'); // 링크의 고유 식별자

        // 항목이 읽음 상태인지 확인
        if (localStorage.getItem(itemId) === 'read') {
            changeItemColor(item, '#D9D9D9');
        }

        link.addEventListener('click', function(event) {
            event.preventDefault(); // 기본 링크 동작 방지

            // 항목을 읽음 상태로 표시
            localStorage.setItem(itemId, 'read');
            changeItemColor(item, '#D9D9D9');

            // 링크 동작 시뮬레이션 (실제 내비게이션으로 대체 가능)
            setTimeout(function() {
                window.location.href = link.getAttribute('href');
            }, 100);
        });
    });
});

// 항목의 색상을 변경하는 함수
function changeItemColor(item, color) {
    item.querySelector('.title').style.color = color;
    item.querySelector('.context').style.color = color;
    item.querySelector('span').style.color = color;
}
