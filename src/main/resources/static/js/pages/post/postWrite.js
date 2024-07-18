document.addEventListener("DOMContentLoaded", function () {
    const imageUpload = document.getElementById('imageUpload');
    const imagePreview = document.getElementById('imagePreview');
    const fileCountMessage = document.getElementById('fileCountMessage');
    const fileTypeMessage = document.getElementById('fileTypeMessage');
    const submitBtn = document.getElementById('submitBtn');
    const placeNameInput = document.getElementById('placeName');
    const placeNameButton = document.getElementById('openMap');
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
        const contentElement = document.getElementById('content');
        const placeNameElement = document.getElementById('placeName');

        const postTitle = postTitleElement ? postTitleElement.value.trim() : '';
        const content = contentElement ? contentElement.value.trim() : '';
        const placeName = placeNameElement ? placeNameElement.value.trim() : '';

        const postTitleError = document.getElementById('postTitleError');
        const contentError = document.getElementById('contentError');
        const imageError = document.getElementById('imageError');
        const placeNameError = document.getElementById('placeNameError');

        let isValid = true;

        if (!postTitle) {
            showErrorMessage(postTitleError, '제목을 입력하세요.');
            isValid = false;
        } else {
            hideErrorMessage(postTitleError);
        }

        if (!content) {
            showErrorMessage(contentError, '내용을 입력하세요.');
            isValid = false;
        } else {
            hideErrorMessage(contentError);
        }

        if (!placeName) {
            showErrorMessage(placeNameError, '주소를 입력하세요.');
            isValid = false;
        } else {
            hideErrorMessage(placeNameError);
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

    async function getPresignedUrl(fileName) {
        const response = await fetch(`/s3/pre-signed-url/image/${fileName}`);
        const data = await response.json();
        if (response.ok) {
            return data.presignedUrl;
        } else {
            throw new Error(data.error || 'Failed to get presigned URL');
        }
    }

    async function uploadFileToS3(file, presignedUrl) {
        await fetch(presignedUrl, {
            method: 'PUT',
            body: file,
            headers: {
                'Content-Type': file.type
            }
        });
    }

    async function uploadFilesAndGetUrls() {
        const urls = [];
        for (const file of selectedFiles) {
            const fileName = `${Date.now()}-${file.name}`;
            const presignedUrl = await getPresignedUrl(fileName);
            await uploadFileToS3(file, presignedUrl);
            urls.push(presignedUrl.split('?')[0]); // URL에서 쿼리 파라미터 제거
        }
        return urls;
    }

    postForm.addEventListener('submit', async function(event) {
        event.preventDefault();

        if (!validateForm()) {
            return;
        }

        try {
            const imageUrls = await uploadFilesAndGetUrls();
            const mainImageUrl = imageUrls[thumbnailIndex];

            const formData = new FormData(postForm);
            formData.append('imageUrls', JSON.stringify(imageUrls));
            formData.append('mainImageUrl', mainImageUrl);

            const response = await fetch('/post/write', {  // 여기가 중요한 부분입니다.
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.json();

            if (response.ok) {
                window.location.href = `/post/detail?postId=${result.postId}`;
            } else {
                alert(`Error: ${result.message}\n${result.error}`);
            }
        } catch (error) {
            console.error('Error submitting form:', error);
            alert('An error occurred while submitting the form.');
        }
    });

    function openMapPage() {
        localStorage.setItem('postFormState', JSON.stringify({
            postTitle: document.getElementById('postTitle').value.trim(),
            content: document.getElementById('content').value.trim(),
            bestMenu: document.getElementById('bestMenu').value.trim(), // 유지
            price: document.getElementById('price').value.trim(), // 유지
            placeName: document.getElementById('placeName').value.trim()
        }));
        window.location.href = '/post/map';
    }

    // 주소 입력 필드 클릭 시
    placeNameInput.addEventListener('click', openMapPage);
    placeNameButton.addEventListener('click', openMapPage);

    // 폼 상태 복원 함수
    function restoreFormState() {
        const postFormState = JSON.parse(localStorage.getItem('postFormState'));
        if (postFormState) {
            document.getElementById('postTitle').value = postFormState.postTitle;
            document.getElementById('content').value = postFormState.content;
            document.getElementById('bestMenu').value = postFormState.bestMenu;
            document.getElementById('price').value = postFormState.price;
            document.getElementById('placeName').value = postFormState.placeName;
            localStorage.removeItem('postFormState');
        }

        // 주소 복원
        const selectedPlace = JSON.parse(localStorage.getItem('selectedPlace'));
        if (selectedPlace) {
            document.getElementById('placeName').value = selectedPlace.place_name || selectedPlace.road_address_name;
            localStorage.removeItem('selectedPlace');
        }

        updateImagePreview();
    }

    // 초기화 시 폼 상태 복원
    restoreFormState();
});
