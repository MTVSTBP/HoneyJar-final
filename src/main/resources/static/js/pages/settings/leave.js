document.addEventListener('DOMContentLoaded', function () {
    const backButton = document.querySelector('.page-header img');

    const agreeCheckbox = document.getElementById('agreeCheckbox');
    const leaveButton = document.getElementById('leaveButton');
    const leaveTextarea = document.querySelector('textarea[name="leave-textbox"]');

    const modalContainer = document.querySelector('.modal-container');
    const confirmButton = document.getElementById('confirmButton');

    let modalConfirmOpenTime;

    /**
     * 모달을 열고 배경 스크롤을 방지하는 함수
     *
     * 이 함수는 다음과 같은 작업을 수행합니다:
     * 1. 콘솔에 모달 열림 로그를 출력합니다.
     * 2. 모달 컨테이너의 display 스타일을 'flex'로 설정하여 모달을 표시합니다.
     * 3. body 요소의 overflow를 'hidden'으로 설정하여 배경 스크롤을 방지합니다.
     */
    function openModal() {
        console.log("Opening modal");
        modalContainer.style.display = "flex";
        document.body.style.overflow = "hidden";
        confirmButton.disabled = true;
        modalConfirmOpenTime = Date.now();
        setTimeout(enableConfirmButton, 3000);
    }

    /**
     * 모달을 닫고 배경 스크롤을 복원하는 함수
     *
     * 이 함수는 다음과 같은 작업을 수행합니다:
     * 1. 모달 컨테이너의 display 스타일을 'none'으로 설정하여 모달을 숨깁니다.
     * 2. body 요소의 overflow 스타일을 초기화하여 배경 스크롤을 복원합니다.
     */
    function closeModal() {
        modalContainer.style.display = "none";
        document.body.style.overflow = ""; // 배경 스크롤 복원
    }

    function enableConfirmButton() {
        const elapsedTime = Date.now() - modalConfirmOpenTime;
        if (elapsedTime >= 3000) {
            confirmButton.disabled = false;
        } else {
            setTimeout(enableConfirmButton, 3000 - elapsedTime);
        }
    }

    /**
     * 폼의 유효성을 검사하고 탈퇴 버튼의 상태를 업데이트하는 함수
     *
     * 이 함수는 다음과 같은 작업을 수행합니다:
     * 1. 동의 체크박스의 체크 여부를 확인합니다.
     * 2. 탈퇴 사유 텍스트 영역의 내용이 있는지 확인합니다.
     * 3. 두 조건이 모두 충족되면 탈퇴 버튼을 활성화하고, 그렇지 않으면 비활성화합니다.
     * 4. 버튼의 시각적 상태를 'active' 클래스로 표시합니다.
     */
    function checkFormValidity() {
        const isCheckboxChecked = agreeCheckbox.checked;
        const isTextareaValid = leaveTextarea.value.trim() !== '';

        if (isCheckboxChecked && isTextareaValid) {
            leaveButton.classList.add('active');
            leaveButton.disabled = false;
        } else {
            leaveButton.classList.remove('active');
            leaveButton.disabled = true;
        }
    }

    // 뒤로가기 버튼에 대한 이벤트 리스너 추가
    backButton.addEventListener('click', function (e) {
        e.preventDefault();
        window.history.back();
    });


    // 동의 체크박스 상태 변경 시 폼 유효성 검사
    agreeCheckbox.addEventListener('change', checkFormValidity);

    // 탈퇴 사유 텍스트 영역 입력 시 폼 유효성 검사
    leaveTextarea.addEventListener('input', checkFormValidity);

    // 탈퇴 버튼 클릭 시 모달 열기
    leaveButton.addEventListener('click', function (event) {
        event.preventDefault();
        console.log('Withdraw button clicked');
        openModal();
    });

    // 확인 버튼 클릭 시 회원탈퇴 처리
    confirmButton.addEventListener("click", function () {
        if (this.disabled) return;

        fetch('/settings/leave', {
            method: 'POST',
            credentials: 'same-origin'
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 200) {
                    closeModal();
                    window.location.href = "/login";
                } else {
                    alert('회원탈퇴 처리 중 오류가 발생했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('회원탈퇴 처리 중 오류가 발생했습니다.');
            });
    });
});
