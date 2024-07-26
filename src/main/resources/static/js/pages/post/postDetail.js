document.addEventListener("DOMContentLoaded", function () {
    const categoryElement = document.getElementById('category');
    if (categoryElement) {
        categoryElement.addEventListener('change', function() {
            document.getElementById('filterForm').submit();
        });
    }

    // 슬라이더 이미지 설정
    const container = document.getElementById('container');
    if (container) {
        const kindWrap = container.querySelector('.kind_wrap');
        const slider = kindWrap.querySelector('.slider');
        const slideLis = slider.querySelectorAll('li');
        const moveButton = kindWrap.querySelectorAll('.arrow a');

        if (slideLis.length > 1) {
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
        } else {
            const singleSlideWidth = container.clientWidth;
            slideLis.forEach(li => {
                li.style.width = `${singleSlideWidth}px`;
            });
            slider.style.width = `${singleSlideWidth}px`;
        }
    }

    // 공용 모달 설정
    const deleteConfirmModal = document.getElementById('deleteConfirmModal');
    const deleteSuccessModal = document.getElementById('deleteSuccessModal');
    const deletePostButton = document.getElementById('deletePost');
    const confirmDeleteButton = document.getElementById('confirmDelete');
    const completeDeleteButton = document.getElementById('completeDelete');
    const closeModalButtons = document.querySelectorAll('.close');
    const confirmCloseButton = document.getElementById('confirmClose');

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

    // 취소 버튼 클릭 시 모달 닫기
    if (confirmCloseButton) {
        confirmCloseButton.addEventListener('click', function () {
            deleteConfirmModal.style.display = 'none';
        });
    }

    window.addEventListener('click', function (event) {
        if (event.target === deleteConfirmModal) {
            deleteConfirmModal.style.display = 'none';
        } else if (event.target === deleteSuccessModal) {
            deleteSuccessModal.style.display = 'none';
        }
    });

    // 삭제 확인 버튼 클릭 시
    if (confirmDeleteButton) {
        confirmDeleteButton.addEventListener('click', async function () {
            // 실제 삭제 작업 수행 (소프트 삭제)
            try {
                const response = await fetch(`/post/${postId}`, {
                    method: 'DELETE',
                });

                if (response.ok) {
                    deleteConfirmModal.style.display = 'none';
                    deleteSuccessModal.style.display = 'block';
                } else {
                    console.error('삭제 실패');
                }
            } catch (error) {
                console.error('삭제 오류:', error);
            }
        });
    }

    // 삭제 완료 모달의 확인 버튼 클릭 시
    if (completeDeleteButton) {
        completeDeleteButton.addEventListener('click', function () {
            deleteSuccessModal.style.display = 'none';
            // 페이지를 리다이렉트하거나 필요한 후속 작업 수행
            window.location.href = '/post';
        });
    }

    // 기타 기능 (좋아요, 북마크 등)
    const bookmarkButton = document.getElementById("bookmarkButton");
    const bookmarkImage = document.getElementById("bookmarkImage");

    if (bookmarkButton && bookmarkImage) {
        bookmarkButton.addEventListener("click", function (event) {
            event.preventDefault();
            const form = document.getElementById("bookmarkForm");
            const formData = new FormData(form);
            const isBookmarked = bookmarkButton.getAttribute("data-bookmarked") === "true";

            fetch(form.action, {
                method: "POST",
                body: formData
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        if (data.bookmarked) {
                            bookmarkImage.src = "/assets/svg/bookmark.svg";
                            bookmarkButton.setAttribute("data-bookmarked", "true");
                        } else {
                            bookmarkImage.src = "/assets/svg/bookmark_border.svg";
                            bookmarkButton.setAttribute("data-bookmarked", "false");
                        }
                    } else {
                        console.error('Bookmark update failed');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        });
    }

    const likeButton = document.getElementById("likeButton");
    const likeImage = document.getElementById("likeImage");
    const likeCount = document.getElementById("likeCount");

    if (likeButton && likeImage && likeCount) {
        const userId = parseInt(likeButton.getAttribute("data-user-id"))
        const postId = parseInt(likeButton.getAttribute("data-post-id"))
        let isLiked = likeButton.getAttribute("data-liked") === "true";

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

        const modalButton = document.getElementById("openRatingModal");

        if (confirmRating) {
            const userId = parseInt(likeButton.getAttribute("data-user-id"))
            const postId = parseInt(likeButton.getAttribute("data-post-id"))
            let isRated = modalButton.getAttribute("data-rated") === "true";

            confirmRating.addEventListener('click', function () {
                ratingModal.style.display = "none";

                stars.forEach(star => {
                    star.addEventListener('click', () => {
                        selectedRating = star.getAttribute('data-value');
                    });
                });
                if (!isRated) {
                    rating(userId, postId, selectedRating);
                    isRated = true;
                } else {
                    ratingAgain(userId, postId, selectedRating);
                    isRated = false;
                }
            }
        )
    }

    function rating(userId, postId, selectedRating) {
        fetch('/post/rating/' + postId, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'charset': 'UTF-8'
            },
            body: JSON.stringify({
                'user-id': userId,
                'post-id': postId,
                'rating': selectedRating
            })
        }).then(res => {
            if (!res.ok) {
                throw new Error('Network response was not ok');
            }
        })
    }

    function ratingAgain(userId, postId, selectedRating) {
        fetch('/post/rating-again/' + postId, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'charset': 'UTF-8'
            },
            body: JSON.stringify({
                'user-id': userId,
                'post-id': postId,
                'rating': selectedRating
            })
        }).then(res => {
            if (!res.ok) {
                throw new Error('Network response was not ok');
            }
        })
    }


    // 더보기 버튼 클릭 이벤트
    const moreHorizImage = document.querySelector('.more_h img');
    const clickBox = document.querySelector('.click_box');

    if (moreHorizImage && clickBox) {
        moreHorizImage.addEventListener('click', function (event) {
            event.stopPropagation();
            clickBox.style.display = (clickBox.style.display === 'block') ? 'none' : 'block';
        });

        document.addEventListener('click', function (event) {
            if (!clickBox.contains(event.target)) {
                clickBox.style.display = 'none';
            }
        });
    }
}})
