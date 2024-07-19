document.addEventListener('DOMContentLoaded', function() {
    // 뒤로 가기 버튼 기능
    var backButton = document.getElementById('back-button');
    
    if (backButton) {
        backButton.addEventListener('click', function(event) {
            event.preventDefault(); // 기본 링크 동작 방지
            window.history.back(); // 브라우저의 뒤로 가기 동작 수행
        });
    }

    // 모든 더보기 버튼에 클릭 이벤트 등록
    function registerMoreHorizClickEvents() {
        const moreHorizImages = document.querySelectorAll('.more_h img');

        moreHorizImages.forEach(function(moreHorizImage) {
            moreHorizImage.addEventListener('click', function(event) {
                event.stopPropagation();
                const clickBox = this.nextElementSibling;
                clickBox.style.display = (clickBox.style.display === 'block') ? 'none' : 'block';
            });
        });

        document.addEventListener('click', function(event) {
            moreHorizImages.forEach(function(moreHorizImage) {
                const clickBox = moreHorizImage.nextElementSibling;
                if (!clickBox.contains(event.target)) {
                    clickBox.style.display = 'none';
                }
            });
        });
    }

    // 로컬 스토리지에서 카테고리 목록 불러오기
    // function loadCategories() {
    //     const categories = JSON.parse(localStorage.getItem('categories')) || {};
    //
    //     Object.keys(categories).forEach(category => {
    //         const categoryList = document.getElementById(`${category}_list`);
    //         if (categoryList) {
    //             categories[category].forEach((item, index) => {
    //                 const newItem = document.createElement('li');
    //                 newItem.classList.add('item');
    //                 newItem.innerHTML = `
    //                     <p>${item}</p>
    //                     <div class="more_h">
    //                         <img src="/assets/svg/more_horiz.svg" alt="more_horiz">
    //                         <div class="click_box">
    //                             <div class="d_flex">
    //                                 <a class="edit_p" href="/src/pages/html/adminCategroyCorrection.html?category=${category}&index=${index}">수정</a>
    //                                 <a class="delete_p" href="javascript:void(0)" data-category="${category}" data-index="${index}">삭제</a>
    //                             </div>
    //                         </div>
    //                     </div>
    //                 `;
    //                 categoryList.appendChild(newItem);
    //             });
    //         }
    //     });

        // 새로운 항목에 대해 클릭 이벤트 등록
        registerMoreHorizClickEvents();
        registerDeleteClickEvents();


    // 삭제 클릭 이벤트 등록
    function registerDeleteClickEvents() {
        const deleteButtons = document.querySelectorAll('.delete_p');

        deleteButtons.forEach(function(deleteButton) {
            deleteButton.addEventListener('click', function(event) {
                event.preventDefault();
                const category = this.getAttribute('data-category');
                const index = this.getAttribute('data-index');
                openDeleteConfirmModal(category, index);
            });
        });
    }

    // 삭제 확인 모달 열기
    function openDeleteConfirmModal(category, index) {
        const deleteConfirmModal = document.getElementById('deleteConfirmModal');
        deleteConfirmModal.style.display = 'block';

        // 현재 삭제하려는 카테고리와 인덱스를 모달에 저장
        deleteConfirmModal.setAttribute('data-category', category);
        deleteConfirmModal.setAttribute('data-index', index);

        // 모달 닫기 버튼 이벤트 리스너 등록 (한 번만)
        const closeButtons = deleteConfirmModal.querySelectorAll('.close');
        closeButtons.forEach(function(closeButton) {
            closeButton.addEventListener('click', function() {
                deleteConfirmModal.style.display = 'none';
            });
        });

        // 모달 외부 클릭 이벤트 리스너 등록 (한 번만)
        window.addEventListener('click', function(event) {
            if (event.target === deleteConfirmModal) {
                deleteConfirmModal.style.display = 'none';
            }
        }, { once: true });
    }

    // 카테고리 항목 삭제
    // function deleteCategoryItem() {
    //     const deleteConfirmModal = document.getElementById('deleteConfirmModal');
    //     const category = deleteConfirmModal.getAttribute('data-category');
    //     const index = deleteConfirmModal.getAttribute('data-index');
    //
    //     const categories = JSON.parse(localStorage.getItem('categories')) || {};
    //     if (categories[category] && categories[category][index]) {
    //         categories[category].splice(index, 1);
    //         localStorage.setItem('categories', JSON.stringify(categories));
    //         showDeleteSuccessModal();
    //         reloadCategories();
    //     }
    //
    //     deleteConfirmModal.style.display = 'none';
    // }

    // 삭제 완료 모달 열기
    // function showDeleteSuccessModal() {
    //     const deleteSuccessModal = document.getElementById('deleteSuccessModal');
    //     deleteSuccessModal.style.display = 'block';
    //
    //     const completeDeleteButton = document.getElementById('completeDelete');
    //     completeDeleteButton.onclick = function() {
    //         deleteSuccessModal.style.display = 'none';
    //     };
    //
    //     const closeButtons = deleteSuccessModal.querySelectorAll('.close');
    //     closeButtons.forEach(function(closeButton) {
    //         closeButton.addEventListener('click', function() {
    //             deleteSuccessModal.style.display = 'none';
    //         });
    //     });
    //
    //     window.addEventListener('click', function(event) {
    //         if (event.target === deleteSuccessModal) {
    //             deleteSuccessModal.style.display = 'none';
    //         }
    //     }, { once: true });
    // }

    // 카테고리 목록 다시 불러오기
    // function reloadCategories() {
    //     const categoryLists = document.querySelectorAll('.categorybody ul');
    //     categoryLists.forEach(function(list) {
    //         list.innerHTML = '';
    //     });
    //     loadCategories();
    // }

    // 삭제 확인 버튼 이벤트 리스너 등록 (한 번만)
    // const confirmDeleteButton = document.getElementById('confirmDelete');
    // confirmDeleteButton.addEventListener('click', function() {
    //     deleteCategoryItem();
    // });

    // 초기화
    // loadCategories();
});
