document.addEventListener("DOMContentLoaded", function () {
    const tabs = {
        noticeTab: {redirect: "notice.html", hasNotification: false},
        contactTab: {redirect: "qna.html", hasNotification: false},
        faqTab: {redirect: "qna.html", hasNotification: false},
        // logoutTab: {redirect: "qna.html"},
        leaveTab: {redirect: "/settings/leave"}
    };

    const logoutTab = document.getElementById("logoutTab");
    if (logoutTab) {
        logoutTab.addEventListener("click", function (event) {
            event.preventDefault();
            logout();
        });
    }

    const notifications = Object.fromEntries(
        Object.entries(tabs).map(([id, config]) => [id, setupTab(id, config)])
    );

    function logout() {
        if (confirm('정말로 로그아웃 하시겠습니까?')) {
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
        }
    }

    /**
     * 각 탭을 설정하고 알림 요소를 반환하는 함수
     * @param {string} id - 탭의 ID
     * @param {Object} config - 탭의 설정 (redirect URL 포함)
     * @returns {Element|null} 알림 요소 또는 null (탭이 존재하지 않는 경우)
     */
    function setupTab(id, {redirect}) {
        const tab = document.getElementById(id);
        if (tab) {
            tab.addEventListener("click", () => {
                window.location.href = redirect;
            });
            return tab.querySelector('.setting-tab__notification');
        }

        return null;
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

// [예시] 해당 페이지를 조회 후 1초 후에 알림 표시
setTimeout(() => {
    ['noticeTab', 'contactTab', 'faqTab'].forEach(showNotification);
}, 1000); // TODO: 다른 방법으로 알림을 호출할 수 있게끔 수정
