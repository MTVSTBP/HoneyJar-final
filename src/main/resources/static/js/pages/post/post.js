document.addEventListener("DOMContentLoaded", function () {
    const bookmarkButtons = document.querySelectorAll(".bookmark");
    let currentPage = 1; // 현재 페이지를 1로 시작
    const postsPerPage = 6; // 한 번에 로드할 포스트 수
    let isLoading = false; // 로딩 상태를 추적
    let timeoutId;

    // 북마크 버튼 클릭 이벤트 처리
    bookmarkButtons.forEach(button => {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            const form = this.querySelector("form");
            const formData = new FormData(form);

            fetch(form.action, {
                method: "POST",
                body: formData
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        const bookmarkImage = this.querySelector("img");
                        if (data.bookmarked) {
                            bookmarkImage.src = "/assets/svg/bookmark.svg";
                        } else {
                            bookmarkImage.src = "/assets/svg/bookmark_border.svg";
                        }
                    } else {
                        console.error('Bookmark update failed');
                    }
                });
        });
    });

    // 포스트 로드 함수
    function loadMorePosts() {
        if (isLoading) return; // 이미 로딩 중이면 무시

        isLoading = true; // 로딩 시작
        fetch(`/post?page=${currentPage}&size=${postsPerPage}`)
            .then(response => response.text())
            .then(html => {
                const postList = document.querySelector("#postList ul");
                if (postList) {
                    postList.insertAdjacentHTML('beforeend', html); // 기존 포스트 아래에 추가
                    currentPage++; // 다음 페이지로 증가
                } else {
                    console.error('Post list element not found.');
                }
                isLoading = false; // 로딩 완료
            })
            .catch(error => {
                console.error('Error fetching posts:', error);
                isLoading = false; // 오류 발생 시 로딩 상태 초기화
            });
    }

    // 스크롤 이벤트 리스너 추가
    window.addEventListener('scroll', () => {
        if (timeoutId) {
            clearTimeout(timeoutId);
        }

        timeoutId = setTimeout(() => {
            if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 500) {
                loadMorePosts(); // 추가 포스트 로드
            }
        }, 100); // 100ms 후에 실행
    });
});