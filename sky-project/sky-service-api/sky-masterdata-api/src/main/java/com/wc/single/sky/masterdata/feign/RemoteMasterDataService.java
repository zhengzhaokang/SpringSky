package com.wc.single.sky.masterdata.feign;

import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.common.core.page.RemoteResponse;
import com.wc.single.sky.masterdata.domain.RemoteBizBaseExchangeRate;
import com.wc.single.sky.masterdata.domain.dto.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 用户 Feign服务层
 *
 * @Author lovefamily
 * @date 2019-05-20
 */

//因为LMP部署环境与SDMS部署环境不一致，所以只能配置域名
//@FeignClient(name = "${otfp.service.masterdata.uri}", fallbackFactory = RemoteMasterDataFallbackFactory.class)
public interface RemoteMasterDataService {

    @GetMapping("/exchangeRate/exchangeRate")
    RemoteResponse<RemoteBizBaseExchangeRate> exchangeRate(@RequestParam("transactionCurrency") String transactionCurrency, @RequestParam("targetCurrency") String targetCurrency);


    @GetMapping("/bizBaseOrgOffice/getOrgAndOffice")
    ResultDTO<BizBaseOrgOfficeDTO> getOrgAndOffice(@RequestParam("businessGroup") String businessGroup, @RequestParam("geoCode") String geoCode
            , @RequestParam("regionMarketCode") String regionMarketCode, @RequestParam("countryCode") String countryCode);

    @GetMapping("/bizBaseOrgOffice/getOrgAndOffice")
    ResultDTO<BizBaseOrgOfficeDTO> getCompanyCode(BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO);

    @GetMapping(value = "/exchangeRate/getExchangeRate")
    ResultDTO<BizBaseExchangeRateDTO> getExchangeRate(@DateTimeFormat(pattern = "MM/dd/yyyy") @RequestParam("rateDate") Date rateDate, @RequestParam("fromCurrency") String fromCurrency, @RequestParam("toCurrency") String toCurrency);

    @GetMapping(value = "/bizBaseCurrency/getByCode/{currencyCode}")
    ResultDTO<BizBaseCurrencyDTO> getByCode(@PathVariable("currencyCode") String currencyCode);

    @PostMapping(value = "/bizBaseCurrency/getListByCodeList")
    ResultDTO<List<BizBaseCurrencyDTO>> getListByCodeList(@RequestBody List<String> currencyCodeList);

    @PostMapping(value = "/bizBaseCurrency/getList")
    ResultDTO<List<BizBaseCurrencyDTO>> getList(@RequestBody BizBaseCurrencyDTO bizBaseCurrency);

    @GetMapping(value = "/bizBaseRegionMarket/dropDownList")
    RemoteResponse<BizBaseRegionMarketDTO> getListByRegionMarket(@RequestParam("geoCode") String geoCode, @RequestParam("businessGroup") String businessGroup);

    @GetMapping(value = "/bizBaseSalesOrg/dropDownList")
    RemoteResponse<BizBaseSalesOrgDTO> getListBySalesOrg(@RequestParam("geoCode") String geoCode, @RequestParam("businessGroup") String businessGroup,
                                                         @RequestParam("regionMarketCode") String regionMarketCode, @RequestParam("tempField") String tempField);

    @GetMapping(value = "/bizBaseSalesOrg/getOne")
    RemoteResponse<BizBaseSalesOrgDTO> getOneSalesOrg(@RequestParam("geoCode") String geoCode, @RequestParam("businessGroup") String businessGroup,
                                                      @RequestParam("salesOrgCode") String salesOrgCode);

    @GetMapping(value = "/bizBaseSalesOffice/dropDownList")
    RemoteResponse<BizBaseSalesOfficeDTO> getListBySalesOffice(@RequestParam("businessGroup") String businessGroup, @RequestParam("regionMarketCode") String regionMarketCode,
                                                               @RequestParam("salesOrgCode") String salesOrgCode);

    @GetMapping(value = "/bizBaseSegment/dropDownList")
    RemoteResponse<BizBaseSegmentDTO> getListBySegment(@RequestParam("geoCode") String geoCode, @RequestParam("businessGroup") String businessGroup);

    @GetMapping("/bizBaseVendor/getVendorAndBankInfoList")
    ResultDTO<List<BizBaseVendorDTO>> getVendor(@RequestParam("vendorCode") String vendorCode,
                                                @RequestParam("bankAccount") String bankAccount, @RequestParam("customerId") String customerId);

    @GetMapping(value = "/bizBaseVendorBank/listByPartnerIncentiveId")
    RemoteResponse<BizBaseVendorBankDTO> getListByVendor(@RequestParam("vendorCode") String vendorCode, @RequestParam("customerId") String customerId);

    @GetMapping(value = "/bizBaseCustomer/list")
    RemoteResponse<BizBaseCustomerDTO> getListByCustomer( BizBaseCustomerDTO bizBaseCustomerDTO);

