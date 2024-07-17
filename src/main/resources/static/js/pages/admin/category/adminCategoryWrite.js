document.addEventListener("DOMContentLoaded", function () {
    const submitBtn = document.getElementById('submitBtn');
    const modal = document.getElementById('Modal');
    const closeBtn = document.querySelector(".close");
    const completeBtn = document.getElementById('complete');

    // 에러 메시지 표시 함수
    function showErrorMessage(element, message) {
        if (element) {
            element.textContent = message;
            element.style.display = 'block';
        }
    }

    // 에러 메시지 숨김 함수
    function hideErrorMessage(element) {
        if (element) {
            element.style.display = 'none';
        }
    }

    // 폼 검증 함수
    function validateForm(showErrors = false) {
        const category = document.getElementById('category').value;
        const categoryCreated = document.getElementById('categoryCreated').value.trim();

        const categoryError = document.getElementById('categoryError');
        const titleError = document.getElementById('titleError');

        let isValid = true;

        if (showErrors) {
            if (!category) {
                showErrorMessage(categoryError, '카테고리를 선택해주세요.');
                isValid = false;
            } else {
                hideErrorMessage(categoryError);
            }

            if (!categoryCreated) {
                showErrorMessage(titleError, '카테고리를 입력해주세요.');
                isValid = false;
            } else {
                hideErrorMessage(titleError);
            }
        } else {
            // 실시간 폼 검증 시 에러 메시지 숨기기
            if (category) hideErrorMessage(categoryError);
            if (categoryCreated) hideErrorMessage(titleError);
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

    // 폼 요소에 이벤트 리스너 추가
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

        // 폼 데이터 백엔드로 전송 (현재는 로컬 스토리지 사용)
        const category = document.getElementById('category').value;
        const categoryCreated = document.getElementById('categoryCreated').value.trim();

        // 로컬 스토리지에 카테고리 저장
        const categories = JSON.parse(localStorage.getItem('categories')) || {};
        if (!categories[category]) {
            categories[category] = [];
        }
        categories[category].push(categoryCreated);
        localStorage.setItem('categories', JSON.stringify(categories));

        openModal();
    });

    // 모달 열기 함수
    function openModal() {
        modal.style.display = "block";
    }

    // 모달 닫기 함수
    function closeModal() {
        modal.style.display = "none";
        document.getElementById('categoryCreated').value = '';
        setTimeout(() => {
            window.location.href = "/admin/category"; // 모달 닫힌 후 페이지 리디렉션
        }, 500);
    }

    // 모달 닫기 버튼 클릭 이벤트
    closeBtn.addEventListener("click", closeModal);

    // 모달 확인 버튼 클릭 이벤트
    completeBtn.addEventListener("click", function () {
        closeModal();
    });

    // 모달 외부 클릭 이벤트
    window.addEventListener("click", function (event) {
        if (event.target === modal) {
            closeModal();
        }
    });
});
