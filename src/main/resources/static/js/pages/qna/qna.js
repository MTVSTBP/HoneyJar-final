document.addEventListener("DOMContentLoaded", function() {
    const tabs = document.querySelectorAll(".tab");
    const qnaContainers = document.querySelectorAll(".qna");

    tabs.forEach(tab => {
        tab.addEventListener("click", function(e) {
            e.preventDefault();

            tabs.forEach(t => t.classList.remove("tab_active"));
            this.classList.add("tab_active");

            const category = this.getAttribute("data-category");

            qnaContainers.forEach(container => {
                container.classList.remove("active");
                if (container.id === "category" + category) {
                    container.classList.add("active");
                }
            });
        });
    });

    const questions = document.querySelectorAll(".question");

    questions.forEach(question => {
        question.addEventListener("click", function() {
            const answer = this.nextElementSibling;
            if (answer.style.display === "block") {
                answer.style.display = "none";
            } else {
                answer.style.display = "block";
            }
        });
    });
});