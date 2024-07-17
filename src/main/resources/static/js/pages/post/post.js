document.addEventListener("DOMContentLoaded", function () {
    // 체크박스 필터링 기능 설정
    // document.getElementById('goodRestaurant').addEventListener('change', function() {
    //     // 체크 상태에 따라 필터링 기능 구현
    //     if (this.checked) {
    //         filterGoodRestaurants(true);
    //     } else {
    //         filterGoodRestaurants(false);
    //     }
    // });

    function filterGoodRestaurants(isChecked) {
        // 필터링 로직 구현
        // if (isChecked) {
        //     console.log("착한 맛집만 보이도록 필터링");
        //     // 예: 착한 맛집만 보이도록 하는 코드 추가
        // } else {
        //     console.log("모든 맛집 보이도록 필터링 해제");
        //     // 예: 모든 맛집 보이도록 하는 코드 추가
        // }
    }

    // 북마크 버튼 이벤트 설정
    const bookmarkButtons = document.querySelectorAll(".bookmark");

    bookmarkButtons.forEach(button => {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            const bookmarkImage = this.querySelector("img");
            const isBookmarked = bookmarkImage.src.includes("/assets/svg/bookmark_border.svg");

            // AJAX 요청 보내기 (북마크 상태 변경)
            /*
            sendBookmarkData(isBookmarked).then(response => {
                if (response.success) {
                    // 이미지 업데이트
                    bookmarkImage.src = isBookmarked ? "/assets/svg/bookmark.svg" : "/assets/svg/bookmark_border.svg";
                } else {
                    console.error('Bookmark update failed');
                }
            });
            */

            // 임시로 이미지 업데이트
            bookmarkImage.src = isBookmarked ? "/assets/svg/bookmark.svg" : "/assets/svg/bookmark_border.svg";
        });
    });
});
