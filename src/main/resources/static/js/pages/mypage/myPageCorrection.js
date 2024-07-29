document.addEventListener("DOMContentLoaded", function () {
    const profileChangeButton = document.querySelector('.profile__change-button');
    const imageUpload = document.getElementById('imageUpload');
    const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];

    const myPageForm = document.getElementById('myPageForm');
    const nickNameInput = document.getElementById('nickName');
    const introductionInput = document.getElementById('introduction');

    const modal = document.querySelector('.modal-container');

    const modifyButton = document.getElementById('modifyButton');
    const completeButton = document.getElementById('completeButton');

    const data = {
        profileImage: '/assets/svg/base_profile.svg',
        nickName: 'Kamil Lee',
        introduction: '한 달이나 남았어요! 여러분 힘냅시다!',
        category: [
            'category-1', 'category-3', 'category-6', 'category-8'
        ]
    };

    /*
    페이지 로드 시 초기 데이터를 설정합니다.
    사용자의 프로필 이미지, 닉네임, 소개, 선호 카테고리를 설정하고
    프로필 이미지 변경 버튼과 이미지 업로드 이벤트 리스너를 추가합니다.
    또한 폼 필드의 입력 상태를 업데이트합니다.
    */
    function loadInitialData() {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]');
        checkboxes.forEach(checkbox => {
            if (data.category.includes(checkbox.id)) {
                checkbox.checked = true;
            }
        });

        if (nickNameInput) nickNameInput.value = data.nickName;
        if (introductionInput) introductionInput.value = data.introduction;

        if (profileChangeButton) {
            profileChangeButton.addEventListener('click', () => {
                if (imageUpload) imageUpload.click();
            });
        }

        if (imageUpload) {
            imageUpload.addEventListener('change', handleImageUpload);
        }

        updateFormFieldInputClass(data.nickName, data.introduction);
    }

    /*
    사용자가 프로필 이미지를 업로드할 때 호출됩니다.
    업로드된 이미지를 캔버스에 그려 크기를 조정하고, 이미지 URL을 프로필 이미지 요소에 설정합니다.
    그리고 폼 유효성 검사를 수행합니다.
    */
    function handleImageUpload(event) {
        const file = event.target.files[0];
        if (file && allowedTypes.includes(file.type)) {
            const reader = new FileReader();
            reader.onload = (e) => {
                const img = new Image();
                img.onload = () => {
                    const canvas = document.createElement('canvas');
                    const ctx = canvas.getContext('2d');
                    canvas.width = canvas.height = 90;
                    ctx.imageSmoothingEnabled = true;
                    ctx.imageSmoothingQuality = 'high';
                    ctx.drawImage(img, 0, 0, 90, 90);
                    document.querySelector('.profile__image').src = canvas.toDataURL('image/jpeg');
                    validateForm();
                };
                img.src = e.target.result;
            };
            reader.readAsDataURL(file);
        } else {
            alert('JPEG, PNG, GIF 형식의 이미지 파일만 업로드 가능합니다.');
        }
    }

    /*
    addCategoryListeners 함수는 카테고리 체크박스 요소에 이벤트 리스너를 추가합니다.
    체크박스 선택 상태에 따라 카테고리 레이블의 스타일을 변경하고 폼 유효성 검사를 수행합니다.
    */
    function addCategoryListeners() {
        const categoryCheckboxes = document.querySelectorAll('.category__checkbox');
        categoryCheckboxes.forEach(checkbox => {
            checkbox.addEventListener('change', function() {
                const label = this.closest('.category__label');
                const customCheckbox = label.querySelector('.category__custom-checkbox');
                const categoryText = label.querySelector('.category__text');

                if (this.checked) {
                    customCheckbox.style.borderColor = '#ff8243';
                    categoryText.style.color = '#ff8243';
                } else {
                    customCheckbox.style.borderColor = '#979797';
                    categoryText.style.color = '#979797';
                }
                validateForm();
            });
        });
    }

    /*
    addFormValidationListeners 함수는 폼 필드(닉네임, 소개, 카테고리)에 입력 이벤트 리스너를 추가합니다.
    이 리스너들은 폼 유효성 검사를 트리거합니다.
    */
    function addFormValidationListeners() {
        nickNameInput.addEventListener('input', validateForm);
        introductionInput.addEventListener('input', validateForm);
        document.querySelectorAll('input[type="checkbox"]').forEach(checkbox => {
            checkbox.addEventListener('change', validateForm);
        });
    }

    /*
    validateForm 함수는 폼 필드의 입력 값과 선택된 카테고리 수를 확인하여
    폼이 유효한지 여부를 반환합니다.
    유효성 검사 결과에 따라 수정 버튼의 상태와 폼 필드 입력 상태를 업데이트합니다.
    */
    function validateForm() {
        const nickName = nickNameInput.value.trim();
        const introduction = introductionInput.value.trim();
        const checkedCategories = document.querySelectorAll('input[type="checkbox"]:checked').length;
        const profileImage = document.querySelector('.profile__image').src;

        const defaultImageUrl = '/assets/svg/base_profile.svg';
        const isImageUploaded = profileImage !== defaultImageUrl;

        const isValid = nickName !== '' && introduction !== '' && checkedCategories > 0;

        console.log('Validating form:', {
            defaultImageUrl,
            nickName,
            introduction,
            checkedCategories,
            isImageUploaded,
            isValid
        });

        updateModifyButton(isValid);
        updateFormFieldInputClass(nickName, introduction);

        return isValid;
    }

    /*
    폼 필드의 입력 값이 있는지 여부에 따라
    폼 필드 요소에 'active' CSS 클래스를 추가하거나 제거합니다.
    이를 통해 입력 필드의 스타일을 변경할 수 있습니다.
    */
    function updateFormFieldInputClass(nickName, introduction) {
        const nickNameInputElement = document.getElementById('nickName');
        const introductionInputElement = document.getElementById('introduction');

        if (nickName === '') {
            nickNameInputElement.classList.remove('active');
        } else {
            nickNameInputElement.classList.add('active');
        }

        if (introduction === '') {
            introductionInputElement.classList.remove('active');
        } else {
            introductionInputElement.classList.add('active');
        }
    }

    /*
    updateModifyButton 함수는 폼 유효성 검사 결과에 따라
    수정 버튼의 CSS 클래스와 커서 스타일을 업데이트합니다.
    폼이 유효한 경우 버튼에 'active' 클래스를 추가하고 커서를 포인터로 변경합니다.
    폼이 유효하지 않은 경우 'active' 클래스를 제거하고 커서를 not-allowed로 변경합니다.
    */
    function updateModifyButton(isValid) {
        modifyButton.classList.toggle('active', isValid);
        modifyButton.style.cursor = isValid ? 'pointer' : 'not-allowed';
    }

    /*
    openModal 함수는 모달 컨테이너의 display 속성을 'block'으로 설정하여 모달을 표시합니다.
    */
    function openModal() {
        console.log("Opening modal");
        modal.style.display = "block";
    }

    /*
    closeModal 함수는 모달 컨테이너의 display 속성을 'none'으로 설정하여 모달을 숨깁니다.
    */
    function closeModal() {
        modal.style.display = "none";
    }

    modifyButton.addEventListener('click', function(event) {
        event.preventDefault();
        console.log('Modify button clicked');
        if (validateForm()) {
            console.log('Form is valid, opening modal');
            openModal();
        } else {
            console.log('Form is invalid, not opening modal');
        }
    });

    myPageForm.addEventListener('submit', function (event) {
        event.preventDefault();
        if (validateForm()) {
            openModal();
        }
    });

    completeButton.addEventListener("click", function() {
        closeModal();
        window.location.href = "/src/pages/html/home.html";
    });

    loadInitialData();
    addFormValidationListeners();
    addCategoryListeners();
    validateForm();
});