export default {
    openModal() {
        modal.style.display = "block";
    },
    closeModal() {
        modal.style.display = "none";
    },
    linkToPage(url) {
        window.location.href = url;
    }
};