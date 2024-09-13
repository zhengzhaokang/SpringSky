package com.wc.single.sky.disclosures.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wc.single.sky.common.constant.Constants;
import com.wc.single.sky.common.utils.poi.NewExcelUtil;
import com.wc.single.sky.disclosures.common.DisclosureConstant;
import com.wc.single.sky.disclosures.common.enums.DisclosureBasicEnum;
import com.wc.single.sky.disclosures.service.DisclosuresBasicService;
import com.wc.single.sky.disclosures.service.DisclosuresItemService;
import com.wc.single.sky.disclosures.vo.DisclosuresBasicVO;
import com.wc.single.sky.disclosures.vo.DisclosuresItemVO;
import com.wc.single.sky.common.core.controller.BaseController;
import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.common.enums.BusinessType;
import com.wc.single.sky.common.log.annotation.OperLog;
import com.wc.single.sky.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Api("disclosuresItem")
@RestController
@RequestMapping("disclosures/item")
@Slf4j
public class DisclosuresItemController extends BaseController {

    @Autowired
    private DisclosuresItemService disclosuresItemService;

    @Autowired
    private DisclosuresBasicService disclosuresBasicService;

    @ApiOperation(value = "Query Disclosure Item List")
    @PostMapping("list")
    public ResultDTO<Object> list(@RequestBody DisclosuresItemVO disclosuresItemVO) {
        if (disclosuresItemVO == null || StringUtils.isBlank(disclosuresItemVO.getDisclosuresId())) {
            logger.info("### DisclosuresItemController list param null");
            return ResultDTO.success(getDataDefaultTable());
        }
        startPage();
        PageInfo<DisclosuresItemVO> disclosuresItemVOPageInfo = disclosuresItemService.selectDisclosuresItemList(disclosuresItemVO);
        if (disclosuresItemVOPageInfo == null) {
            logger.info("### DisclosuresItemController list disclosuresItemVOPageInfo is null");
            return ResultDTO.success(getDataDefaultTable());
        }
        return ResultDTO.success(getDataTable(disclosuresItemVOPageInfo));
    }

    @ApiOperation(value = "Query Disclosure Item Export")
    @PostMapping("export")
    public void export(@RequestBody DisclosuresItemVO disclosuresItemVO) {
        List<DisclosuresItemVO> list = new ArrayList<>();
        if (disclosuresItemVO != null && StringUtils.isNotBlank(disclosuresItemVO.getDisclosuresId())) {
            PageHelper.startPage(Constants.DEFAULT_PAGE_NUM, Integer.MAX_VALUE);
            PageInfo<DisclosuresItemVO> disclosuresItemVOPageInfo = disclosuresItemService.selectDisclosuresItemList(disclosuresItemVO);
            list = disclosuresItemVOPageInfo.getList();
        }
        NewExcelUtil<DisclosuresItemVO> util = new NewExcelUtil<>(DisclosuresItemVO.class);
        util.exportExcel(getResponse(),list, DisclosureConstant.DISCLOSURES_ITEM);
    }

    @ApiOperation(value = "Query Disclosures Item Detail")
    @GetMapping("info/{itemId}")
    public ResultDTO<Object> info(@PathVariable("itemId") String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return ResultDTO.fail("disclosuresItem param is null");
        }
        DisclosuresItemVO disclosuresItemVO = disclosuresItemService.selectDisclosuresItemById(Long.parseLong(itemId));
        return ResultDTO.success(disclosuresItemVO);
    }

    @ApiOperation(value = "Add Disclosures Item")
    @PostMapping("add")
    public ResultDTO<Object> add(@RequestBody DisclosuresItemVO disclosuresItemVO) {
        if (disclosuresItemVO == null || StringUtils.isBlank(disclosuresItemVO.getDisclosuresId())) {
            return ResultDTO.fail("disclosuresItem param is null");
        }
        int count = disclosuresItemService.insertDisclosuresItem(disclosuresItemVO);

        // 修改basic状态
        if (count > 0) {
            DisclosuresBasicVO param = new DisclosuresBasicVO();
            param.setBasicId(disclosuresItemVO.getDisclosuresId());
            param.setUpdateBy(disclosuresItemVO.getUpdateBy());
            param.setStatus(DisclosureBasicEnum.DATA_COLLECTED.code);
            disclosuresBasicService.updateDisclosuresBasic(param);
        }
        return ResultDTO.success(count);
    }

    @ApiOperation(value = "Update Disclosures Item")
    @PostMapping("update")
    public ResultDTO<Object> update(@RequestBody DisclosuresItemVO disclosuresItemVO) {
        if (disclosuresItemVO == null || StringUtils.isBlank(disclosuresItemVO.getItemId())) {
            return ResultDTO.fail("disclosuresItem param is null");
        }
        int count = disclosuresItemService.updateDisclosuresItem(disclosuresItemVO);
        return ResultDTO.success(count);
    }

    @ApiOperation(value = "Update Disclosures Item Status")
    @PostMapping("status/update")
    public ResultDTO<Object> updateStatus(@RequestBody DisclosuresItemVO disclosuresItemVO) {
        if (disclosuresItemVO == null || StringUtils.isBlank(disclosuresItemVO.getItemId())) {
            return ResultDTO.fail("disclosuresItem param is null");
        }
        DisclosuresItemVO param = new DisclosuresItemVO();
        param.setItemId(disclosuresItemVO.getItemId());
        param.setStatus(disclosuresItemVO.getStatus());
        int count = disclosuresItemService.updateDisclosuresItem(disclosuresItemVO);
        return ResultDTO.success(count);
    }

    @ApiOperation(value = "Del Disclosures Item")
    @PostMapping("remove")
    @OperLog(title = "Del Disclosures Item", businessType = BusinessType.DELETE)
    public ResultDTO<Object> remove(@RequestParam("ids")  String ids, @RequestParam("update") String updateBy)
    {
        if (StringUtils.isBlank(ids)) {
            return ResultDTO.fail("disclosuresItem param is null");
        }
        List<String> idList = Arrays.asList(ids.split(","));
        int count = disclosuresItemService.deleteDisclosuresItemByIds(idList, updateBy);
        return ResultDTO.success(count);
    }

}
