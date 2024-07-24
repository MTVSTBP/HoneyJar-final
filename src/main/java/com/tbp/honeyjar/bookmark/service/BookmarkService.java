package com.tbp.honeyjar.bookmark.service;


import com.tbp.honeyjar.bookmark.dao.BookmarkMapper;
import com.tbp.honeyjar.bookmark.dto.BookmarkDTO;
import com.tbp.honeyjar.post.dao.PostMapper;
import com.tbp.honeyjar.post.dto.PostListDTO;
import com.tbp.honeyjar.post.dto.PostResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookmarkService {
    private final BookmarkMapper bookmarkMapper;
    private final PostMapper postMapper;

    public BookmarkService(BookmarkMapper bookmarkMapper, PostMapper postMapper) {
        this.bookmarkMapper = bookmarkMapper;
        this.postMapper = postMapper;
    }

    @Transactional
    public void toggleBookmark(Long postId, Long userId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("postId", postId);
        params.put("userId", userId);

        BookmarkDTO existingBookmark = bookmarkMapper.findBookmark(params);
        if (existingBookmark == null) {
            BookmarkDTO bookmark = new BookmarkDTO();
            bookmark.setPostId(postId);
            bookmark.setUserId(userId);
            bookmarkMapper.insertBookmark(bookmark);
        } else {
            bookmarkMapper.deleteBookmark(params);
        }
    }

    public boolean isBookmarked(Long postId, Long userId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("postId", postId);
        params.put("userId", userId);

        return bookmarkMapper.findBookmark(params) != null;
    }
//
//    public List<PostListDTO> getAllPostWithBookmarksByUserId(Long userId) {
//        return postMapper.findAllPost(userId);
//    }
}