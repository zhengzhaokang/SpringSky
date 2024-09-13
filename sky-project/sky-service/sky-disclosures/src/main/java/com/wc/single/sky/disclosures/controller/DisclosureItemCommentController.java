package com.wc.single.sky.disclosures.controller;

import com.github.pagehelper.PageInfo;
import com.wc.single.sky.disclosures.common.DisclosureConstant;
import com.wc.single.sky.disclosures.service.DisclosureItemCommentService;
import com.wc.single.sky.disclosures.vo.DisclosureItemCommentVO;
import com.wc.single.sky.common.core.controller.BaseController;
import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("disclosuresItemComment")
@RestController
@RequestMapping("disclosures/item/comment")
@Slf4j
public class DisclosureItemCommentController extends BaseController {

    @Autowired
    private DisclosureItemCommentService disclosureItemCommentService;

    @ApiOperation(value = "Disclosure Item Comment List")
    @PostMapping("list")
    public ResultDTO<Object> list(@RequestBody DisclosureItemCommentVO disclosureItemCommentVO) {
        startPage();
        PageInfo<DisclosureItemCommentVO> disclosureItemCommentList = disclosureItemCommentService.getDisclosureItemCommentList(disclosureItemCommentVO);
        if (disclosureItemCommentList == null) {
            log.info("### DisclosureItemCommentController list disclosureItemCommentList is null");
            return ResultDTO.success(getDataDefaultTable());
        }
        return ResultDTO.success(getDataTable(disclosureItemCommentList));
    }

    @ApiOperation(value = "Disclosure Item Comment Add")
    @PostMapping("add")
    public ResultDTO<Object> add(@RequestBody DisclosureItemCommentVO disclosureItemCommentVO) {
        if (disclosureItemCommentVO == null || StringUtils.isAnyBlank(disclosureItemCommentVO.getComment(), disclosureItemCommentVO.getBusiness())) {
            return ResultDTO.fail("Disclosures Item Comment param is null");
        }
        int count = disclosureItemCommentService.saveDisclosureItemComment(disclosureItemCommentVO);
        return ResultDTO.success(count);
    }

    @ApiOperation(value = "Disclosure Item Comment Delete")
    @PostMapping("remove")
    public ResultDTO<Object> remove(@RequestParam("id")  String id, @RequestParam("update") String updateBy) {
        if (StringUtils.isBlank(id)) {
            return ResultDTO.fail("Disclosures Item Comment param is null");
        }
        int count = disclosureItemCommentService.deleteDisclosureItemComment(id, updateBy);
        if (count == DisclosureConstant.CAN_NOT_CHANGE) {
            return ResultDTO.fail("Disclosures Item Comment can not change");
        }
        return ResultDTO.success(count);
    }

}
