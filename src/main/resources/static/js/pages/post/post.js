// document.addEventListener("DOMContentLoaded", function () {
//     const bookmarkButtons = document.querySelectorAll(".bookmark");
//     let currentPage = 1; // 현재 페이지를 1로 시작
//     const postsPerPage = 6; // 한 번에 로드할 포스트 수
//     let isLoading = false; // 로딩 상태를 추적
//     let timeoutId;
//
//     const goodRestaurantCheckbox = document.getElementById('goodRestaurant');
//     const filterForm = document.getElementById('filterForm');
//
//     // 북마크 버튼 클릭 이벤트 처리
//     bookmarkButtons.forEach(button => {
//         button.addEventListener("click", function (event) {
//             event.preventDefault();
//             const form = this.querySelector("form");
//             const formData = new FormData(form);
//
//             fetch(form.action, {
//                 method: "POST",
//                 body: formData
//             })
//                 .then(response => response.json())
//                 .then(data => {
//                     if (data.success) {
//                         const bookmarkImage = this.querySelector("img");
//                         if (data.bookmarked) {
//                             bookmarkImage.src = "/assets/svg/bookmark.svg";
//                         } else {
//                             bookmarkImage.src = "/assets/svg/bookmark_border.svg";
//                         }
//                     } else {
//                         console.error('Bookmark update failed');
//                     }
//                 });
//         });
//     });
//
//     // 포스트 로드 함수
//     function loadMorePosts() {
//         if (isLoading) return; // 이미 로딩 중이면 무시
//
//         isLoading = true; // 로딩 시작
//         const goodRestaurant = goodRestaurantCheckbox.checked; // 체크박스 상태 가져오기
//         fetch(`/post?page=${currentPage}&size=${postsPerPage}&goodRestaurant=${goodRestaurant}`)
//             .then(response => response.text())
//             .then(html => {
//                 const postList = document.querySelector("#postList ul");
//                 if (postList) {
//                     postList.insertAdjacentHTML('beforeend', html); // 기존 포스트 아래에 추가
//                     currentPage++; // 다음 페이지로 증가
//                 } else {
//                     console.error('Post list element not found.');
//                 }
//                 isLoading = false; // 로딩 완료
//             })
//             .catch(error => {
//                 console.error('Error fetching posts:', error);
//                 isLoading = false; // 오류 발생 시 로딩 상태 초기화
//             });
//     }
//
//     // 스크롤 이벤트 리스너 추가
//     window.addEventListener('scroll', () => {
//         if (timeoutId) {
//             clearTimeout(timeoutId);
//         }
//
//         timeoutId = setTimeout(() => {
//             if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 500) {
//                 loadMorePosts(); // 추가 포스트 로드
//             }
//         }, 100); // 100ms 후에 실행
//     });
//
//     // 체크박스 상태 변경 시 필터링된 결과를 가져옴
//     goodRestaurantCheckbox.addEventListener('change', function () {
//         currentPage = 1; // 페이지를 초기화
//         const goodRestaurant = goodRestaurantCheckbox.checked; // 체크박스 상태 가져오기
//         fetch(`/post?page=0&size=${postsPerPage}&goodRestaurant=${goodRestaurant}`)
//             .then(response => response.text())
//             .then(html => {
//                 const postList = document.querySelector("#postList ul");
//                 if (postList) {
//                     postList.innerHTML = html; // 기존 포스트를 새로운 포스트로 교체
//                 } else {
//                     console.error('Post list element not found.');
//                 }
//             })
//             .catch(error => {
//                 console.error('Error fetching posts:', error);
//             });
//     });
//
// });


