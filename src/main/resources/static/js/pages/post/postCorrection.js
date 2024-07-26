function dataURLtoBlob(dataurl) {
    const arr = dataurl.split(',');
    if (arr.length !== 2) {
        throw new Error("Invalid dataURL format");
    }

    const mimeMatch = arr[0].match(/:(.*?);/);
    if (!mimeMatch) {
        throw new Error("Invalid MIME type in dataURL");
    }

    const mime = mimeMatch[1];
    const bstr = atob(arr[1]);
    let n = bstr.length;
    const u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], { type: mime });
}


document.addEventListener("DOMContentLoaded", function () {
    function htmlDecode(input) {
        const doc = new DOMParser().parseFromString(input, "text/html");
        return doc.documentElement.textContent;
    }

    const imageUpload = document.getElementById('imageUpload');
    const imagePreview = document.getElementById('imagePreview');
    const fileCountMessage = document.getElementById('fileCountMessage');
    const fileTypeMessage = document.getElementById('fileTypeMessage');
    const submitBtn = document.getElementById('submitBtn');
    const placeNameInput = document.getElementById('placeNameInput');
    const placeNameButton = document.getElementById('openMap');
    const postForm = document.getElementById('postForm');
    const errorMessage = document.getElementById('errorMessage');
    const placeIdField = document.getElementById('placeId');
    const postIdField = document.getElementById('postId');
    const placeNameField = document.getElementById('placeName');
    const placeXField = document.getElementById('placeXCoordinate');
    const placeYField = document.getElementById('placeYCoordinate');
    const placeRoadAddressNameField = document.getElementById('placeRoadAddressName');
    const categoryField = document.getElementById('category');
    const modal = document.getElementById('Modal');
    const completeBtn = document.getElementById('complete');
    const maxFiles = 5;
    const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];
    let selectedFiles = JSON.parse(localStorage.getItem('selectedFiles')) || [];
    let thumbnailIndex = localStorage.getItem('thumbnailIndex') !== null ? parseInt(localStorage.getItem('thumbnailIndex')) : null;
    let postId;

    // 서버에서 전달받은 기존 이미지 URL 배열을 JavaScript 배열로 변환
    let existingImageUrls = [];
    try {
        const initialDataElement = document.getElementById('initial-data');
        if (initialDataElement) {
            const rawJsonData = initialDataElement.textContent.trim();
            const decodedJsonData = htmlDecode(rawJsonData); // HTML 디코딩
            existingImageUrls = JSON.parse(decodedJsonData);
        }
    } catch (e) {
        console.error('Failed to parse JSON data:', e);
    }

    // 기존 이미지 URL을 selectedFiles 배열에 추가
    existingImageUrls.forEach(url => {
        const fileData = { name: url.split('/').pop(), dataURL: url };
        selectedFiles.push(fileData);
    });
    localStorage.setItem('selectedFiles', JSON.stringify(selectedFiles));
    updateImagePreview();

    function updateImagePreview() {
        imagePreview.innerHTML = '';
        selectedFiles.forEach((fileData, index) => {
            const imageContainer = document.createElement('div');
            imageContainer.classList.add('image-container');

            const img = document.createElement('img');
            img.src = fileData.dataURL;
            img.classList.toggle('thumbnail', thumbnailIndex === index);
            img.addEventListener('click', function () {
                thumbnailIndex = index;
                updateThumbnail();
                validateForm();
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
            deleteBtn.addEventListener('click', function () {
                selectedFiles.splice(index, 1);
                if (thumbnailIndex === index) {
                    thumbnailIndex = null;
                } else if (thumbnailIndex > index) {
                    thumbnailIndex--;
                }
                updateImagePreview();
                validateForm();
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

    function dataURLtoBlob(dataurl) {
        const arr = dataurl.split(',');
        if (arr.length !== 2) {
            console.error("Invalid dataURL format:", dataurl);
            throw new Error("Invalid dataURL format");
        }

        const mimeMatch = arr[0].match(/:(.*?);/);
        if (!mimeMatch) {
            console.error("Invalid MIME type in dataURL:", dataurl);
            throw new Error("Invalid MIME type in dataURL");
        }

        const mime = mimeMatch[1];
        const bstr = atob(arr[1]);
        let n = bstr.length;
        const u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new Blob([u8arr], { type: mime });
    }

    function compressImage(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = function (event) {
                const img = new Image();
                img.onload = function () {
                    const canvas = document.createElement('canvas');
                    const ctx = canvas.getContext('2d');
                    const maxWidth = 800;
                    const maxHeight = 800;
                    let width = img.width;
                    let height = img.height;

                    if (width > height) {
                        if (width > maxWidth) {
                            height *= maxWidth / width;
                            width = maxWidth;
                        }
                    } else {
                        if (height > maxHeight) {
                            width *= maxHeight / height;
                            height = maxHeight;
                        }
                    }

                    canvas.width = width;
                    canvas.height = height;
                    ctx.drawImage(img, 0, 0, width, height);

                    const dataURL = canvas.toDataURL('image/jpeg', 0.7);
                    resolve(dataURL);
                };
                img.src = event.target.result;
            };
            reader.readAsDataURL(file);
        });
    }

    imageUpload.addEventListener('change', async function () {
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

        for (const file of files) {
            if (selectedFiles.length < maxFiles) {
                const compressedDataURL = await compressImage(file);
                selectedFiles.push({ name: file.name, dataURL: compressedDataURL });
                localStorage.setItem('selectedFiles', JSON.stringify(selectedFiles));
                updateImagePreview();
                validateForm();
            }
        }
        resetFileInput();
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
        const placeNameElement = document.getElementById('placeNameInput');

        const postTitle = postTitleElement ? postTitleElement.value.trim() : '';
        const content = contentElement ? contentElement.value.trim() : '';
        const placeName = placeNameElement ? placeNameElement.value.trim().replace(/^,/, '') : '';

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
            submitBtn.disabled = false;
        } else {
            submitBtn.classList.remove('active');
            submitBtn.disabled = true;
        }

        return isValid;
    }

    postForm.addEventListener('submit', async function (event) {
        event.preventDefault();
        hideErrorMessage(errorMessage);

        if (!validateForm()) {
            return;
        }

        // 선택한 장소 정보를 숨겨진 필드에 저장
        const selectedPlace = JSON.parse(localStorage.getItem('selectedPlace'));
        if (selectedPlace) {
            placeIdField.value = selectedPlace.placeId || placeIdField.value;
            placeNameField.value = selectedPlace.road_address_name.replace(/^,/, '');
            placeXField.value = selectedPlace.x;
            placeYField.value = selectedPlace.y;
            placeRoadAddressNameField.value = selectedPlace.road_address_name;
        } else {
            console.log("Using existing placeId: ", placeIdField.value);
        }

        console.log("placeIdField value before submission: ", placeIdField.value);

        const formData = new FormData(postForm);

        // Add PlaceDTO fields to formData
        formData.append('place.placeId', placeIdField.value);
        formData.append('place.name', placeNameField.value);
        formData.append('place.xCoordinate', placeXField.value);
        formData.append('place.yCoordinate', placeYField.value);
        formData.append('place.roadAddressName', placeRoadAddressNameField.value);

        // 기존 이미지 URL을 formData에 추가
        existingImageUrls.forEach((url) => {
            formData.append('existingImageUrls', url);
        });

        // Add selected files to formData
        selectedFiles.forEach((fileData, index) => {
            if (index === thumbnailIndex) {
                // 썸네일 이미지는 mainImageFile로 추가
                if (!fileData.dataURL.startsWith('data:')) {
                    // 기존 썸네일 이미지는 그대로 추가
                    formData.append('mainImageUrl', fileData.dataURL);
                    // mainImageFile 필드를 위해 빈 파일을 추가
                    formData.append('mainImageFile', new Blob(), fileData.name);
                } else {
                    // 새로운 썸네일 이미지는 Blob으로 변환하여 추가
                    const mainImageBlob = dataURLtoBlob(fileData.dataURL);
                    formData.append('mainImageUrl', fileData.name);
                    formData.append('mainImageFile', mainImageBlob, fileData.name);
                }
            } else {
                // 나머지 이미지는 files로 추가
                if (!fileData.dataURL.startsWith('data:')) {
                    // 기존 이미지 URL은 Blob으로 변환하지 않고 그대로 추가
                    formData.append('existingImageUrls', fileData.dataURL);
                } else {
                    // 새로 추가된 이미지만 Blob으로 변환하여 추가
                    const blob = dataURLtoBlob(fileData.dataURL);
                    formData.append('files', blob, fileData.name);
                }
            }
        });

        // 삭제된 이미지를 formData에 추가
        const deletedImages = existingImageUrls.filter(url => !selectedFiles.some(fileData => fileData.dataURL === url));
        deletedImages.forEach((url) => {
            formData.append('deletedImages', url);
        });

        try {
            const response = await fetch(postForm.action, {
                method: 'POST',
                body: formData,
                redirect: 'follow'
            });

            if (response.redirected) {
                window.location.href = response.url;
                return;
            }

            if (!response.ok) {
                const result = await response.json();
                throw new Error(result.error || 'Failed to submit');
            }

            const result = await response.json();
            if (result.error) {
                showErrorMessage(errorMessage, result.error);
            } else {
                postId = result.postId;
                localStorage.removeItem('selectedFiles'); // 로컬스토리지에서 selectedFiles 삭제
                localStorage.removeItem('thumbnailIndex'); // 로컬스토리지에서 thumbnailIndex 삭제
                modal.style.display = 'block';
            }
        } catch (error) {
            console.error('Error submitting form:', error);
            showErrorMessage(errorMessage, 'An error occurred while submitting the form.');
        }
    });


    completeBtn.addEventListener('click', function () {
        window.location.href = `/post`;
    });

    function openMapPage() {
        localStorage.removeItem('selectedPlace');

        localStorage.setItem('postFormState', JSON.stringify({
            postTitle: document.getElementById('postTitle').value.trim(),
            content: document.getElementById('content').value.trim(),
            bestMenu: document.getElementById('bestMenu').value.trim(),
            price: document.getElementById('price').value.trim(),
            placeName: document.getElementById('placeNameInput').value.trim().replace(/^,/, ''),
            category: categoryField.value.trim(),
            placeId: placeIdField.value.trim(),
            postId: postIdField.value.trim(),
            placeRoadAddressName : document.getElementById("placeRoadAddressName").value.trim().replace(/^,/, '')
        }));
        const currentUrl = window.location.href;
        window.location.href = '/post/map?redirectTo=' + encodeURIComponent(currentUrl);
    }

    placeNameInput.addEventListener('click', openMapPage);
    placeNameButton.addEventListener('click', openMapPage);

    function restoreFormState() {
        const postFormState = JSON.parse(localStorage.getItem('postFormState'));
        if (postFormState) {
            document.getElementById('postTitle').value = postFormState.postTitle;
            document.getElementById('content').value = postFormState.content;
            document.getElementById('bestMenu').value = postFormState.bestMenu;
            document.getElementById('price').value = postFormState.price;
            document.getElementById('placeNameInput').value = postFormState.placeRoadAddressName.replace(/^,/, '');
            document.getElementById('placeName').value = postFormState.placeName.replace(/^,/, '');
            categoryField.value = postFormState.category;
            placeIdField.value = postFormState.placeId;
            localStorage.removeItem('postFormState');
            postId = postFormState.postId;
        }

        const selectedPlace = JSON.parse(localStorage.getItem('selectedPlace'));
        if (selectedPlace) {
            document.getElementById('placeNameInput').value = selectedPlace.road_address_name.replace(/^,/, '');
            placeIdField.value = selectedPlace.placeId || placeIdField.value;
            placeXField.value = selectedPlace.x;
            placeYField.value = selectedPlace.y;
            placeName.value = selectedPlace.place_name.replace(/^,/, '');
            localStorage.removeItem('selectedPlace');
        }

        updateImagePreview();
    }

    restoreFormState();
});
