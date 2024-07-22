async function uploadFiles() {
    const fileInput = document.getElementById('fileInput');
    const files = fileInput.files;
    const formData = new FormData();
    for (let i = 0; i < files.length; i++) {
        formData.append('file', files[i]);
    }

    const response = await fetch('/files', {
        method: 'POST',
        body: formData
    });

    const fileUrls = await response.json();
    fileUrls.forEach(displayImage);
}

function displayImage(url) {
    const imageContainer = document.getElementById('imageContainer');
    const img = document.createElement('img');
    img.src = url;
    imageContainer.appendChild(img);
}