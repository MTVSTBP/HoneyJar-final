document.addEventListener("DOMContentLoaded", function() {
    const editIcon = document.querySelector('.edit_icon img');
    const editComment = document.querySelector('.input_area');
    const editSubmit = document.querySelector('.submit_button');

    // 초기 상태 설정
    editComment.style.display = "none";
    editSubmit.style.display = "none";

    // editIcon 클릭 시 댓글 수정 영역 토글
    editIcon.addEventListener('click', function() {
        const isHidden = editComment.style.display === "none";
        editComment.style.display = isHidden ? "block" : "none";
        editSubmit.style.display = isHidden ? "block" : "none";
    });

    editSubmit.addEventListener('click', function() {
        alert('등록되었습니다.');
    });

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
