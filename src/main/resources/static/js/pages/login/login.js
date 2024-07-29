document.addEventListener("DOMContentLoaded", function () {
    const kakaoLoginButton = document.querySelector(".kakao_login");

    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
    }

    // 로그인 상태 확인
    const accessToken = getCookie('access_token');
    if (accessToken) {
        console.log('User is logged in');
        // 로그인 상태에 따른 UI 업데이트 등을 수행
        window.location.href = '/'; // 이미 로그인된 경우 홈페이지로 리다이렉트
    } else {
        console.log('User is not logged in');
        // 비로그인 상태에 따른 처리
    }

    // 소셜 로그인(카카오) 버튼 이벤트 리스너
    if (kakaoLoginButton) {
        ['mousedown', 'mouseup', 'mouseleave'].forEach(eventType => {
            kakaoLoginButton.addEventListener(eventType, () => {
                kakaoLoginButton.classList.toggle('clicked', eventType === 'mousedown');
            });
        });

        kakaoLoginButton.addEventListener('touchstart', () => {
            kakaoLoginButton.classList.add('clicked');
        });

        kakaoLoginButton.addEventListener('click', () => {
            // 카카오 로그인 처리 후 서버의 /login 엔드포인트 호출
            fetch('/login', {
                method: 'POST',
                credentials: 'same-origin'
            })
                .then(response => response.json())
                .then(data => {
                    if (data.status === 200) {
                        // 로그인 성공
                        window.location.href = data.data.redirectUrl;
                    } else {
                        // 로그인 실패
                        console.error('Login failed:', data.message);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        });
    }
});