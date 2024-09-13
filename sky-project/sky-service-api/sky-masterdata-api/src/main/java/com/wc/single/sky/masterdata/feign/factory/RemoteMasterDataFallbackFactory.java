package com.wc.single.sky.masterdata.feign.factory;

import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.common.core.page.RemoteResponse;
import com.wc.single.sky.masterdata.domain.RemoteBizBaseExchangeRate;
import com.wc.single.sky.masterdata.feign.RemoteMasterDataService;
import com.wc.single.sky.masterdata.domain.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class RemoteMasterDataFallbackFactory implements RemoteMasterDataService {

    @Override
    public RemoteResponse<RemoteBizBaseExchangeRate> exchangeRate(String transactionCurrency, String targetCurrency) {
        return null;
    }

    @Override
    public ResultDTO<BizBaseOrgOfficeDTO> getOrgAndOffice(String businessGroup, String geoCode, String regionMarketCode, String countryCode) {
        return null;
    }

    @Override
    public ResultDTO<BizBaseOrgOfficeDTO> getCompanyCode(BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO) {
        return null;
    }

    @Override
    public ResultDTO<BizBaseExchangeRateDTO> getExchangeRate(Date rateDate, String fromCurrency, String toCurrency) {
        return null;
    }

    @Override
    public ResultDTO<BizBaseCurrencyDTO> getByCode(String currencyCode) {
        return null;
    }

    @Override
    public ResultDTO<List<BizBaseCurrencyDTO>> getListByCodeList(List<String> currencyCodeList) {
        return null;
    }

    @Override
    public ResultDTO<List<BizBaseCurrencyDTO>> getList(BizBaseCurrencyDTO bizBaseCurrency) {
        return null;
    }

    @Override
    public RemoteResponse<BizBaseRegionMarketDTO> getListByRegionMarket(String geoCode, String businessGroup) {
        return null;
    }

    @Override
    public RemoteResponse<BizBaseSalesOrgDTO> getListBySalesOrg(String geoCode, String businessGroup, String regionMarketCode, String tempField) {
        return null;
    }

    @Override
    public RemoteResponse<BizBaseSalesOrgDTO> getOneSalesOrg(String geoCode, String businessGroup, String salesOrgCode) {
        return null;
    }

    @Override
    public RemoteResponse<BizBaseSalesOfficeDTO> getListBySalesOffice(String businessGroup, String regionMarketCode, String salesOrgCode) {
        return null;
    }

    @Override
    public RemoteResponse<BizBaseSegmentDTO> getListBySegment(String geoCode, String businessGroup) {
        return null;
    }

    @Override
    public ResultDTO<List<BizBaseVendorDTO>> getVendor(String vendorCode, String bankAccount, String customerId) {
        return null;
    }

    @Override
    public RemoteResponse<BizBaseVendorBankDTO> getListByVendor(String vendorCode, String customerId) {
        return null;
    }

    @Override
    public RemoteResponse<BizBaseCustomerDTO> getListByCustomer(BizBaseCustomerDTO bizBaseCustomerDTO) {
        return null;
    }

    @Override
    public RemoteResponse<BizBaseGtnCategoryDTO> getGtnCategoryByType(String gtnCategoryL1) {
        return null;
    }

    @Override
    public String getCustomerNameByNumber(String customerNumber) {
        return null;
    }

    @Override
    public RemoteResponse<BizBaseApcDTO> bizBaseApcList(BizBaseApcDTO bizBaseApcDTO) {
        return null;
    }

    @Override
    public String getEndCustomerName(String endCustomerId) {
        return null;
    }

    @Override
    public RemoteResponse<BizBaseDcDivisionMappingDTO> getListByDistributionChannel(String salesOrgCode, String salesOfficeCode, String dcCode) {
        return null;
    }

    @Override
    public BizBaseCustomerDTO getCustomer(BizBaseCustomerDTO bizBaseCustomer) {
        return null;
    }

    @Override
    public ResultDTO<List<BizBaseProfitCenterDTO>> selectProfitCenterList(BizBaseProfitCenterDTO bizBaseProfitCenterDTO) {
        return null;
    }

    @Override
    public ResultDTO<BizBaseSegmentDTO> getSegment(String businessGroup, String segmentCode, String segmentLevel) {
        return null;
    }

    @Override
    public ResultDTO<BizBaseRegionMarketDTO> getRegionMarket(String geoCode, String businessGroup, String regionMarketCode) {
        return null;
    }

    @Override
    public ResultDTO<List<BizBaseExchangeRateDTO>> getExchangeRateList(List<ExchangeRateDTO> exchangeRateDTOList) {
        return null;
    }

    @Override
    public ResultDTO<BizBaseMbgCustomerDrmTomsTofiDTO> getByCustomerNumber(String customerNumber, String businessGroup) {
        return null;
    }

    @Override
    public ResultDTO<BizBaseOrgOfficeDTO> getByOrgOffice(String salesOrgCode, String salesOfficeCode) {
        return null;
    }

    @Override
    public RemoteResponse<BizBaseLenovoEntityDTO> bizBaseLenovoEntityList(BizBaseLenovoEntityDTO bizBaseLenovoEntity) {
        return null;
    }

    @Override
    public ResultDTO<BizBaseOrgOfficeDTO> getOrgOfficeByOrgAndBg(String salesOrgCode, String businessGroup) {
        return null;
    }

    @Override
    public ResultDTO<List<BizBaseSelfInvoiceDTO>> getSelfInvoice(String sellerCountry, String gtnCategoryL1, String crmId, String status) {
        return null;
    }

    @Override
    public ResultDTO<List<BizBaseVendorAndPerferBankDTO>> getVendorAndPerferBank(List<String> customerIds) {
        return null;
    }

    @Override
    public ResultDTO<List<BizBaseParty3rdVendorDTO>> getParty3rdVendor(BizBaseParty3rdVendorDTO bizBaseParty3rdVendorDTO) {
        return null;
    }
}
