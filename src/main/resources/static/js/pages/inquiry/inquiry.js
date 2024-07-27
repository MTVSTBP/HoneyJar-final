page = [[${page}]] > totalPages ? totalPages : [[${page}]];
totalPages = [[${totalPages}]];
page = page < 1 ? 1 : page;

function changePage(page) {
    const params = new URLSearchParams(window.location.search);
    params.set('page', page);
    window.location.search = params.toString();
    if (page < 1) {
        page = 1;
        firstPageButton.disabled = true;
        prevPageButton.disabled = true;
    } else {
        firstPageButton.disabled = false;
        prevPageButton.disabled = false;
    }

    if (page > totalPages) {
        page = totalPages;
        nextPageButton.disabled = true;
        lastPageButton.disabled = true;
    } else {
        nextPageButton.disabled = false;
        lastPageButton.disabled = false;

    }
    location.href = '/settings/inquiry?page=' + page;
}
