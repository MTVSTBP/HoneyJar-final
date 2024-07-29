document.addEventListener("DOMContentLoaded", function () {
    const imageUpload = document.getElementById('imageUpload');
    const imagePreview = document.getElementById('imagePreview');
    const fileCountMessage = document.getElementById('fileCountMessage');
    const fileTypeMessage = document.getElementById('fileTypeMessage');
    const submitBtn = document.getElementById('submitBtn');
    const modal = document.getElementById('Modal');
    const closeBtn = document.querySelector(".close");
    const completeBtn = document.getElementById('complete');
    const addressInput = document.getElementById('address');
    const addressButton = document.getElementById('openMap');
    const publicPost = document.getElementById('publicPost'); // 공개 여부 토글
    const maxFiles = 5;
    const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];
    let selectedFiles = JSON.parse(localStorage.getItem('selectedFiles')) || [];
    let thumbnailIndex = localStorage.getItem('thumbnailIndex') !== null ? parseInt(localStorage.getItem('thumbnailIndex')) : null;

    function updateImagePreview() {
        imagePreview.innerHTML = '';
        selectedFiles.forEach((fileData, index) => {
            const imageContainer = document.createElement('div');
            imageContainer.classList.add('image-container');

            const img = document.createElement('img');
            img.src = fileData.dataURL;
            img.classList.toggle('thumbnail', thumbnailIndex === index);
            img.addEventListener('click', function() {
                thumbnailIndex = index;
                updateThumbnail();
                validateForm();  // 실시간으로 에러 메시지 숨기기 및 폼 검증
                localStorage.setItem('thumbnailIndex', thumbnailIndex);
            });
            imageContainer.appendChild(img);

            const icon = document.createElement('img');
            icon.src = thumbnailIndex === index ? '/assets/svg/label_important_color.svg' : '/assets/svg/label_important.svg';
            icon.classList.add('thumbnail-icon');
            imageContainer.appendChild(icon);

            const deleteBtn = document.createElement('img');
            deleteBtn.src = "/assets/svg/close.svg";
            deleteBtn.classList.add('delete-button');
            deleteBtn.addEventListener('click', function() {
                selectedFiles.splice(index, 1);
                if (thumbnailIndex === index) {
                    thumbnailIndex = null;
                } else if (thumbnailIndex > index) {
                    thumbnailIndex--;
                }
                updateImagePreview();
                validateForm();  // 실시간으로 에러 메시지 숨기기 및 폼 검증
                resetFileInput();
                localStorage.setItem('selectedFiles', JSON.stringify(selectedFiles));
                localStorage.setItem('thumbnailIndex', thumbnailIndex);
            });
            imageContainer.appendChild(deleteBtn);

            imagePreview.appendChild(imageContainer);
        });
    }

    function resetFileInput() {
        imageUpload.value = '';
    }

    function updateThumbnail() {
        const containers = imagePreview.querySelectorAll('.image-container');
        containers.forEach((container, index) => {
            const img = container.querySelector('img');
            const icon = container.querySelector('.thumbnail-icon');
            img.classList.toggle('thumbnail', thumbnailIndex === index);
            icon.src = thumbnailIndex === index ? '/assets/svg/label_important_color.svg' : '/assets/svg/label_important.svg';
        });
    }

    imageUpload.addEventListener('change', function () {
        fileCountMessage.style.display = 'none';
        fileTypeMessage.style.display = 'none';

        const files = Array.from(imageUpload.files);

        const invalidFiles = files.filter(file => !allowedTypes.includes(file.type));
        if (invalidFiles.length > 0) {
            fileTypeMessage.style.display = 'block';
            resetFileInput();
            return;
        }

        if (selectedFiles.length + files.length > maxFiles) {
            fileCountMessage.style.display = 'block';
            return;
        }

        files.forEach(file => {
            if (selectedFiles.length < maxFiles) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    selectedFiles.push({ name: file.name, dataURL: e.target.result });
                    localStorage.setItem('selectedFiles', JSON.stringify(selectedFiles));
                    updateImagePreview();
                    validateForm();
                };
                reader.readAsDataURL(file);
            }
        });
    });

    function showErrorMessage(element, message) {
        if (element) {
            element.textContent = message;
            element.style.display = 'block';
        }
    }

    function hideErrorMessage(element) {
        if (element) {
            element.style.display = 'none';
        }
    }

    function validateForm() {
        const postTitleElement = document.getElementById('postTitle');
        const categoryElement = document.getElementById('category');
        const contentElement = document.getElementById('content');
        const addressElement = document.getElementById('address');

        const postTitle = postTitleElement ? postTitleElement.value.trim() : '';
        const category = categoryElement ? categoryElement.value : '';
        const content = contentElement ? contentElement.value.trim() : '';
        const address = addressElement ? addressElement.value.trim() : '';

        const postTitleError = document.getElementById('postTitleError');
        const categoryError = document.getElementById('categoryError');
        const contentError = document.getElementById('contentError');
        const imageError = document.getElementById('imageError');
        const addressError = document.getElementById('addressError');

        let isValid = true;

        if (!postTitle) {
            showErrorMessage(postTitleError, '제목을 입력하세요.');
            isValid = false;
        } else {
            hideErrorMessage(postTitleError);
        }

        if (!category) {
            showErrorMessage(categoryError, '카테고리를 선택하세요.');
            isValid = false;
        } else {
            hideErrorMessage(categoryError);
        }

        if (!content) {
            showErrorMessage(contentError, '내용을 입력하세요.');
            isValid = false;
        } else {
            hideErrorMessage(contentError);
        }

        if (!address) {
            showErrorMessage(addressError, '주소를 입력하세요.');
            isValid = false;
        } else {
            hideErrorMessage(addressError);
        }

        if (selectedFiles.length === 0) {
            showErrorMessage(imageError, '사진을 추가하세요.');
            isValid = false;
        } else {
            hideErrorMessage(imageError);
            if (thumbnailIndex === null) {
                showErrorMessage(imageError, '썸네일을 선택하세요.');
                isValid = false;
            } else {
                hideErrorMessage(imageError);
            }
        }

        if (isValid) {
            submitBtn.classList.add('active');
            submitBtn.disabled = false; // 버튼 활성화
        } else {
            submitBtn.classList.remove('active');
            submitBtn.disabled = true; // 버튼 비활성화
        }

        return isValid;
    }

    const postForm = document.getElementById('postForm');
    postForm.addEventListener('input', (event) => {
        hideErrorMessage(document.getElementById(event.target.id + 'Error'));
    });
    postForm.addEventListener('change', (event) => {
        hideErrorMessage(document.getElementById(event.target.id + 'Error'));
    });
    postForm.addEventListener('submit', function (event) {
        event.preventDefault();

        if (!validateForm()) {
            return;
        }

        openModal();
    });

    function openModal() {
        modal.style.display = "block";
    }

    function closeModal() {
        modal.style.display = "none";
    }

    closeBtn.addEventListener("click", closeModal);

    completeBtn.addEventListener("click", function() {
        closeModal();
        window.location.href = "/templates/page/post/postDetail.html";
        localStorage.removeItem('selectedFiles');
        localStorage.removeItem('thumbnailIndex');
    });

    window.addEventListener("click", function(event) {
        if (event.target === modal) {
            closeModal();
        }
    });

    function openMapPage() {
        localStorage.setItem('postFormState', JSON.stringify({
            postTitle: document.getElementById('postTitle').value.trim(),
            category: document.getElementById('category').value,
            content: document.getElementById('content').value.trim(),
            bestMenu: document.getElementById('bestMenu').value.trim(),
            price: document.getElementById('price').value.trim(),
            address: document.getElementById('address').value.trim(),
            publicPost: publicPost.checked  // 공개 여부 상태 저장
        }));
        window.location.href = '/templates/page/post/findAddress.html';
    }

    addressInput.addEventListener('click', openMapPage);
    addressButton.addEventListener('click', openMapPage);

    function restoreFormState() {
        const postFormState = JSON.parse(localStorage.getItem('postFormState'));
        if (postFormState) {
            document.getElementById('postTitle').value = postFormState.postTitle;
            document.getElementById('category').value = postFormState.category;
            document.getElementById('content').value = postFormState.content;
            document.getElementById('bestMenu').value = postFormState.bestMenu;
            document.getElementById('price').value = postFormState.price;
            document.getElementById('address').value = postFormState.address;
            publicPost.checked = postFormState.publicPost; // 공개 여부 상태 복원
            localStorage.removeItem('postFormState');
        }
        updateImagePreview();
    }

    restoreFormState();

    // 공개 여부 토글 초기 상태 설정
    publicPost.checked = true; // 초기값 설정

    // 공개 여부 토글 이벤트 설정
    publicPost.addEventListener('change', function () {
        console.log('Public post:', publicPost.checked);
    });

});
