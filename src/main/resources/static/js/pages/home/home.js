var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 4 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 맵 크기 재설정
// function resizeMap() {
//     var mapContainer = document.getElementById('map');
//     mapContainer.style.width = '100%';
//     mapContainer.style.height = window.innerHeight - 60 + 'px'; // footer 높이 고려
//     map.relayout();
// }

// 초기 로딩 및 윈도우 크기 변경 시 맵 크기 조정
// resizeMap();
// window.addEventListener('resize', resizeMap);

var imageSrc = '/assets/img/marker.png', // 마커이미지의 주소입니다
    imageSize = new kakao.maps.Size(52, 64), // 마커이미지의 크기입니다
    imageOption = {offset: new kakao.maps.Point(26, 64)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

// HTML5의 geolocation으로 사용할 수 있는지 확인합니다
if (navigator.geolocation) {

    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
    navigator.geolocation.getCurrentPosition(function (position) {

        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도

        var locPosition = new kakao.maps.LatLng(lat, lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
            position: locPosition,
            image: markerImage // 마커이미지 설정
        });

        // 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(map);

        // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        var content =
            '<div class="customoverlay">' +
            '  <a href="https://map.kakao.com/link/map/현재위치,' + lat + ',' + lon + '" target="_blank"></a>' +
            '</div>';

        // 커스텀 오버레이를 생성합니다
        var customOverlay = new kakao.maps.CustomOverlay({
            map: map,
            position: locPosition,
            content: content
            // yAnchor: 1 (마커 위에 표시)
        });

        // 지도 중심좌표를 접속위치로 변경합니다
        map.setCenter(locPosition);
    });

} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

    var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
        message = 'geolocation을 사용할수 없어요..'

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        map: map,
        position: locPosition,
        image: markerImage // 마커이미지 설정
    });

    var content =
        '<div class="customoverlay">' +
        '  <a href="https://map.kakao.com/link/map/기본위치,33.450701,126.570667" target="_blank"></a>' +
        '</div>';

    // 커스텀 오버레이를 생성합니다
    var customOverlay = new kakao.maps.CustomOverlay({
        map: map,
        position: locPosition,
        content: content
        // yAnchor: 1
    });

    // 지도 중심좌표를 기본 위치로 설정합니다
    map.setCenter(locPosition);
}
