package com.wc.single.sky.system.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class DataIntegrateApcDTO {

    private List<String> apcList;
    private  List<String> countryList;
    private Integer pageNumber;
    private   Integer pageSize;
    private  List<String> partNumberList;
    private   List<String> externalNameList;
}
