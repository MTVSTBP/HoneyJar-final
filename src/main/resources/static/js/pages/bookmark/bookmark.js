// document.addEventListener("DOMContentLoaded", function() {
//     const bookmarkButtons = document.querySelectorAll(".bookmark button");
//
//     bookmarkButtons.forEach(button => {
//         button.addEventListener("click", function(event) {
//             event.preventDefault();
//             const form = this.closest("form");
//             const formData = new FormData(form);
//
//             fetch(form.action, {
//                 method: "POST",
//                 body: formData
//             })
//                 .then(response => response.json())
//                 .then(data => {
//                     if (data.success) {
//                         const bookmarkImage = this.querySelector("img");
//                         if (data.bookmarked) {
//                             bookmarkImage.src = "/assets/svg/bookmark.svg";
//                             form.closest(".bookmark").classList.add("bookmarked");
//                         } else {
//                             bookmarkImage.src = "/assets/svg/bookmark_border.svg";
//                             form.closest(".bookmark").classList.remove("bookmarked");
//
//                             // 북마크가 해제되면 목록에서 해당 항목을 제거
//                             const listItem = form.closest("li.item");
//                             if (listItem) {
//                                 listItem.remove();
//                             }
//                         }
//                         // 요청이 성공적으로 처리된 후에 /bookmarks 페이지로 리다이렉트
//                         window.location.href = "/bookmarks";
//                     } else {
//                         console.error('Bookmark update failed');
//                     }
//                 })
//                 .catch(error => {
//                     console.error('Error:', error);
//                 });
//         });
//     });
// });




document.addEventListener("DOMContentLoaded", function() {
    const bookmarkButtons = document.querySelectorAll(".bookmark button");

    bookmarkButtons.forEach(button => {
        button.addEventListener("click", function(event) {
            event.preventDefault();
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
