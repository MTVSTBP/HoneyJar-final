document.addEventListener("DOMContentLoaded", function() {
    initNav();
});

function initNav() {
    const navItems = document.querySelectorAll('.nav li');
    const currentPage = window.location.pathname;

    function isCurrentSection(href, currentPage, sectionPages) {
        return sectionPages.some(page => currentPage.includes(page));
    }

    const sections = {
        "mypage": ["/src/pages/html/myList.html", "/src/pages/html/myPage.html", "/src/pages/html/myPageCorrection.html"],
        "settings": ["/src/pages/html/settings.html", "/src/pages/html/settingsAccount.html", "/src/pages/html/settingsPrivacy.html"],
        "post": ["/src/pages/html/boardPost.html", "/src/pages/html/findAddress.html", "/src/pages/html/postWrite.html", "/src/pages/html/postCorrection.html", "/src/pages/html/follower.html", "/src/pages/html/following.html"]
    };

    navItems.forEach(item => {
        const link = item.querySelector('a');
        const img = item.querySelector('img');
        const href = link.getAttribute('href');

        // 현재 페이지가 postWrite.html인 경우 예외 처리
        if (currentPage === "/templates/pages/post/postWrite.html") {
            return; // postWrite.html에서는 이미지가 변경되지 않음
        }

        // 현재 페이지와 href가 일치하는 경우 활성화
        if (href === currentPage) {
            item.classList.add('active');
            img.src = img.src.replace('.svg', '_color.svg');
        } else {
            // 섹션 내 하위 페이지가 있는 경우 활성화
            for (const [section, pages] of Object.entries(sections)) {
                if (isCurrentSection(href, currentPage, pages)) {
                    item.classList.add('active');
                    img.src = img.src.replace('.svg', '_color.svg');
                    break;
                }
            }
        }

        item.addEventListener('click', () => {
            if (href !== "/templates/pages/post/postWrite.html") {
                // navItems.forEach(navItem => {
                //     navItem.classList.remove('active');
                //     const navImg = navItem.querySelector('img');
                //     navImg.src = navImg.src.replace('_color.svg', '.svg');
                // });
                item.classList.add('active');
                img.src = img.src.replace('.svg', '_color.svg');
            }
        });
    });
}
