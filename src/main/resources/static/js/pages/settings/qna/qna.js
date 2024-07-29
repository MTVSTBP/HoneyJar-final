document.addEventListener("DOMContentLoaded", function () {
    const tabs = document.querySelectorAll(".tab");
    const qnaContainers = document.querySelectorAll(".qna");

    tabs.forEach(tab => {
        tab.addEventListener("click", function () {
            tabs.forEach(t => t.classList.remove("tab_active"));
            this.classList.add("tab_active");

            const category = this.getAttribute("data-category");
            showCategory(category);
        });
    });

    function showCategory(category) {
        qnaContainers.forEach(container => {
            if (container.classList.contains(`category-${category}`)) {
                container.style.display = "block";
            } else {
                container.style.display = "none";
            }
        });
    }

    const questions = document.querySelectorAll(".question");
    questions.forEach(question => {
        question.addEventListener("click", function () {
            const answer = this.nextElementSibling;
            if (answer.style.display === "block") {
                answer.style.display = "none";
            } else {
                answer.style.display = "block";
            }
        });
    });

    // 페이지 로드 시 첫 번째 탭(General)의 내용을 표시
    showCategory(1);
});
