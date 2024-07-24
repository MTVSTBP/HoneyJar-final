package com.tbp.honeyjar.bookmark.service;


import com.tbp.honeyjar.bookmark.dao.BookmarkMapper;
import com.tbp.honeyjar.bookmark.dto.BookmarkDTO;
import com.tbp.honeyjar.post.dao.PostMapper;
import com.tbp.honeyjar.post.dto.PostListDTO;
import com.tbp.honeyjar.post.dto.PostResponseDTO;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookmarkService {

    private final BookmarkMapper bookmarkMapper;

    public BookmarkService(BookmarkMapper bookmarkMapper) {
        this.bookmarkMapper = bookmarkMapper;
    }

    @Transactional
    public boolean toggleBookmark(Long postId, Long userId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("postId", postId);
        params.put("userId", userId);

        try {
            BookmarkDTO existingBookmark = bookmarkMapper.findBookmarkByPostIdAndUserId(params);
            if (existingBookmark == null) {
                bookmarkMapper.insertBookmark(params);
                return true; // 북마크가 추가된 상태
            } else {
                bookmarkMapper.deleteBookmark(params);
                return false; // 북마크가 삭제된 상태
            }
        } catch (DuplicateKeyException e) {
            // 이미 존재하는 경우 삭제를 시도
            bookmarkMapper.deleteBookmark(params);
            return false; // 북마크가 삭제된 상태
        }
    }

    public List<PostListDTO> getBookmarkedPosts(Long userId, Long category) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("category", category);

        List<PostListDTO> posts = bookmarkMapper.findBookmarkedPostsByUserId(params);

        return posts;
    }
}