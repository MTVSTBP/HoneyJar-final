let map;
let clusterer;
let markerImage;

document.addEventListener('DOMContentLoaded', function () {
    const mapContainer = document.getElementById('map');
    const mapOption = {
        center: new kakao.maps.LatLng(36.2683, 127.6358),
        level: 13
    };

    map = new kakao.maps.Map(mapContainer, mapOption);
    clusterer = new kakao.maps.MarkerClusterer({
        map: map,
        averageCenter: true,
        minLevel: 10
    });

    markerImage = new kakao.maps.MarkerImage(
        '/assets/img/marker.png',
        new kakao.maps.Size(52, 64),
        {offset: new kakao.maps.Point(26, 64)}
    );

    loadInitialMarkers();
});

function loadInitialMarkers() {
    fetch('/post/coordinates')
        .then(response => response.json())
        .then(data => {
            const markers = data.map(coord => {
                const marker = new kakao.maps.Marker({
                    position: new kakao.maps.LatLng(coord.lat, coord.lng),
                    image: markerImage
                });

                kakao.maps.event.addListener(marker, 'click', function () {
                    fetch(`/post/info?lat=${coord.lat}&lng=${coord.lng}`)
                        .then(response => response.json())
                        .then(placeInfo => {
                            if (placeInfo.postCount > 1) {
                                window.location.href = `/post/by-place?placeName=${encodeURIComponent(placeInfo.placeName)}`;
                            } else if (placeInfo.postCount === 1) {
                                window.location.href = `/post/detail?postId=${placeInfo.postId}`;
                            }
                        });
                });

                return marker;
            });

            clusterer.addMarkers(markers);

            kakao.maps.event.addListener(clusterer, 'clusterclick', function (cluster) {
                const level = map.getLevel() - 1;
                map.setLevel(level, {anchor: cluster.getCenter()});
            });

            kakao.maps.event.addListener(map, 'zoom_changed', function () {
                if (map.getLevel() <= 3) {
                    clusterer.clear();
                    data.forEach(coord => {
                        const marker = new kakao.maps.Marker({
                            position: new kakao.maps.LatLng(coord.lat, coord.lng),
                            image: markerImage
                        });
                        kakao.maps.event.addListener(marker, 'click', function () {
                            fetch(`/post/info?lat=${coord.lat}&lng=${coord.lng}`)
                                .then(response => response.json())
                                .then(placeInfo => {
                                    if (placeInfo.postCount > 1) {
                                        window.location.href = `/post/by-place?placeName=${encodeURIComponent(placeInfo.placeName)}`;
                                    } else if (placeInfo.postCount === 1) {
                                        window.location.href = `/post/detail?postId=${placeInfo.postId}`;
                                    }
                                });
                        });
                        marker.setMap(map);
                    });
                } else {
                    clusterer.clear();
                    clusterer.addMarkers(markers);
                }
            });
        });
}

window.searchAndDisplayMarkers = function (keyword) {
    fetch(`/post/search?keyword=${encodeURIComponent(keyword)}`)
        .then(response => response.json())
        .then(posts => {
            map.setLevel(3);
            clusterer.clear();

            posts.forEach(post => {
                const marker = new kakao.maps.Marker({
                    position: new kakao.maps.LatLng(post.yCoordinate, post.xCoordinate),
                    image: markerImage
                });

                kakao.maps.event.addListener(marker, 'click', function () {
                    if (post.postCount > 1) {
                        window.location.href = `/post/by-place?placeName=${encodeURIComponent(post.placeName)}`;
                    } else {
                        window.location.href = `/post/detail?postId=${post.postId}`;
                    }
                });

                marker.setMap(map);
            });

            if (posts.length > 0) {
                map.setCenter(new kakao.maps.LatLng(posts[0].yCoordinate, posts[0].xCoordinate));
            }
        });
};