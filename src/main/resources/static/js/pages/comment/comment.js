document.addEventListener("DOMContentLoaded", function() {
    const editIcon = document.querySelector('.edit_icon img');
    const editComment = document.querySelector('.input_area');
    const registSubmit = document.querySelector('.submit_button');

    // 초기 상태 설정
    editComment.style.display = "none";
    registSubmit.style.display = "none";

    // editIcon 클릭 시 댓글 수정 영역 토글
    editIcon.addEventListener('click', function() {
        const isHidden = editComment.style.display === "none";
        editComment.style.display = isHidden ? "block" : "none";
        registSubmit.style.display = isHidden ? "block" : "none";
    });

    // comment 클래스 또는 함수 정의
    class Comment {
        constructor(comment) {
            this.comment = comment;
        }
    }

    const commentInput = document.getElementById('commentInput'); // comment input 요소 정의
    const formData = new Comment(commentInput.value);

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

    const moreHorizImages = document.querySelectorAll('.more_h img');

    moreHorizImages.forEach(function(moreHorizImage) {
        moreHorizImage.addEventListener('click', function(event) {
            event.stopPropagation(); // 클릭 이벤트 전파 방지
            const clickBox = this.nextElementSibling;
            clickBox.style.display = (clickBox.style.display === 'block') ? 'none' : 'block';
        });
    });

    document.addEventListener('click', function(event) {
        moreHorizImages.forEach(function(moreHorizImage) {
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
                // actionButtons 숨기고 edit_comment 보이기
                actionButtons.style.display = 'none'; // 다른 액션 버튼 숨기기
                editCommentSection.style.display = (editCommentSection.style.display === 'block') ? 'none' : 'block';
            }
        });
    });
});
