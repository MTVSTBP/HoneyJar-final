document.addEventListener("DOMContentLoaded", function () {
    const submitBtn = document.getElementById('submitBtn');
    const modal = document.getElementById('Modal');
    const closeBtn = document.querySelector(".close");
    const completeBtn = document.getElementById('complete');

    const postForm = document.getElementById('Form');
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
        const title = document.getElementById('inquiry_title').value.trim();
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

        if (isValid) {
            submitBtn.classList.add('active');
            submitBtn.disabled = false; // 버튼 활성화
        } else {
            submitBtn.classList.remove('active');
            submitBtn.disabled = true; // 버튼 비활성화
        }

        return isValid;
    }

    // Add event listeners for real-time validation
    document.getElementById('inquiry_title').addEventListener('input', () => validateForm(false));
    document.getElementById('category').addEventListener('change', () => validateForm(false));
    document.getElementById('content').addEventListener('input', () => validateForm(false));

    postForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent default form submission

        if (!validateForm(true)) {
            console.log("Form validation failed");
            return;
        }

        const formData = new FormData(postForm);
        console.log("write button click!!")
        fetch('/settings/inquiry/write', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    openModal();
                } else {
                    alert('등록에 실패했습니다. 다시 시도해주세요.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('등록에 실패했습니다. 다시 시도해주세요.');
            });
    });

    // Modal handling, close button, etc.
    function openModal() {
        console.log("Opening modal");
        modal.style.display = "block";
    }

    closeBtn.addEventListener("click", function () {
        modal.style.display = "none";
    });

    completeBtn.addEventListener("click", function () {
        modal.style.display = "none";
        window.location.href = '/settings/inquiry'; // 페이지 이동
    });
});
