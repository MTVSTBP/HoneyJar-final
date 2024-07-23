document.addEventListener("DOMContentLoaded", function () {
    const tabs = {
        adminContactTab: {redirect: "/admin/inquiry"},
        adminNoticeTab: {redirect: "/admin/notice"},
        adminCategoryTab: {redirect: "/admin/category"},
        adminFaqTab: {redirect: "/admin/qna"},
        adminLogoutTab: {redirect: "/logout"},
    };

    const modalContainer = document.querySelector('.modal-container');
    const cancelButton = document.getElementById('cancelButton');
    const confirmButton = document.getElementById('confirmButton')

    Object.entries(tabs).forEach(([id, config]) => {
        setupTab(id, config);
    });

    function setupTab(id, {redirect}) {
        const tab = document.getElementById(id);
        if (tab) {
            tab.addEventListener("click", (event) => {
                event.preventDefault();
                if (id === 'adminLogoutTab') {
                    adminLogout();
                } else {
                    window.location.href = redirect;
                }
            });
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

    function adminLogout() {
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
});
