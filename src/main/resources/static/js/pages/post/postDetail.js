document.addEventListener("DOMContentLoaded", function () {
    // 슬라이더 이미지 설정
    const container = document.getElementById('container');
    const kindWrap = container.querySelector('.kind_wrap');
    const slider = kindWrap.querySelector('.slider');
    const slideLis = slider.querySelectorAll('li');
    const moveButton = kindWrap.querySelectorAll('.arrow a');

    const clone1 = slideLis[0].cloneNode(true);
    const cloneLast = slideLis[slideLis.length - 1].cloneNode(true);
    slider.insertBefore(cloneLast, slideLis[0]);
    slider.appendChild(clone1);

    let currentIdx = 1;
    let translate = 0;
    const speedTime = 500;

    function setSliderWidth() {
        const sliderCloneLis = slider.querySelectorAll('li');
        const liWidth = container.clientWidth;
        const sliderWidth = liWidth * sliderCloneLis.length;
        slider.style.width = `${sliderWidth}px`;
        sliderCloneLis.forEach(li => {
            li.style.width = `${liWidth}px`;
        });
        translate = -liWidth * currentIdx;
        slider.style.transform = `translateX(${translate}px)`;
        return liWidth;
    }

    let liWidth = setSliderWidth();

    moveButton.forEach(button => button.addEventListener('click', moveSlide));

    function move(D) {
        currentIdx += (-1 * D);
        translate += liWidth * D;
        slider.style.transform = `translateX(${translate}px)`;
        slider.style.transition = `all ${speedTime}ms ease`;
    }

    function moveSlide(event) {
        event.preventDefault();
        if (event.currentTarget.classList.contains('next')) {
            move(-1);
            if (currentIdx === slider.querySelectorAll('li').length - 1) {
                setTimeout(() => {
                    slider.style.transition = 'none';
                    currentIdx = 1;
                    translate = -liWidth;
                    slider.style.transform = `translateX(${translate}px)`;
                }, speedTime);
            }
        } else {
            move(1);
            if (currentIdx === 0) {
                setTimeout(() => {
                    slider.style.transition = 'none';
                    currentIdx = slider.querySelectorAll('li').length - 2;
                    translate = -(liWidth * currentIdx);
                    slider.style.transform = `translateX(${translate}px)`;
                }, speedTime);
            }
        }
    }

    window.addEventListener('resize', () => {
        liWidth = setSliderWidth();
    });

    // 공용 모달 설정
    const deleteConfirmModal = document.getElementById('deleteConfirmModal');
    const deleteSuccessModal = document.getElementById('deleteSuccessModal');
    const deletePostButton = document.getElementById('deletePost');
    const confirmDeleteButton = document.getElementById('confirmDelete');
    const completeDeleteButton = document.getElementById('completeDelete');
    const closeModalButtons = document.querySelectorAll('.close');

    // 모달 열기
    if (deletePostButton) {
        deletePostButton.addEventListener('click', function () {
            deleteConfirmModal.style.display = 'block';
        });
    }

    // 모달 닫기
    closeModalButtons.forEach(button => {
        button.addEventListener('click', function () {
            deleteConfirmModal.style.display = 'none';
            deleteSuccessModal.style.display = 'none';
        });
    });

    window.addEventListener('click', function (event) {
        if (event.target === deleteConfirmModal) {
            deleteConfirmModal.style.display = 'none';
        } else if (event.target === deleteSuccessModal) {
            deleteSuccessModal.style.display = 'none';
        }
    });

    // 삭제 확인 버튼 클릭 시
    if (confirmDeleteButton) {
        confirmDeleteButton.addEventListener('click', function () {
            deleteConfirmModal.style.display = 'none';
            deleteSuccessModal.style.display = 'block';

            // 삭제 작업을 여기서 수행
            /*
            performDeleteAction().then(response => {
                if (response.success) {
                    deleteSuccessModal.style.display = 'block';
                } else {
                    console.error('Deletion failed');
                }
            });
            */
        });
    }

    // 삭제 완료 모달의 확인 버튼 클릭 시
    if (completeDeleteButton) {
        completeDeleteButton.addEventListener('click', function () {
            deleteSuccessModal.style.display = 'none';
            // 페이지를 리다이렉트하거나 필요한 후속 작업을 수행
            window.location.href = '/src/pages/html/postList.html';
        });
    }

    // 기타 기능 (좋아요, 북마크 등)
    const bookmarkButton = document.getElementById("bookmarkButton");
    const bookmarkImage = document.getElementById("bookmarkImage");

    if (bookmarkButton && bookmarkImage) {
        bookmarkButton.addEventListener("click", function () {
            const isBookmarked = bookmarkImage.src.includes("bookmark_border.svg");

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
    }

    const likeButton = document.getElementById("likeButton");
    const likeImage = document.getElementById("likeImage");
    const likeCount = document.getElementById("likeCount");

    if (likeButton && likeImage && likeCount) {
        const userId = parseInt(likeButton.getAttribute("data-user-id"))
        const postId = parseInt(likeButton.getAttribute("data-post-id"))
        let isLiked = likeButton.getAttribute("data-liked") === "true";

        console.log(isLiked)

        likeButton.addEventListener("click", function () {
            const newCount = isLiked ? parseInt(likeCount.textContent) - 1 : parseInt(likeCount.textContent) + 1;
            likeCount.textContent = newCount;
            likeImage.src = isLiked ? "/assets/svg/favorite.svg" : "/assets/svg/favorite_color.svg";

            // AJAX 요청 보내기 (좋아요 상태 변경)
            if (!isLiked) {
                likePost(userId, postId);
                isLiked = true;
            } else {
                unlikePost(userId, postId);
                isLiked = false;
            }
        });
    }

    function likePost(userId, postId) {
        fetch('/post/like/' + postId, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'charset': 'UTF-8'
            },
            body: JSON.stringify({
                'user-id': userId,
                'post-id': postId
            })
        }).then(res => {
            if (!res.ok) {
                throw new Error('Network response was not ok');
            }
            return res.json();
        })
    }

    function unlikePost(userId, postId) {
        fetch('/post/like/' + postId, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json',
                'charset': 'UTF-8'
            },
            body: JSON.stringify({
                'user-id': userId,
                'post-id': postId
            })
        }).then(res => {
            if (!res.ok) {
                throw new Error('Network response was not ok');
            }
            return res.json();
        })
    }

    const commentButton = document.getElementById("commentButton");

    if (commentButton) {
        commentButton.addEventListener("click", function () {
            window.location.href = "/src/pages/html/comment.html";
        });
    }

    const openRatingModal = document.getElementById("openRatingModal");
    const ratingModal = document.getElementById("ratingModal");
    const stars = document.querySelectorAll('.modal-rating .star');
    const confirmRating = document.getElementById("confirmRating");
    const ratingStar = document.getElementById("ratingStar");
    const averageRating = document.getElementById("averageRating");

    let selectedRating = 0;

    if (openRatingModal && ratingModal) {
        openRatingModal.addEventListener("click", function () {
            ratingModal.style.display = "block";
        });

        if (ratingStar) {
            ratingStar.addEventListener("click", function () {
                ratingModal.style.display = "block";
            });
        }

        closeModalButtons.forEach(button => {
            button.addEventListener('click', function () {
                ratingModal.style.display = 'none';
            });
        });

        window.addEventListener('click', function (event) {
            if (event.target === ratingModal) {
                ratingModal.style.display = 'none';
            }
        });

        stars.forEach(star => {
            star.addEventListener('click', function () {
                selectedRating = parseInt(this.getAttribute('data-value'));

                stars.forEach(s => {
                    s.classList.remove('selected');
                    if (parseInt(s.getAttribute('data-value')) <= selectedRating) {
                        s.classList.add('selected');
                    }
                });
            });
        });

        if (confirmRating) {
            confirmRating.addEventListener('click', function () {
                ratingModal.style.display = "none";

                // AJAX 요청 보내기 (평점 저장)
                /*
                sendRatingData(selectedRating).then(response => {
                    if (response.success) {
                        // 백엔드에서 총점을 받아와서 업데이트
                        averageRating.textContent = response.newAverage;
                        // 성공적으로 저장되면 별점 이미지를 업데이트
                        ratingStar.src = "/assets/svg/star_color.svg";
                    } else {
                        console.error('Rating update failed');
                    }
                });
                */

                // 임시로 별점 이미지 및 총점 업데이트
                averageRating.textContent = (parseFloat(averageRating.textContent) + selectedRating) / 2;
                ratingStar.src = "/assets/svg/star_color.svg";
            });
        }
    }

    // 더보기 버튼 클릭 이벤트
    const moreHorizImage = document.querySelector('.more_h img');
    const clickBox = document.querySelector('.click_box');

    moreHorizImage.addEventListener('click', function (event) {
        event.stopPropagation();
        clickBox.style.display = (clickBox.style.display === 'block') ? 'none' : 'block';
    });

    document.addEventListener('click', function (event) {
        if (!clickBox.contains(event.target)) {
            clickBox.style.display = 'none';
        }
    });
});
