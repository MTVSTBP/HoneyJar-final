document.addEventListener('DOMContentLoaded', function() {
    var map;
    var markers = [];
    var clusterer;

    function initMap() {
        var mapContainer = document.getElementById('map');
        var mapOption = {
            center: new kakao.maps.LatLng(36.2683, 127.6358),
            level: 14
        };

        map = new kakao.maps.Map(mapContainer, mapOption);

        // 지도 크기 재설정
        window.setTimeout(function() {
            map.relayout();
        }, 0);

        // kakao.maps.load(function() {
        //     clusterer = new kakao.maps.MarkerClusterer({
        //         map: map,
        //         averageCenter: true,
        //         minLevel: 10
        //     });
        //     loadAllMarkers();
        // });
    }

    // function loadAllMarkers() {
    //     fetch("/post/search?keyword=")
    //         .then(response => response.json())
    //         .then(data => {
    //             console.log("Fetched data:", data);
    //             createMarkers(data);
    //         })
    //         .catch(error => console.error('Error:', error));
    // }
    //
    // function createMarkers(posts) {
    //     console.log("Creating markers for posts:", posts);
    //     if (clusterer) clusterer.clear();
    //     markers = [];
    //
    //     posts.forEach(function(post) {
    //         console.log("Creating marker for post:", post);
    //         var markerPosition = new kakao.maps.LatLng(post.yCoordinate, post.xCoordinate);
    //         var marker = new kakao.maps.Marker({
    //             position: markerPosition,
    //             map: map  // 여기에 map을 추가
    //         });
    //
    //         markers.push(marker);
    //
    //         kakao.maps.event.addListener(marker, 'click', function() {
    //             window.location.href = '/post/detail?postId=' + post.postId;
    //         });
    //     });
    //
    //     console.log("Total markers created:", markers.length);
    //     if (clusterer) clusterer.addMarkers(markers);
    //
    //     if (markers.length > 0) {
    //         var bounds = new kakao.maps.LatLngBounds();
    //         markers.forEach(function(marker) {
    //             bounds.extend(marker.getPosition());
    //         });
    //         map.setBounds(bounds);
    //     }
    // }
    //
    // function searchAndDisplayMarkers(keyword) {
    //     fetch("/post/search?keyword=" + encodeURIComponent(keyword))
    //         .then(response => response.json())
    //         .then(data => {
    //             console.log("Search results:", data);
    //             createMarkers(data);
    //         })
    //         .catch(error => console.error('Error:', error));
    // }
    //
    // window.searchAndDisplayMarkers = searchAndDisplayMarkers;

    // 약간의 지연 후 initMap 실행
    setTimeout(initMap, 100);
});