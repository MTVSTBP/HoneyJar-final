document.addEventListener("DOMContentLoaded", function () {
    const submitBtn = document.getElementById('submitBtn');
    const modal = document.getElementById('Modal');
    const closeBtn = document.querySelector(".close");
    const completeBtn = document.getElementById('complete');

    // Function for showing and hiding error messages
    // Validate form function as before

    const postForm = document.getElementById('Form');
    postForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent default form submission

        if (!validateForm(true)) {
            return;
        }

        // Serialize form data manually or use FormData
        const formData = new FormData(postForm);

        // Perform Ajax POST request
        fetch('/settings/inquiry/write', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                openModal();
            })
            .catch(error => console.error('Error:', error));
    });

    // Modal handling, close button, etc. as before
});
