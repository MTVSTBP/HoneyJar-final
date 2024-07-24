document.addEventListener("DOMContentLoaded", function() {
    const submitBtn = document.getElementById('postForm');
    const modalContainer = document.querySelector('.modal-container');
    const modalText = document.querySelector('.modal__text');
    const completeButton = document.getElementById('completeButton');

    submitBtn.addEventListener("submit", (event) => {
        event.preventDefault();

        const nickName = document.getElementById('nickName').value;
        const introduction = document.getElementById('introduction').value;
        var url = "/mypage/correction";

        fetch(url, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'charset': 'UTF-8'
            },
            body: JSON.stringify({
                userName: nickName,
                userPr: introduction
            })
        }).then(res => {
            if (!res.ok) {
                throw new Error('Network response was not ok');
            }
            return res.json();
        }).then(data => {
            modalText.textContent = "수정 완료 되었어요!";
            modalContainer.style.display = "flex";  // 모달 표시
            completeButton.onclick = () => {
                window.location.href = "/mypage";  // 성공 시 이동
            };
        }).catch(error => {
            modalText.textContent = "수정에 실패했습니다.";
            modalContainer.style.display = "flex";  // 모달 표시
            completeButton.onclick = () => {
                modalContainer.style.display = "none";  // 실패 시 모달 닫기
            };
            console.error('There was a problem with the fetch operation:', error);
        });
    });

    completeButton.onclick = () => {
        modalContainer.style.display = "none";  // 기본적으로 모달 닫기
    };
});