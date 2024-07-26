document.addEventListener("DOMContentLoaded", function() {
    const deleteButton = document.querySelector(".delete-button");
    const deleteConfirmModal = document.getElementById("deleteConfirmModal");
    const deleteSuccessModal = document.getElementById("deleteSuccessModal");
    const confirmDeleteButton = document.getElementById("confirmDelete");
    const completeDeleteButton = document.getElementById("completeDelete");
    const closeModalButtons = document.querySelectorAll(".modal .close");

    const urlParams = new URLSearchParams(window.location.search);
    const currentPage = urlParams.get('page');

    deleteButton.addEventListener("click", function(event) {
        event.preventDefault();
        deleteConfirmModal.style.display = "block";
    });

    closeModalButtons.forEach(button => {
        button.addEventListener("click", function() {
            deleteConfirmModal.style.display = "none";
            deleteSuccessModal.style.display = "none";
        });
    });

    confirmDeleteButton.addEventListener("click", function() {
        const inquiryId = deleteButton.getAttribute("data-inquiry-id");
        fetch(`/admin/inquiry/delete/${inquiryId}?page=${currentPage}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    deleteConfirmModal.style.display = "none";
                    deleteSuccessModal.style.display = "block";
                } else {
                    alert("삭제에 실패했습니다.");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("삭제에 실패했습니다.");
            });
    });

    completeDeleteButton.addEventListener("click", function() {
        window.location.href = `/admin/inquiry?page=${currentPage}`;
    });
});
