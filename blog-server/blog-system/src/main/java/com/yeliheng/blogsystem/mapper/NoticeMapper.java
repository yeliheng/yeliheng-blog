package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.Notice;

import java.util.List;

public interface NoticeMapper {

    List<Notice> getNotice();

    //获取最新公告
    Notice getLatestNotice();
}
