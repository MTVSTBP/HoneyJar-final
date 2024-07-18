var markers = [];
var overlays = []; // 생성된 오버레이를 저장할 배열
var selectedPlace = null; // 선택된 장소를 저장할 변수

var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(37.413827745, 127.099316624097),
        level: 3
    };

var map = new kakao.maps.Map(mapContainer, mapOption);
var ps = new kakao.maps.services.Places();
var infowindow = new kakao.maps.InfoWindow({zIndex: 1});

searchPlaces();

function searchPlaces() {
    var keyword = document.getElementById('keyword').value;
    ps.keywordSearch(keyword, placesSearchCB);
}

function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        displayPlaces(data);
        displayPagination(pagination);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다.');
        return;
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다.');
        return;
    }
}

function displayPlaces(places) {
    var listEl = document.getElementById('placesList'),
        menuEl = document.getElementById('menu_wrap'),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds();

    removeAllChildNods(listEl);
    removeMarker();
    removeOverlays(); // 기존의 오버레이를 제거

    for (var i = 0; i < places.length; i++) {
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(placePosition, i),
            itemEl = getListItem(i, places[i]);

        bounds.extend(placePosition);

        (function(marker, title, place, placePosition) {
            itemEl.onclick = function() {
                map.panTo(placePosition);
                map.setLevel(2); // 확대 레벨 설정

                // 기존의 오버레이를 모두 제거
                removeOverlays();

                // 새로운 오버레이 생성
                var content = '<div class="place_wrap"><div class="customOverlay">' + title + '</div></div>';
                var overlay = new kakao.maps.CustomOverlay({
                    content: content,
                    position: placePosition,
                    yAnchor: 1
                });

                overlay.setMap(map); // 지도에 오버레이 추가
                overlays.push(overlay); // 새로운 오버레이를 배열에 저장

                selectedPlace = place; // 선택된 장소 저장
            };
        })(marker, places[i].place_name, places[i], placePosition);

        fragment.appendChild(itemEl);
        if (i < places.length - 1) {
            var hr = document.createElement('hr');
            fragment.appendChild(hr);
        }
    }

    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;
    map.setBounds(bounds);
}

function getListItem(index, places) {
    var el = document.createElement('li'),
        itemStr = '<span class="markerbg marker_' + (index + 1) + '"></span>' +
            '<p>' + (index + 1) + '</p>' +
            '<div class="info">' +
            '   <p>' + places.place_name + '</p>';

    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
            '    <span class="jibun gray">' +
            '        <p>지번</p>' +
            places.address_name +
            '    </span>';
    } else {
        itemStr += '    <span>' + places.address_name + '</span>';
    }

    itemStr += '</div>';
    el.innerHTML = itemStr;
    el.className = 'item';
    return el;
}

function addMarker(position, idx, title) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png',
        imageSize = new kakao.maps.Size(36, 37),
        imgOptions = {
            spriteSize: new kakao.maps.Size(36, 691),
            spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10),
            offset: new kakao.maps.Point(13, 37)
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
            position: position,
            image: markerImage
        });

    marker.setMap(map);
    markers.push(marker);
    return marker;
}

function removeMarker() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

function removeOverlays() {
    for (var i = 0; i < overlays.length; i++) {
        overlays[i].setMap(null);
    }
    overlays = [];
}

function displayPagination(pagination) {
    var paginationEl = document.getElementById('pagination'),
        fragment = document.createDocumentFragment(),
        i;

    while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild(paginationEl.lastChild);
    }

    for (i = 1; i <= pagination.last; i++) {
        var el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;

        if (i === pagination.current) {
            el.className = 'on';
        } else {
            el.onclick = (function(i) {
                return function() {
                    pagination.gotoPage(i);
                }
            })(i);
        }

        fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
}

function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
    }
}

// 선택된 주소를 로컬스토리지에 저장하고 페이지를 전환하는 함수
function storeAndRedirect() {
    if (selectedPlace) {
        const placeData = {
            address_name: selectedPlace.address_name,
            road_address_name: selectedPlace.road_address_name || selectedPlace.address_name,
            place_name: selectedPlace.place_name,
            y: selectedPlace.y,
            x: selectedPlace.x
        };
        localStorage.setItem("selectedPlace", JSON.stringify(placeData));
        window.location.href = "/post/write";
    } else {
        alert("장소를 선택해 주세요.");
    }
}

// 완료 버튼 클릭 시
document.getElementById('completeBtn').addEventListener('click', storeAndRedirect);