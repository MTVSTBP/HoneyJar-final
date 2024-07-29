document.addEventListener("DOMContentLoaded", function () {
    const modalContainer = document.querySelector('.modal-container');
    const modalText = document.querySelector('.modal__text');

    const submitBtn = document.getElementById('postForm');
    const modifyButton = document.getElementById('modifyButton');
    const completeButton = document.getElementById('completeButton');

    const nickNameInput = document.getElementById('nickName');
    const introductionInput = document.getElementById('introduction');

    let originalNickName = nickNameInput.value;
    let originalIntroduction = introductionInput.value;

    function checkForChanges() {
        if (nickNameInput.value !== originalNickName || introductionInput.value !== originalIntroduction) {
            modifyButton.classList.add('active');
        } else {
            modifyButton.classList.remove('active');
        }
    }

    nickNameInput.addEventListener('input', checkForChanges);
    introductionInput.addEventListener('input', checkForChanges);

    submitBtn.addEventListener("submit", (event) => {
        event.preventDefault();

        const nickName = nickNameInput.value;
        const introduction = introductionInput.value;
        var url = "/mypage/correction";

        let data = {
            userName: nickName,
            userPr: introduction
        };

        sendData(data, url);
    });

    function sendData(data, url) {
        fetch(url, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then(res => {
            if (!res.ok) {
                throw new Error('Network response was not ok');
            }
            return res.json();
        }).then(data => {
            modalText.textContent = "수정 완료 되었어요!";
            modalContainer.style.display = "flex";
            completeButton.onclick = () => {
                window.location.href = "/mypage";
            };
        }).catch(error => {
            modalText.textContent = "수정에 실패했습니다.";
            modalContainer.style.display = "flex";
            completeButton.onclick = () => {
                modalContainer.style.display = "none";
            };
            console.error('There was a problem with the fetch operation:', error);
        });
    }

    completeButton.onclick = () => {
        modalContainer.style.display = "none";
    };
});