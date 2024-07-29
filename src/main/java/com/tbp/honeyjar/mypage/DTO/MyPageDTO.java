package com.tbp.honeyjar.mypage.DTO;
import lombok.*;
import java.util.List;

@Data
@Setter
@NoArgsConstructor
public class MyPageDTO {
    private Long userId;
    private String userName;
    private String userPr;
    private String profileImage;
    private Integer numberOfPosts;
    private Integer numberOfFollowers;
    private Integer numberOfFollowees;
    private List<PostDTO> BookmarkedPosts;

    @Builder
    public MyPageDTO(Long userId, String userName, String userPr, String profileImage, Integer numberOfPosts, Integer numberOfFollowers, Integer numberOfFollowees, List<PostDTO> bookmarkedPosts) {
        this.userId = userId;
        this.userName = userName;
        this.userPr = userPr;
        this.profileImage = profileImage;
        this.numberOfPosts = numberOfPosts;
        this.numberOfFollowers = numberOfFollowers;
        this.numberOfFollowees = numberOfFollowees;
        this.BookmarkedPosts = bookmarkedPosts;
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
    public String getProfileImage() {
        return profileImage;
    }

    public Integer getNumberOfPosts() {
        return numberOfPosts;
    }

    public Integer getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public Integer getNumberOfFollowees() {
        return numberOfFollowees;
    }

    public List<PostDTO> getBookmarkedPosts() {
        return BookmarkedPosts;
    }

    @Override
    public String toString() {
        return "MyPageDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPr='" + userPr + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", numberOfPosts=" + numberOfPosts +
                ", numberOfFollowers=" + numberOfFollowers +
                ", numberOfFollowees=" + numberOfFollowees +
                ", BookmarkedPosts=" + BookmarkedPosts +
                '}';
    }
}