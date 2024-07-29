package com.tbp.honeyjar.place.dto;

public class PlaceDTO {
    private Long placeId;
    private String name;
    private float ratingAvg;
    private Double xCoordinate;
    private Double yCoordinate;
    private String roadAddressName;
    private String createdAt;
    private String updatedAt;

    public PlaceDTO() {}

    public PlaceDTO(Long placeId, String name, float ratingAvg, Double xCoordinate, Double yCoordinate, String roadAddressName, String createdAt, String updatedAt) {
        this.placeId = placeId;
        this.name = name;
        this.ratingAvg = ratingAvg;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.roadAddressName = roadAddressName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(float ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public Double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(Double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(Double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public String getRoadAddressName() {
        return roadAddressName;
    }

    public void setRoadAddressName(String roadAddressName) {
        this.roadAddressName = roadAddressName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "PlaceDTO{" +
                "placeId=" + placeId +
                ", name='" + name + '\'' +
                ", ratingAvg=" + ratingAvg +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", roadAddressName='" + roadAddressName + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}