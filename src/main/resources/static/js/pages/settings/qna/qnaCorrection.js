document.addEventListener("DOMContentLoaded", function () {
    const submitBtn = document.getElementById('submitBtn');
    const modal = document.getElementById('Modal');
    const closeBtn = document.querySelector(".close");
    const completeBtn = document.getElementById('complete');

    // // 초기 데이터 불러오기 (임시 데이터 사용)
    // function loadInitialData() {
    //     // 예시 데이터
    //     const data = {
    //         category: 'category1',
    //         title: '기존 제목',
    //         content: '기존 내용'
    //     };
    //
    //     document.getElementById('category').value = data.category;
    //     document.getElementById('title').value = data.title;
    //     document.getElementById('content').value = data.content;
    // }
    //
    // loadInitialData();

    function showErrorMessage(element, message) {
        if (element) {
            element.textContent = message;
            element.style.display = 'block';
        }
    }

    function hideErrorMessage(element) {
        if (element) {
            element.style.display = 'none';
        }
    }

    function validateForm(showErrors = false) {
        const title = document.getElementById('title').value.trim();
        const category = document.getElementById('category').value;
        const content = document.getElementById('content').value.trim();

        const titleError = document.getElementById('titleError');
        const categoryError = document.getElementById('categoryError');
        const contentError = document.getElementById('contentError');

        let isValid = true;

        if (showErrors) {
            if (!title) {
                showErrorMessage(titleError, '제목을 입력하세요.');
                isValid = false;
            } else {
                hideErrorMessage(titleError);
            }

            if (!category) {
                showErrorMessage(categoryError, '카테고리를 선택하세요.');
                isValid = false;
            } else {
                hideErrorMessage(categoryError);
            }

            if (!content) {
                showErrorMessage(contentError, '내용을 입력하세요.');
                isValid = false;
            } else {
                hideErrorMessage(contentError);
            }
        } else {
            // 실시간 폼 검증 시 에러 메시지 숨기기
            if (title) hideErrorMessage(titleError);
            if (category) hideErrorMessage(categoryError);
            if (content) hideErrorMessage(contentError);
        }

        // if (isValid) {
        //     submitBtn.classList.add('active');
        //     submitBtn.disabled = false; // 버튼 활성화
        // } else {
        //     submitBtn.classList.remove('active');
        //     submitBtn.disabled = true; // 버튼 비활성화
        // }
        //
        // return isValid;
    }

    // const postForm = document.getElementById('Form');
    // postForm.addEventListener('input', (event) => {
    //     validateForm(false);
    //     hideErrorMessage(document.getElementById(event.target.id + 'Error'));
    // });
    // postForm.addEventListener('change', (event) => {
    //     validateForm(false);
    //     hideErrorMessage(document.getElementById(event.target.id + 'Error'));
    // });
    // postForm.addEventListener('submit', function (event) {
    //     event.preventDefault();
    //
    //     if (!validateForm(true)) {
    //         return;
    //     }
    //
    //     openModal();
    // });

    function openModal() {
        modal.style.display = "block";
    }

    function closeModal() {
        modal.style.display = "none";
    }

    closeBtn.addEventListener("click", closeModal);

    completeBtn.addEventListener("click", function () {
        closeModal();
        window.location.href = "/admin/qna";
    });

    window.addEventListener("click", function (event) {
        if (event.target === modal) {
            closeModal();
        }
    });
});
