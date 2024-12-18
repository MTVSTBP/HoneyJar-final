document.addEventListener("DOMContentLoaded", function() {
    const editIcon = document.querySelector('.edit_icon img');
    const editComment = document.querySelector('.input_area');
    const registSubmit = document.querySelector('.submit_button');

    // 초기 상태 설정
    editComment.style.display = "none";
    registSubmit.style.display = "none";


    // 모든 more_h div를 가져오기
    const commentDivs = document.querySelectorAll('.more_h');

    commentDivs.forEach(div => {
        // 각 div의 data-post-id 속성 값 가져오기
        const parentUserId = div.getAttribute('data-user-id');
        const commentPostId = div.getAttribute('data-other-id');

        // postId와 comment.postId가 다르면 해당 div 숨기기
        if (parentUserId !== commentPostId) {
            div.style.display = 'none';
        }
    });

    // editIcon 클릭 시 댓글 수정 영역 토글
    editIcon.addEventListener('click', function() {
        const isHidden = editComment.style.display === "none";
        editComment.style.display = isHidden ? "block" : "none";
        registSubmit.style.display = isHidden ? "block" : "none";
    });

    // comment 클래스 또는 함수 정의
    // class Comment {
    //     constructor(comment) {
    //         this.comment = comment;
    //     }
    // }

    // const commentInput = document.getElementById('commentInput'); // comment input 요소 정의
    // const formData = new Comment(commentInput.value);

    // registSubmit.addEventListener('click', async function () {
    //     try {
    //         const response = await fetch('/comment/regist', {
    //             method: 'POST',
    //             body: JSON.stringify(formData) // 객체를 JSON 문자열로 변환하여 전송
    //         });
    //
    //         if (!response.ok) {
    //             throw new Error('Network response was not ok');
    //         }
    //
    //         const result = await response.json();
    //         window.location.href = `comment/?postId=${result.postId}`;
    //     } catch (error) {
    //         console.error('There was a problem with the fetch operation:', error);
    //     }
    // });

    const moreHorizImages2 = document.querySelectorAll('.more_h img');

    moreHorizImages2.forEach(function(moreHorizImage) {
        moreHorizImage.addEventListener('click', function(event) {
            event.stopPropagation(); // 클릭 이벤트 전파 방지
            const clickBox = this.nextElementSibling;
            clickBox.style.display = (clickBox.style.display === 'block') ? 'none' : 'block';
        });
    });

    document.addEventListener('click', function(event) {
        moreHorizImages2.forEach(function(moreHorizImage) {
            const clickBox = moreHorizImage.nextElementSibling;
            if (!clickBox.contains(event.target)) {
                clickBox.style.display = 'none';
            }
        });
    });

    // 수정 버튼 클릭 시 해당 댓글의 edit_comment 토글
    const editButtons = document.querySelectorAll('.editButton');
    editButtons.forEach(function(editButton) {
        editButton.addEventListener('click', function(event) {
            event.stopPropagation(); // 클릭 이벤트 전파 방지
            const editCommentSection = this.closest('.user_details').querySelector('.edit_comment');
            const actionButtons = this.closest('.more_h').querySelector('.click_box'); // actionButtons
            console.log(editCommentSection);
            console.log(actionButtons)
            if (editCommentSection) {
    const editButtons = document.querySelectorAll('.editButton');
                // actionButtons 숨기고 edit_comment 보이기
                actionButtons.style.display = 'none'; // 다른 액션 버튼 숨기기
                editCommentSection.style.display = (editCommentSection.style.display === 'block') ? 'none' : 'block';
            }
        });
    });

    // 삭제 버튼
    const deleteButtons = document.querySelectorAll('.deleteButton');
    deleteButtons.forEach(function(deleteButton) {
        deleteButton.addEventListener('click', async function(event) {
            event.preventDefault(); // 기본 링크 행동 방지
            event.stopPropagation(); // 클릭 이벤트 전파 방지

            const userRecord = this.closest('.user_record');
            const postId = userRecord.querySelector(".user_info").getAttribute('data-post-id');
            const commentId = userRecord.querySelector(".user_info").getAttribute('data-comment-id'); // commentId 가져오기
            console.log("최형석 바보ㅋ")
            console.log("commentId: ", commentId);
            console.log("postId: ", postId);

            // 삭제 확인 메시지
            if (!confirm('댓글을 삭제하시겠습니까?')) {
                return; // 사용자가 취소하면 함수 종료
            }

            const url = `/comment/delete/${postId}/${commentId}`; // 요청할 URL 구성

            try {
                const response = await fetch(url, {
                    method: 'GET', // GET 메소드 사용
                    headers: {
                        "Content-Type": "application/json",
                    },
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                // 삭제 성공 후 처리 로직 추가
                console.log('Comment deleted successfully');
                // 예를 들어, 삭제된 댓글을 DOM에서 제거하는 등의 추가 작업 수행
                userRecord.remove(); // 댓글 요소 삭제

            } catch (error) {
                console.error('There was a problem with the fetch operation:', error);
            }
        });
    });

});
