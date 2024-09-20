package com.wc.single.sky.disclosures.service;

import com.github.pagehelper.PageInfo;
import com.wc.single.sky.disclosures.vo.DisclosureItemLogVO;

import java.util.List;

public interface DisclosureItemLogService {
    int insertDisclosureItemLog(DisclosureItemLogVO disclosureItemLogVO);

    List<DisclosureItemLogVO> selectDisclosureItemLog(DisclosureItemLogVO disclosureItemLogVO);

    PageInfo<DisclosureItemLogVO> selectDisclosureItemLogPage(DisclosureItemLogVO disclosureItemLogVO);
}