document.addEventListener("DOMContentLoaded", function () {
    const bookmarkButtons = document.querySelectorAll(".bookmark");
    let currentPage = 1; // 현재 페이지를 1로 시작
    const postsPerPage = 6; // 한 번에 로드할 포스트 수
    let isLoading = false; // 로딩 상태를 추적
    let timeoutId;

    const goodRestaurantCheckbox = document.getElementById('goodRestaurant');
    const filterForm = document.getElementById('filterForm');
    const sortSelect = document.getElementById('sortOption');

    // 사용자 위치 가져오기
    let latitude = null;
    let longitude = null;

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition((position) => {
            latitude = position.coords.latitude;
            longitude = position.coords.longitude;
            console.log("Latitude:", latitude);
            console.log("Longitude:", longitude);
        });
    }

    // #postList ul에 스타일 추가
    const postList = document.querySelector("#postList ul");
    if (postList) {
        postList.style.width = "100%";
        postList.style.height = "100%";
        postList.style.display = "flex";
        postList.style.justifyContent = "space-between";
        postList.style.flexWrap = "wrap";
    }

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
        const goodRestaurant = goodRestaurantCheckbox.checked; // 체크박스 상태 가져오기
        const sortOption = sortSelect.value || "date"; // 정렬 옵션 가져오기 (기본값: 최신순)
        fetch(`/post?page=${currentPage}&size=${postsPerPage}&goodRestaurant=${goodRestaurant}&latitude=${latitude}&longitude=${longitude}&sortOption=${sortOption}`)
            .then(response => response.text())
            .then(html => {
                const postList = document.querySelector("#postList ul");
                if (postList) {
                    const tempDiv = document.createElement('div');
                    tempDiv.innerHTML = html;
                    const newPosts = tempDiv.querySelectorAll('li.item');
                    newPosts.forEach(post => postList.appendChild(post));
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

    // 체크박스 상태 변경 시 필터링된 결과를 가져옴
    goodRestaurantCheckbox.addEventListener('change', function () {
        currentPage = 1; // 페이지를 초기화
        const goodRestaurant = goodRestaurantCheckbox.checked; // 체크박스 상태 가져오기
        const sortOption = sortSelect.value || "date"; // 정렬 옵션 가져오기 (기본값: 최신순)
        fetch(`/post?page=0&size=${postsPerPage}&goodRestaurant=${goodRestaurant}&latitude=${latitude}&longitude=${longitude}&sortOption=${sortOption}`)
            .then(response => response.text())
            .then(html => {
                const postList = document.querySelector("#postList ul");
                if (postList) {
                    postList.innerHTML = ''; // 기존 포스트를 제거
                    const tempDiv = document.createElement('div');
                    tempDiv.innerHTML = html;
                    const newPosts = tempDiv.querySelectorAll('li.item');
                    newPosts.forEach(post => postList.appendChild(post));
                } else {
                    console.error('Post list element not found.');
                }
            })
            .catch(error => {
                console.error('Error fetching posts:', error);
            });
    });

    // 정렬 옵션 변경 시 필터링된 결과를 가져옴
    sortSelect.addEventListener('change', function () {
        currentPage = 1; // 페이지를 초기화
        const goodRestaurant = goodRestaurantCheckbox.checked; // 체크박스 상태 가져오기
        const sortOption = sortSelect.value || "date"; // 정렬 옵션 가져오기 (기본값: 최신순)
        fetch(`/post?page=0&size=${postsPerPage}&goodRestaurant=${goodRestaurant}&latitude=${latitude}&longitude=${longitude}&sortOption=${sortOption}`)
            .then(response => response.text())
            .then(html => {
                const postList = document.querySelector("#postList ul");
                if (postList) {
                    postList.innerHTML = ''; // 기존 포스트를 제거
                    const tempDiv = document.createElement('div');
                    tempDiv.innerHTML = html;
                    const newPosts = tempDiv.querySelectorAll('li.item');
                    newPosts.forEach(post => postList.appendChild(post));
                } else {
                    console.error('Post list element not found.');
                }
            })
            .catch(error => {
                console.error('Error fetching posts:', error);
            });
    });
});






