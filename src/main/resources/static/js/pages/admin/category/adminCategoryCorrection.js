document.addEventListener("DOMContentLoaded", function () {
    const submitBtn = document.getElementById('submitBtn');
    const modal = document.getElementById('Modal');
    const closeBtn = document.querySelector(".close");
    const completeBtn = document.getElementById('complete');

    // URL에서 수정할 카테고리와 항목 인덱스를 가져오기
    const urlParams = new URLSearchParams(window.location.search);
    const category = urlParams.get('category');
    const index = parseInt(urlParams.get('index'));

    // 기존 카테고리 데이터를 로컬 스토리지에서 가져오기
    const categories = JSON.parse(localStorage.getItem('categories')) || {};

    // 폼에 기존 데이터 채우기
    if (category && !isNaN(index) && categories[category] && categories[category][index]) {
        document.getElementById('category').value = category;
        document.getElementById('categoryCreated').value = categories[category][index];
    } else {
        alert('유효하지 않은 접근입니다.');
        window.location.href = "/src/pages/html/adminCategory.html";
    }

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
        const categoryCreated = document.getElementById('categoryCreated').value.trim();

        const titleError = document.getElementById('titleError');

        let isValid = true;

        if (showErrors) {
            if (!categoryCreated) {
                showErrorMessage(titleError, '카테고리를 입력해주세요.');
                isValid = false;
            } else {
                hideErrorMessage(titleError);
            }
        } else {
            // 실시간 폼 검증 시 에러 메시지 숨기기
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
        const categoryCreated = document.getElementById('categoryCreated').value.trim();

        // 기존 카테고리 데이터를 수정하여 로컬 스토리지에 저장
        categories[category][index] = categoryCreated;
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
        setTimeout(() => {
            window.location.href = "/src/pages/html/adminCategory.html"; // 모달 닫힌 후 페이지 리디렉션
        }, 500);
    }

    // 모달 닫기 버튼 클릭 이벤트
    closeBtn.addEventListener("click", closeModal);

    // 모달 확인 버튼 클릭 이벤트
    completeBtn.addEventListener("click", function() {
        closeModal();
    });

    // 모달 외부 클릭 이벤트
    window.addEventListener("click", function(event) {
        if (event.target === modal) {
            closeModal();
        }
    });
});
