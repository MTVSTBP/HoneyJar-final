package com.tbp.honeyjar.mypage.DTO;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class ViewPostsDTO {
    private Long userId;
    private String userName;
    private String userPr;
    private List<PostDTO> posts;
    private List<CategoryDTO> categories;

    @Builder
    public ViewPostsDTO(Long userId, String userName, String userPr, List<PostDTO> posts, List<CategoryDTO> categories) {
        this.userId = userId;
        this.userName = userName;
        this.userPr = userPr;
        this.posts = posts;
        this.categories = categories;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPr() {
        return userPr;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }
    @Override
    public String toString() {
        return "BookmarkDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPr='" + userPr + '\'' +
                ", posts=" + posts +
                ", categories=" + categories +
                '}';
    }
}