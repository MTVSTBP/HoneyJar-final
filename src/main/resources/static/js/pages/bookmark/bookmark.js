document.addEventListener("DOMContentLoaded", function () {
    const bookmarkForms = document.querySelectorAll(".bookmark form");

    bookmarkForms.forEach(form => {
        form.addEventListener("submit", function (event) {
            event.preventDefault();

            fetch(this.action, {
                method: this.method,
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: new URLSearchParams(new FormData(this))
            })
                .then(response => response.text())
                .then(result => {
                    if (result === "success") {
                        const bookmarkImage = this.querySelector("button img");
                        const isBookmarked = bookmarkImage.src.includes("/assets/svg/bookmark_border.svg");
                        // 이미지 업데이트
                        bookmarkImage.src = isBookmarked ? "/assets/svg/bookmark.svg" : "/assets/svg/bookmark_border.svg";
                    } else {
                        console.error('Bookmark update failed');
                    }
                })
                .catch(error => console.error('Error:', error));
        });
    });
});