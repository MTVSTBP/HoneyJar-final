document.addEventListener("DOMContentLoaded", function () {
    const bookmarkButtons = document.querySelectorAll(".bookmark");

    bookmarkButtons.forEach(button => {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            const form = this.querySelector("form");
            const formData = new FormData(form);

            fetch(form.action, {
                method: "POST",
                body: formData
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        const bookmarkImage = this.querySelector("img");
                        if (data.bookmarked) {
                            bookmarkImage.src = "/assets/svg/bookmark.svg";
                        } else {
                            bookmarkImage.src = "/assets/svg/bookmark_border.svg";
                        }
                    } else {
                        console.error('Bookmark update failed');
                    }
                });
        });
    });
});