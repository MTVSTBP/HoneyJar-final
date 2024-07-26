document.addEventListener("DOMContentLoaded", function() {
    const bookmarkButtons = document.querySelectorAll(".bookmark button");

    bookmarkButtons.forEach(button => {
        button.addEventListener("click", function(event) {
            event.preventDefault();  // 기본 폼 제출을 막음
            const form = this.closest("form");
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
                            form.closest(".bookmark").classList.add("bookmarked");
                        } else {
                            bookmarkImage.src = "/assets/svg/bookmark_border.svg";
                            form.closest(".bookmark").classList.remove("bookmarked");

                            // 북마크가 해제되면 목록에서 해당 항목을 제거
                            const listItem = form.closest("li.item");
                            if (listItem) {
                                listItem.remove();
                            }
                        }
                    } else {
                        console.error('Bookmark update failed');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        });
    });
});
