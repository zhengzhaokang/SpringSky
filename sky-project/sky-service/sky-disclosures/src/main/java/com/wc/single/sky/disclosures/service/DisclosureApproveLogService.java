package com.wc.single.sky.disclosures.service;

import com.wc.single.sky.disclosures.vo.DisclosureApproveLogVO;

public interface DisclosureApproveLogService {

    int insertDisclosureApproveLog(DisclosureApproveLogVO disclosureApproveLogVO);

    int updateDisclosureApproveLog(DisclosureApproveLogVO disclosureApproveLogVO);

    DisclosureApproveLogVO getDisclosureApproveLog(String approveUuid);

}
