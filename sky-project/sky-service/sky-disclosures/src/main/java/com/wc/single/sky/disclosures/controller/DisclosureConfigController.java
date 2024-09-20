package com.wc.single.sky.disclosures.controller;

import com.github.pagehelper.PageInfo;
import com.wc.single.sky.common.core.controller.BaseController;
import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.common.utils.StringUtils;
import com.wc.single.sky.disclosures.service.DisclosureConfigService;
import com.wc.single.sky.disclosures.vo.DisclosureConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("disclosuresConfig")
@RestController
@RequestMapping("disclosures/config")
@Slf4j
public class DisclosureConfigController extends BaseController {

    @Autowired
    private DisclosureConfigService disclosureConfigService;

    @ApiOperation(value = "Query Disclosures Config data List")
    @PostMapping("data/list")
    public ResultDTO<Object> dataList(@RequestBody DisclosureConfigVO disclosureConfigVO){
        if (disclosureConfigVO == null) {
            return ResultDTO.fail("param is null");
        }
        List<DisclosureConfigVO> disclosureConfigVOS = disclosureConfigService.listDisclosureConfig(disclosureConfigVO);
        return ResultDTO.success(disclosureConfigVOS);
    }

    @ApiOperation(value = "Query Disclosures Config List")
    @PostMapping("list")
    public ResultDTO<Object> list(@RequestBody DisclosureConfigVO disclosureConfigVO){
        startPage();
        PageInfo<DisclosureConfigVO> disclosureConfigVOPageInfo = disclosureConfigService.pageDisclosureConfig(disclosureConfigVO);
        if (disclosureConfigVOPageInfo == null) {
            logger.info("### DisclosuresBasicController list disclosureConfigVOPageInfo is null");
            return ResultDTO.success(getDataDefaultTable());
        }
        return ResultDTO.success(getDataTable(disclosureConfigVOPageInfo));
    }

    @ApiOperation(value = "Query Disclosures Config Detail")
    @GetMapping("info/{configId}")
    public ResultDTO<Object> info(@PathVariable("configId") String configId){
        if (StringUtils.isBlank(configId)) {
            return ResultDTO.fail("param is null");
        }
        DisclosureConfigVO disclosureConfigVO = disclosureConfigService.getDisclosureConfig(configId);
        return ResultDTO.success(disclosureConfigVO);
    }

    @ApiOperation(value = "Add Disclosures Config")
    @PostMapping("add")
    public ResultDTO<Object> add(@RequestBody DisclosureConfigVO disclosureConfigVO){
        if (disclosureConfigVO == null) {
            return ResultDTO.fail("param is null");
        }
        int count = disclosureConfigService.saveDisclosureConfig(disclosureConfigVO);
        return ResultDTO.success(count);
    }

    @ApiOperation(value = "Update Disclosures Config")
    @PostMapping("update")
    public ResultDTO<Object> update(@RequestBody DisclosureConfigVO disclosureConfigVO){
        if (disclosureConfigVO == null || StringUtils.isBlank(disclosureConfigVO.getConfigId())) {
            return ResultDTO.fail("param is null");
        }
        int count = disclosureConfigService.updateDisclosureConfig(disclosureConfigVO);
        return ResultDTO.success(count);
    }

    @ApiOperation(value = "Delete Disclosures Config")
    @PostMapping("delete")
    public ResultDTO<Object> delete(@RequestParam("configIds") String configIds){
        if (StringUtils.isBlank(configIds)) {
            return ResultDTO.fail("param is null");
        }
        int count = disclosureConfigService.deleteDisclosureConfig(configIds);
        return ResultDTO.success(count);
    }
}
