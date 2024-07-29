package com.tbp.honeyjar.login.DTO;


public class UserDTO {
    private Long userId;
    private String name; // 닉네임
    private boolean isFollowed;

    public UserDTO() {
    }

    public UserDTO(Long userId, String name, boolean isFollowed) {
        this.userId = userId;
        this.name = name;
        this.isFollowed = isFollowed;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", isFollowed=" + isFollowed +
                '}';
    }
}
