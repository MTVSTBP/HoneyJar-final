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
            // 수정 페이지로 이동 (수정 페이지 URL로 변경)
            window.location.href = "/admin/notice/correction/" + window.location.pathname.split("/").reverse()[0];
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
            window.location.href = "/admin/notice/delete/" + window.location.pathname.split("/").reverse()[0];
        });
    }
});
