package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dao.AdminInquiryMapper;
import com.tbp.honeyjar.inquiry.dto.InquiryDto;

import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminInquiryService {
    AdminInquiryMapper  adminInquiryMapper;
    UserMapper          userMapper;

    public AdminInquiryService(AdminInquiryMapper adminInquiryMapper, UserMapper userMapper) {
        this.adminInquiryMapper = adminInquiryMapper;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public User findByKakaoId(String kakaoId) {
        return userMapper.findByKakaoId(kakaoId);
    }
    
    /* for Admin, find all inquiries */

    @Transactional(readOnly = true)
    public Page<InquiryDto> getInquiryList(Pageable pageable) {
        int totalPageCount = adminInquiryMapper.getAdminInquiryCount();

        Map<String, Object> params = new HashMap<>();

        params.put("offset", pageable.getOffset());
        params.put("pageSize", pageable.getPageSize());
        System.out.println("params : " + params.toString());
        List<InquiryDto> inquiries = adminInquiryMapper.getInquiryList(params);
        System.out.println("after call inquiryList");
        return new PageImpl<>(inquiries, pageable, totalPageCount);
    }

    /* AdminInquiryDetail */
    @Transactional(readOnly = true)
    public InquiryDto getInquiryById(Long inquiryId) {
        return adminInquiryMapper.getInquiryById(inquiryId);
    }
    /* Admin can delete Inquiry */

    @Transactional
    public boolean deleteInquiry(Long inquiryId) {
        try {
            adminInquiryMapper.delete(inquiryId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
