document.addEventListener("DOMContentLoaded", function () {
    initNav();
});

function initNav() {
    const navItems = document.querySelectorAll('.nav li');
    const currentPage = window.location.pathname;

    function isCurrentSection(currentPage, sectionPages) {
        return sectionPages.some(page => currentPage.includes(page));
    }

    const sections = {
        "mypage": ["/mypage", "/myList", "/myPageCorrection"],
        "settings": ["/settings", "/notice", "/settingsPrivacy"],
        "post": ["/post", "/boardPost", "/findAddress", "/postCorrection", "/follower", "/following"]
    };

    navItems.forEach(item => {
        const link = item.querySelector('a');
        const img = item.querySelector('img');
        const action = link.getAttribute('data-action');
        const href = link.getAttribute('href');

        // 맛집추가 버튼 (가운데 버튼) 처리
        if (item.id === 'created') {
            link.addEventListener('click', function (e) {
                // 기본 동작 유지 (페이지 이동)
            });
            return; // 다음 항목으로 넘어감
        }

        // 현재 페이지 활성화 로직
        if (currentPage.includes(href) || (action && currentPage.includes(action))) {
            item.classList.add('active');
            img.src = img.src.replace('.svg', '_color.svg');
        } else {
            for (const [section, pages] of Object.entries(sections)) {
                if (isCurrentSection(currentPage, pages) && item.id === section) {
                    item.classList.add('active');
                    img.src = img.src.replace('.svg', '_color.svg');
                    break;
                }
            }
        }

        // 클릭 이벤트 리스너
        link.addEventListener('click', function (e) {
            e.preventDefault(); // 기본 동작 중지

            // 모든 아이템 비활성화 (맛집추가 버튼 제외)
            navItems.forEach(navItem => {
                if (navItem.id !== 'created') {
                    navItem.classList.remove('active');
                    const navImg = navItem.querySelector('img');
                    navImg.src = navImg.src.replace('_color.svg', '.svg');
                }
            });

            // 클릭된 아이템 활성화
            item.classList.add('active');
            img.src = img.src.replace('.svg', '_color.svg');

            // 페이지 이동 처리
            if (action) {
                switch (action) {
                    case 'home':
                        goHome();
                        break;
                    case 'mypage':
                        goMypage();
                        break;
                    case 'settings':
                        goSettings();
                        break;
                }
            } else if (href && href !== '#') {
                window.location.href = href;
            }
        });
    });
}

function goHome() {
    const isAdmin = document.documentElement.getAttribute('data-is-admin') === 'true';
    console.log('Is Admin:', isAdmin);
    window.location.href = isAdmin ? '/admin' : '/home';
}

function goMypage() {
    const isAdmin = document.documentElement.getAttribute('data-is-admin') === 'true';
    console.log('Is Admin:', isAdmin);
    window.location.href = isAdmin ? '/admin/mypage' : '/mypage';
}

function goSettings() {
    const isAdmin = document.documentElement.getAttribute('data-is-admin') === 'true';
    console.log('Is Admin:', isAdmin);
    window.location.href = isAdmin ? '/admin/settings' : '/settings';
}