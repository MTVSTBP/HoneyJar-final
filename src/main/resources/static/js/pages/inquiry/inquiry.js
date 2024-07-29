// document.addEventListener('DOMContentLoaded', () => {
//     const inquiryLinks = document.querySelectorAll('.inquiry_link');
//     inquiryLinks.forEach(link => {
//         link.addEventListener('click', function(event) {
//             event.preventDefault();
//             const inquiryId = this.getAttribute('href').split('=')[1];
//             // 여기에 상세보기 페이지로 이동하는
//             console.log('Inquiry ID:', inquiryId);
//         });
//     });
// });


// document.addEventListener('DOMContentLoaded', () => {
//     const inquiriesData = JSON.parse(inquiries);
//     renderInquiries(inquiriesData);
// });
//
// function createElement(tag, className, textContent) {
//     const element = document.createElement(tag);
//     if (className) element.className = className;
//     if (textContent) element.textContent = textContent;
//     return element;
// }
//
// function createInquiryItem(inquiry) {
//     const ul = document.createElement('ul');
//     ul.className = 'list';
//
//     ul.innerHTML = `
//         <li>
//             <a href="/settings/inquiry/detail?id=${inquiry.id}" class="inquiry_link">
//                 <div class="item">
//                     <div class="title">${inquiry.title}</div>
//                     <div class="context_text">
//                         <p class="context">${inquiry.context}</p>
//                         <span>${inquiry.date}</span>
//                     </div>
//                 </div>
//             </a>
//         </li>
//     `;
//
//     return ul;
// }
//
// function renderInquiries(inquiries) {
//     const inquiryContainer = document.querySelector('.inquiry');
//     inquiries.forEach(inquiry => {
//         const inquiryItem = createInquiryItem(inquiry);
//         inquiryContainer.appendChild(inquiryItem);
//     });
// }
