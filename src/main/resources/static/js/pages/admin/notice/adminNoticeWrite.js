document.addEventListener("DOMContentLoaded", function () {
    const submitBtn = document.getElementById('submitBtn');
    const modal = document.getElementById('Modal');
    const closeBtn = document.querySelector(".close");
    const completeBtn = document.getElementById('complete');

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
        const content = document.getElementById('content').value.trim();

        const titleError = document.getElementById('titleError');
        const contentError = document.getElementById('contentError');

        let isValid = true;

        if (showErrors) {
            if (!title) {
                showErrorMessage(titleError, '제목을 입력하세요.');
                isValid = false;
            } else {
                hideErrorMessage(titleError);
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
            if (content) hideErrorMessage(contentError);
        }

        if (isValid) {
            submitBtn.classList.add('active');
            submitBtn.disabled = false; // 버튼 활성화
        } else {
            submitBtn.classList.remove('active');
            submitBtn.disabled = true; // 버튼 비활성화
        }

        return isValid;
    }

    const postForm = document.getElementById('Form');
    postForm.addEventListener('input', (event) => {
        validateForm(false);
        hideErrorMessage(document.getElementById(event.target.id + 'Error'));
    });
    postForm.addEventListener('change', (event) => {
        validateForm(false);
        hideErrorMessage(document.getElementById(event.target.id + 'Error'));
    });
    postForm.addEventListener('submit', function (event) {
        event.preventDefault();

        if (!validateForm(true)) {
            return;
        }

        openModal();
    });

    function openModal() {
        modal.style.display = "block";
    }

    function closeModal() {
        modal.style.display = "none";
    }

    closeBtn.addEventListener("click", closeModal);

    completeBtn.addEventListener("click", function() {
        closeModal();
        window.location.href = "/src/pages/html/adminNoticeDetail.html";
    });

    window.addEventListener("click", function(event) {
        if (event.target === modal) {
            closeModal();
        }
    });
});
