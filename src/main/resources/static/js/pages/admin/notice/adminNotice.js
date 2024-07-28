
document.addEventListener('DOMContentLoaded', function() {


    // 뒤로 가기 버튼 기능
    var backButton = document.getElementById('back-button');

    if (backButton) {
        backButton.addEventListener('click', function(event) {
            event.preventDefault(); // 기본 링크 동작 방지
            window.history.back(); // 브라우저의 뒤로 가기 동작 수행
        });
    }

    function attachEventListeners() {
        const questions = document.querySelectorAll(".question");
        const moreHIcons = document.querySelectorAll(".more_h");

        questions.forEach(question => {
            question.addEventListener("click", function() {
                const answer = this.nextElementSibling;
                if (answer.style.display === "block") {
                    answer.style.display = "none";
                } else {
                    answer.style.display = "block";
                }
            });
        });

        moreHIcons.forEach(icon => {
            icon.addEventListener("click", function(e) {
                e.stopPropagation(); // 이벤트 버블링 방지
                const clickBox = this.querySelector(".click_box");
                clickBox.style.display = clickBox.style.display === "block" ? "none" : "block";
            });
        });
    }

    attachEventListeners();

    // 공용 모달 설정
    const deleteConfirmModal = document.getElementById('deleteConfirmModal');
    const deleteSuccessModal = document.getElementById('deleteSuccessModal');
    const deletePostButtons = document.querySelectorAll('.delete_p');
    const confirmDeleteButton = document.getElementById('confirmDelete');
    const completeDeleteButton = document.getElementById('completeDelete');
    const closeModalButtons = document.querySelectorAll('.close');

    let currentDeleteHref = '';
    // 모달 열기
    deletePostButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault();
            currentDeleteHref = button.getAttribute('th:href');
            deleteConfirmModal.style.display = 'block';
        });
    });

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

    confirmDeleteButton.addEventListener('click', function () {
        fetch(currentDeleteHref, { method: 'POST' })
            .then(response => {
                if (response.ok) {
                    deleteConfirmModal.style.display = 'none';
                    deleteSuccessModal.style.display = 'block';
                } else {
                    console.error('Deletion failed');
                }
            })
            .catch(error => console.error('Error:', error));
    });

    completeDeleteButton.addEventListener('click', function () {
        deleteSuccessModal.style.display = 'none';
        location.reload();
    });



    // 삭제 완료 모달의 확인 버튼 클릭 시
    if (completeDeleteButton) {
        completeDeleteButton.addEventListener('click', function () {
            deleteSuccessModal.style.display = 'none';
            // 페이지를 리다이렉트하거나 필요한 후속 작업을 수행
            refreshQnaContainer();
        });
    }

    // function refreshQnaContainer() {
    //     const qnaContainer = document.getElementById('qna-container');

    //     // AJAX 요청으로 QnA 데이터를 가져와서 업데이트
    //     fetch('/path/to/qna/api')
    //         .then(response => response.text())
    //         .then(html => {
    //             qnaContainer.innerHTML = html;
    //             // 필요한 경우 새로 로드된 컨텐츠에 이벤트 리스너 재적용
    //             attachEventListeners();
    //         })
    //         .catch(error => console.error('Error refreshing QnA container:', error));
    // }

});