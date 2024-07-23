document.addEventListener("DOMContentLoaded", function () {
    const tabs = {
        noticeTab: {redirect: "/settings/notice", hasNotification: false},
        inquiryTab: {redirect: "/settings/inquiry", hasNotification: false},
        faqTab: {redirect: "/settings/qna", hasNotification: false},
        logoutTab: {redirect: "/logout"},
        leaveTab: {redirect: "/settings/leave"}
    };

    const modalContainer = document.querySelector('.modal-container');
    const cancelButton = document.getElementById('cancelButton');
    const confirmButton = document.getElementById('confirmButton')

    const notifications = Object.fromEntries(
        Object.entries(tabs).map(([id, config]) => [id, setupTab(id, config)])
    );

    Object.entries(tabs).forEach(([id, config]) => {
        setupTab(id, config);
    });

    function setupTab(id, {redirect}) {
        const tab = document.getElementById(id);
        if (tab) {
            tab.addEventListener("click", (event) => {
                event.preventDefault();
                if (id === 'logoutTab') {
                    logout();
                } else {
                    window.location.href = redirect;
                }
            });
        }
    }

    /**
     * 특정 탭의 알림 상태를 업데이트하는 함수
     * @param {string} id - 업데이트할 탭의 ID
     * @param {boolean} show - 알림을 표시할지 여부
     */
    function updateNotification(id, show) {
        const notification = notifications[id];
        if (notification) {
            notification.classList.toggle('active', show);
        }
    }

    function openModal() {
        modalContainer.style.display = "flex";
        document.body.style.overflow = "hidden";
    }

    function closeModal() {
        modalContainer.style.display = "none";
        document.body.style.overflow = "";
    }

    function logout() {
        openModal();
    }

    cancelButton.addEventListener('click', closeModal);

    confirmButton.addEventListener('click', function () {
        fetch('/logout', {
            method: 'POST',
            credentials: 'same-origin'
        }).then(response => response.json())
            .then(data => {
                if (data.status === 200) {
                    window.location.href = data.data.redirectUrl;
                } else {
                    throw new Error('Logout failed');
                }
            }).catch(error => {
            console.error('Logout error:', error);
            alert('로그아웃 처리 중 오류가 발생했습니다.');
        });
    });

    /**
     * 특정 탭의 알림을 표시하는 함수
     * @param {string} id - 알림을 표시할 탭의 ID
     */
    window.showNotification = id => updateNotification(id, true);

    /**
     * 특정 탭의 알림을 숨기는 함수
     * @param {string} id - 알림을 숨길 탭의 ID
     */
    window.hideNotification = id => updateNotification(id, false);
});
