function getCookie(cookieName) {
    cookieName = cookieName + '=';
    let cookieData = document.cookie;
    let start = cookieData.indexOf(cookieName);
    let cValue= '';
    if (start !== -1) {
        start += cookieName.length;
        let end = cookieData.indexOf(';', start);
        if(end === -1) end = cookieData.length;
        cValue = cookieData.substring(start,end);
    }
    return decodeURI(cValue);
    // return unescape(cValue);// Use decodeURI() or decodeURIComponent() instead.
}


export function testSendCookie() {
    //
    if (getCookie("access_token") !== "") {
        const response = fetch(`/settings/inquiry/detail/4`, {
            method: 'POST',
            header: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${getCookie("access_token")}`,
            }
        });
    }

}
