document.addEventListener("DOMContentLoaded", function () {
    const bookmarkButtons = document.querySelectorAll(".bookmark button");
    let currentPage = 1;
    const postsPerPage = 6;
    let isLoading = false;
    let timeoutId;

    const goodRestaurantCheckbox = document.getElementById('goodRestaurant');
    const filterForm = document.getElementById('filterForm');
    const sortSelect = document.getElementById('sortOption');
    const categorySelect = document.getElementById('category');

    let latitude = localStorage.getItem('userLatitude');
    let longitude = localStorage.getItem('userLongitude');

    console.log("Retrieved location from localStorage - Latitude:", latitude, "Longitude:", longitude);

    const currentUrl = window.location.href;
    const isByPlacePage = currentUrl.includes('/post/by-place');
    let placeName = '';

    if (isByPlacePage) {
        placeName = new URLSearchParams(window.location.search).get('placeName');
    }

    if (navigator.geolocation && !isByPlacePage) {
        navigator.geolocation.getCurrentPosition((position) => {
            latitude = position.coords.latitude;
            longitude = position.coords.longitude;
            console.log("Latitude:", latitude);
            console.log("Longitude:", longitude);
        });
    }

    // 세션 스토리지에서 이전 상태 불러오기 (by-place 페이지가 아닐 때만)
    if (!isByPlacePage) {
        const savedSortOption = sessionStorage.getItem('sortOption');
        const savedCategory = sessionStorage.getItem('selectedCategory');
        const savedGoodRestaurant = sessionStorage.getItem('goodRestaurant');

        if (savedSortOption && sortSelect) {
            sortSelect.value = savedSortOption;
        }

        if (savedCategory && categorySelect) {
            categorySelect.value = savedCategory;
        }

        if (savedGoodRestaurant && goodRestaurantCheckbox) {
            goodRestaurantCheckbox.checked = savedGoodRestaurant === 'true';
        }
    }

    // 포스트 로드 함수
    function loadMorePosts() {
        if (isLoading) return;

        isLoading = true;
        const goodRestaurant = goodRestaurantCheckbox ? goodRestaurantCheckbox.checked : false;
        const sortOption = sortSelect ? sortSelect.value || "date" : "date";
        const selectedCategory = categorySelect ? categorySelect.value || "" : "";

        let url;
        if (isByPlacePage) {
            url = `/post/by-place?placeName=${encodeURIComponent(placeName)}&page=${currentPage}&size=${postsPerPage}`;
        } else {
            url = `/post?page=${currentPage}&size=${postsPerPage}&goodRestaurant=${goodRestaurant}&latitude=${latitude || ''}&longitude=${longitude || ''}&sortOption=${sortOption}&category=${selectedCategory}`;
        }

        fetch(url)
            .then(response => response.text())
            .then(html => {
                const postList = document.querySelector("#postList ul");
                if (postList) {
                    const tempDiv = document.createElement('div');
                    tempDiv.innerHTML = html;
                    const newPosts = tempDiv.querySelectorAll('li.item');
                    newPosts.forEach(post => postList.appendChild(post));
                    currentPage++;

                    // 새로 추가된 포스트에 북마크 이벤트 리스너 추가
                    addBookmarkListeners(newPosts);

                    // 중복 제거 로직 적용
                    removeDuplicatePosts();
                } else {
                    console.error('Post list element not found.');
                }
                isLoading = false;
            })
            .catch(error => {
                console.error('Error fetching posts:', error);
                isLoading = false;
            });
    }

    // 스크롤 이벤트 리스너 추가
    window.addEventListener('scroll', () => {
        if (timeoutId) {
            clearTimeout(timeoutId);
        }

        timeoutId = setTimeout(() => {
            if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 500) {
                loadMorePosts();
            }
        }, 100);
    });

    // 필터 관련 이벤트 리스너 (by-place 페이지가 아닐 때만)
    if (!isByPlacePage) {
        if (goodRestaurantCheckbox) {
            goodRestaurantCheckbox.addEventListener('change', function () {
                currentPage = 1;
                sessionStorage.setItem('goodRestaurant', this.checked);
                loadFilteredPosts();
            });
        }

        if (sortSelect) {
            sortSelect.addEventListener('change', function () {
                currentPage = 1;
                sessionStorage.setItem('sortOption', this.value);
                loadFilteredPosts();
            });
        }

        if (categorySelect) {
            categorySelect.addEventListener('change', function () {
                currentPage = 1;
                sessionStorage.setItem('selectedCategory', this.value);
                loadFilteredPosts();
            });
        }
    }

    function loadFilteredPosts() {
        if (isByPlacePage) return;

        const goodRestaurant = goodRestaurantCheckbox ? goodRestaurantCheckbox.checked : false;
        const sortOption = sortSelect ? sortSelect.value || "date" : "date";
        const selectedCategory = categorySelect ? categorySelect.value || "" : "";

        fetch(`/post?page=0&size=${postsPerPage}&goodRestaurant=${goodRestaurant}&latitude=${latitude != null ? latitude : ''}&longitude=${longitude != null ? longitude : ''}&sortOption=${sortOption}&category=${selectedCategory}`)
            .then(response => response.text())
            .then(html => {
                const postList = document.querySelector("#postList ul");
                if (postList) {
                    postList.innerHTML = '';
                    const tempDiv = document.createElement('div');
                    tempDiv.innerHTML = html;
                    const newPosts = tempDiv.querySelectorAll('li.item');
                    newPosts.forEach(post => postList.appendChild(post));

                    // 필터링된 포스트에 북마크 이벤트 리스너 추가
                    addBookmarkListeners(newPosts);

                    // 중복 제거 로직 적용
                    removeDuplicatePosts();
                } else {
                    console.error('Post list element not found.');
                }
            })
            .catch(error => {
                console.error('Error fetching posts:', error);
            });
    }

    // 북마크 이벤트 리스너 추가 함수
    function addBookmarkListeners(posts) {
        posts.forEach(post => {
            const bookmarkButton = post.querySelector(".bookmark button");
            if (bookmarkButton) {
                bookmarkButton.addEventListener("click", handleBookmarkClick);
            }
        });
    }

    // 북마크 클릭 핸들러
    function handleBookmarkClick(event) {
        event.preventDefault();
        const form = this.closest("form");
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
                        form.closest(".bookmark").classList.add("bookmarked");
                    } else {
                        bookmarkImage.src = "/assets/svg/bookmark_border.svg";
                        form.closest(".bookmark").classList.remove("bookmarked");
                    }
                } else {
                    console.error('Bookmark update failed');
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    // 초기 북마크 이벤트 리스너 설정
    addBookmarkListeners(document.querySelectorAll("#postList ul li.item"));

    // #postList ul 스타일 설정
    const postListElement = document.querySelector("#postList ul");
    if (postListElement) {
        postListElement.style.width = '100%';
        postListElement.style.height = '100%';
        postListElement.style.display = 'flex';
        postListElement.style.justifyContent = 'space-between';
        postListElement.style.flexWrap = 'wrap';
    }

    // 페이지 로드 시 필터링된 포스트 로드 (일반 페이지에만 적용)
    if (!isByPlacePage) {
        loadFilteredPosts();
    }

    // 중복 포스트 제거 함수
    function removeDuplicatePosts() {
        const postContainer = document.querySelector("#postList ul");
        const posts = postContainer.querySelectorAll('li.item');

        const uniquePosts = new Map();
        posts.forEach(post => {
            const postId = post.dataset.postId; // 각 포스트에 data-post-id 속성을 추가해야 합니다
            if (!uniquePosts.has(postId)) {
                uniquePosts.set(postId, post);
            }
        });

        postContainer.innerHTML = '';
        uniquePosts.forEach(post => {
            postContainer.appendChild(post);
        });
    }

    // 페이지 로드 시 중복 제거 로직 실행
    removeDuplicatePosts();
});