package com.tbp.honeyjar.bookmark.dao;

import com.tbp.honeyjar.bookmark.dto.BookmarkDTO;
import com.tbp.honeyjar.post.dto.PostListDTO;
import com.tbp.honeyjar.post.dto.PostRequestDTO;
import com.tbp.honeyjar.post.dto.PostResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper
public interface BookmarkMapper {
    void insertBookmark(BookmarkDTO bookmark);
    void deleteBookmark(HashMap<String, Object> params);
    List<BookmarkDTO> findBookmarksByUserId(Long userId);
    BookmarkDTO findBookmark(HashMap<String, Object> params);
    List<BookmarkDTO> findTopBookmarksByUserId(HashMap<String, Object> params);
}
