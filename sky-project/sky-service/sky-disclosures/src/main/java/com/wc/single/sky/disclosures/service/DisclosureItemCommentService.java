package com.wc.single.sky.disclosures.service;

import com.github.pagehelper.PageInfo;
import com.wc.single.sky.disclosures.vo.DisclosureItemCommentVO;

import java.util.List;

public interface DisclosureItemCommentService {

    int saveDisclosureItemComment(DisclosureItemCommentVO disclosureItemCommentVO);

    int deleteDisclosureItemComment(String id, String updateBy);

    PageInfo<DisclosureItemCommentVO> getDisclosureItemCommentList(DisclosureItemCommentVO disclosureItemCommentVO);

    List<DisclosureItemCommentVO> getDisclosureItemCommentVOList(DisclosureItemCommentVO disclosureItemCommentVO);
}
