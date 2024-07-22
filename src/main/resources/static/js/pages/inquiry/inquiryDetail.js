import {testSendCookie} from "../utils/auth";

document.addEventListener('DOMContentLoaded', function() {
    // 뒤로 가기 버튼 기능
    var backButton = document.getElementById('back-button');

    if (backButton) {
        backButton.addEventListener('click', function(event) {
            event.preventDefault(); // 기본 링크 동작 방지
            window.history.back(); // 브라우저의 뒤로 가기 동작 수행
        });
    }

    // 수정 버튼 클릭 시
    var editButton = document.getElementById('edit-button');

    if (editButton) {
        editButton.addEventListener('click', function() {
            // var inquiryId = editButton.getAttribute('data-id'); // 수정할 문의 ID 가져오기
            // window.location.href = `/settings/inquiry/correction/${inquiryId}`; // 수정 페이지로 이동 (ID 포함)
        });
    }

    // 공용 모달 설정
    const deleteConfirmModal = document.getElementById('deleteConfirmModal');
    const deleteSuccessModal = document.getElementById('deleteSuccessModal');
    const deleteButton = document.getElementById('delete-button');
    const confirmDeleteButton = document.getElementById('confirmDelete');
    const completeDeleteButton = document.getElementById('completeDelete');
    const closeModalButtons = document.querySelectorAll('.close');

    // 모달 열기
    if (deleteButton) {
        deleteButton.addEventListener('click', function () {
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

    const editIcon = document.querySelector('.edit_icon img');
    const editComment = document.querySelector('.input_area');
    const editSubmit = document.querySelector('.submit_button');

    // 초기에는 수정 부분을 숨김
    editComment.style.display = "none";
    editSubmit.style.display = "none";

    // 수정 아이콘 클릭 시 이벤트
    editIcon.addEventListener('click', function() {
        // 수정 부분이 보이도록 토글
        if (editComment.style.display && editSubmit.style.display === "none") {
            editComment.style.display = "block";
            editSubmit.style.display = "block";
        } else {
            editComment.style.display = "none";
            editSubmit.style.display = "none";
        }
    });

    // 수정 버튼 클릭 시 이벤트
    editSubmit.addEventListener('click', function() {
        alert('등록되었습니다.');
    });

});

function getCookie(cookieName) {
    cookieName = cookieName + '=';
    let cookieData = doument.cookie;
    let start = cookeData.indexOf(cookieName);
    let cValue = '';
    if (start !== -1) {
        start += cookieName.length;
        let end = cookieData.indexOf(';', start);
        if(end === -1) end = cookieData.length;
        cValue = cookieData.substring(start,end);
    }
    return decodeURI(cValue);
    // return unescape(cValue);// Use decodeURI() or decodeURIComponent() instead.
}

function testSendCookie() {
    //
    if (getCookie("access_token") !== "") {
        const response = fetch(`/settings/inquiry/detail/4`, {
            method: 'POST',
            header: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${getCookie("access_token")}`,
            }
        });
    }

}