    @GetMapping(value = "/gtnCategory/list")
    RemoteResponse<BizBaseGtnCategoryDTO> getGtnCategoryByType(@RequestParam("gtnCategoryL1") String gtnCategoryL1);
//
//    @GetMapping("/bizBaseGtnType/remoteGetGtnType")
//    RemoteResponse<BizBaseGtnTypeDTO> remoteGetGtnType(@RequestParam("gtnType") String gtnType,@RequestParam("tempField") String tempField);

    @GetMapping(value = "/bizBaseCustomer/getCustomerName")
    String getCustomerNameByNumber(@RequestParam("customerNumber") String customerNumber);

    @GetMapping(value = "/bizBaseApc/list")
    RemoteResponse<BizBaseApcDTO> bizBaseApcList( BizBaseApcDTO bizBaseApcDTO);

    @GetMapping("/bizBaseEndCustomer/getEndCustomerName")
    String getEndCustomerName(@RequestParam("endCustomerId") String endCustomerId);

    @GetMapping(value = "/bizBaseDcDivisionMapping/dropDownList")
    RemoteResponse<BizBaseDcDivisionMappingDTO> getListByDistributionChannel(@RequestParam("salesOrgCode") String salesOrgCode, @RequestParam("salesOfficeCode")
    String salesOfficeCode, @RequestParam("dcCode") String dcCode);

    @PostMapping(value = "/bizBaseCustomer/getCustomer")
    BizBaseCustomerDTO getCustomer(@RequestBody BizBaseCustomerDTO bizBaseCustomer);

    @GetMapping(value = "/bizBaseProfitCenter/selectProfitCenterListPrecise")
    ResultDTO<List<BizBaseProfitCenterDTO>> selectProfitCenterList( BizBaseProfitCenterDTO bizBaseProfitCenterDTO);

    /**
     * 根据segment code、level查询BaseSegment信息
     *
     * @param segmentCode
     * @param segmentLevel
     * @return
     */
    @GetMapping(value = "/bizBaseSegment/getSegment")
    ResultDTO<BizBaseSegmentDTO> getSegment(@RequestParam("businessGroup") String businessGroup, @RequestParam("segmentCode") String segmentCode, @RequestParam("segmentLevel") String segmentLevel);

    /**
     * 根据geo、business group、region market code查询信息
     *
     * @param geoCode
     * @param businessGroup
     * @param regionMarketCode
     * @return
     */
    @GetMapping(value = "/bizBaseRegionMarket/getRegionMarket")
    ResultDTO<BizBaseRegionMarketDTO> getRegionMarket(@RequestParam("geoCode") String geoCode, @RequestParam("businessGroup") String businessGroup, @RequestParam("regionMarketCode") String regionMarketCode);

    @GetMapping(value = "/exchangeRate/getExchangeRateList")
    ResultDTO<List<BizBaseExchangeRateDTO>> getExchangeRateList(@RequestBody List<ExchangeRateDTO> exchangeRateDTOList);


    @GetMapping(value = "bizBaseMbgCustomerDrmTomsTofi/getByCustomerNumber")
    ResultDTO<BizBaseMbgCustomerDrmTomsTofiDTO> getByCustomerNumber(@RequestParam("customerNumber") String customerNumber, @RequestParam("businessGroup") String businessGroup);

    /**
     * 通过salesOrgCode和salesOfficeCode获取regionMarketCode
     *
     * @param salesOrgCode
     * @param salesOfficeCode
     * @return
     */
    @GetMapping(value = "bizBaseOrgOffice/getByOrgOffice")
    ResultDTO<BizBaseOrgOfficeDTO> getByOrgOffice(@RequestParam("salesOrgCode") String salesOrgCode, @RequestParam("salesOfficeCode") String salesOfficeCode);

    @GetMapping(value = "/bizBaseLenovoEntity/list")
    RemoteResponse<BizBaseLenovoEntityDTO> bizBaseLenovoEntityList( BizBaseLenovoEntityDTO bizBaseLenovoEntity);

    /**
     * 通过salesOrgCode获取accrualCompanyCode
     *
     * @param salesOrgCode
     * @param businessGroup
     * @return
     */
    @GetMapping("/bizBaseOrgOffice/getOrgOfficeByOrgAndBg")
    ResultDTO<BizBaseOrgOfficeDTO> getOrgOfficeByOrgAndBg(@RequestParam("salesOrgCode") String salesOrgCode, @RequestParam("businessGroup") String businessGroup);

    @GetMapping(value = "bizBaseSelfInvoice/list")
    ResultDTO<List<BizBaseSelfInvoiceDTO>> getSelfInvoice(@RequestParam("sellerCountry") String sellerCountry, @RequestParam("gtnCategoryL1") String gtnCategoryL1
            , @RequestParam("crmId") String crmId, @RequestParam("status") String status);

    @PostMapping(value = "/bizBaseVendor/getVendorAndPerferBank")
    ResultDTO<List<BizBaseVendorAndPerferBankDTO>> getVendorAndPerferBank(@RequestBody List<String> customerIds);

    @GetMapping(value = "/bizBaseParty3rdVendor/remote/list")
    ResultDTO<List<BizBaseParty3rdVendorDTO>> getParty3rdVendor( BizBaseParty3rdVendorDTO bizBaseParty3rdVendorDTO);

}
