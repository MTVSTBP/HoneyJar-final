document.addEventListener("DOMContentLoaded", function () {
    const logo = document.querySelector('.logo');
    const loginForm = document.getElementById('loginForm');

    const adminLoginButton = document.getElementById('adminLoginButton');

    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const emailError = document.getElementById('email-error');
    const passwordError = document.getElementById('password-error');

    const submitLoginButton = document.getElementById('submitLoginButton');

    // 이메일(사용자 이름) 유효성 검사 함수
    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }

    // 비밀번호 유효성 검사 함수
    function validatePassword(password) {
        // 8~13자의 영문 대/소문자, 숫자, 특수문자
        const re = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,13}$/;
        return re.test(password);
    }

    // 입력 필드 변경 감지 및 로그인 버튼 상태 업데이트 함수
    function updateLoginButtonState() {
        const isEmailValid = validateEmail(emailInput.value.trim());
        const isPasswordValid = validatePassword(passwordInput.value)

        if (isEmailValid) {
            emailInput.classList.remove('error');
            emailError.style.display = 'none';
        } else {
            emailInput.classList.add('error');
            emailError.textContent = '정확한 이메일 형식으로 작성해 주세요.'
            emailError.style.display = 'block';
        }

        if (isPasswordValid) {
            passwordInput.classList.remove('error');
            passwordError.style.display = 'none';
        } else {
            passwordInput.classList.add('error');
            passwordError.textContent = '8~13자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요.'
            passwordError.style.display = 'block';
        }

        submitLoginButton.disabled = !(isEmailValid && isPasswordValid);
    }

    // 에러 메시지 표시 함수
    function showError(element, message) {
        element.textContent = message;
        element.style.display = 'block';
    }

    // 에러 메시지 초기화 함수
    function clearErrors() {
        emailError.textContent = '';
        emailError.style.display = 'none';
        passwordError.textContent = '';
        passwordError.style.display = 'none';
    }

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

    // 관리자 로그인 버튼 이벤트 리스너
    if (adminLoginButton) {
        ['mousedown', 'mouseup', 'mouseleave'].forEach(eventType => {
            adminLoginButton.addEventListener(eventType, () => {
                adminLoginButton.classList.toggle('clicked', eventType === 'mousedown');
            });
        });

        adminLoginButton.addEventListener('touchstart', () => {
            adminLoginButton.classList.add('clicked');
        });

        adminLoginButton.addEventListener('touchend', () => {
            adminLoginButton.classList.remove('clicked');
        });

        adminLoginButton.addEventListener("click", () => {
            logo.classList.add('mini');
            adminLoginButton.classList.add('hidden');
            loginForm.classList.remove('hidden');
        });
    }

    // 입력 필드 변경 감지 이벤트 리스너
    emailInput.addEventListener('input', updateLoginButtonState);
    passwordInput.addEventListener('input', updateLoginButtonState);

    // 로그인 제출 버튼 이벤트 리스너
    if (submitLoginButton) {
        ['mousedown', 'mouseup', 'mouseleave'].forEach(eventType => {
            submitLoginButton.addEventListener(eventType, () => {
                submitLoginButton.classList.toggle('clicked', eventType === 'mousedown');
            });
        });

        submitLoginButton.addEventListener('touchstart', () => {
            submitLoginButton.classList.add('clicked');
        });

        submitLoginButton.addEventListener('touchend', () => {
            submitLoginButton.classList.remove('clicked');
        });

        submitLoginButton.addEventListener("click", (e) => {
            e.preventDefault(); // 기본 제출 동작 방지 차원

            const email = emailInput.value.trim();
            const password = passwordInput.value.trim();

            clearErrors(); // 이전 오류 메시지 초기화

            // AJAX를 사용하여 서버에 로그인 요청
            fetch('/admin/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({email, password}),
            })
                .then(response => response.json())
                .then(data => {
                    if (data.status === 200) {
                        // 로그인 성공
                        window.location.href = '/'; // 홈페이지로 리다이렉트
                    } else {
                        // 로그인 실패
                        showError(emailError, data.message);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    showError(emailError, '로그인 중 오류가 발생했습니다.');
                });
        });
    }
});