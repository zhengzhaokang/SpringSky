package com.wc.single.sky.disclosures.service;

import com.github.pagehelper.PageInfo;
import com.wc.single.sky.disclosures.vo.DisclosureConfigVO;

import java.util.List;

public interface DisclosureConfigService {
    int saveDisclosureConfig(DisclosureConfigVO disclosureConfigVO);

    DisclosureConfigVO getDisclosureConfig(String id);

    int updateDisclosureConfig(DisclosureConfigVO disclosureConfigVO);

    int deleteDisclosureConfig(String ids);

    List<DisclosureConfigVO> listDisclosureConfig(DisclosureConfigVO disclosureConfigVO);

    PageInfo<DisclosureConfigVO> pageDisclosureConfig(DisclosureConfigVO disclosureConfigVO);
}
