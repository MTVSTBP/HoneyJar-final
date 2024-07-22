package com.tbp.honeyjar.place.dto;

public class PlaceDTO {
    private Long placeId;
    private String name;
    private float ratingAvg;
    private String xCoordinate;
    private String yCoordinate;
    private String createdAt;
    private String updatedAt;

    public PlaceDTO() {}

    public PlaceDTO(Long placeId, String name, float ratingAvg, String xCoordinate, String yCoordinate, String createdAt, String updatedAt) {
        this.placeId = placeId;
        this.name = name;
        this.ratingAvg = ratingAvg;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
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

    public String getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(String xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public String getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(String yCoordinate) {
        this.yCoordinate = yCoordinate;
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
                ", xCoordinate='" + xCoordinate + '\'' +
                ", yCoordinate='" + yCoordinate + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}