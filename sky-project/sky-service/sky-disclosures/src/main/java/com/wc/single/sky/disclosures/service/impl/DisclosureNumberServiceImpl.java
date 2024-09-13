package com.wc.single.sky.disclosures.service.impl;

import com.wc.single.sky.disclosures.dto.DisclosureNumberDto;
import com.wc.single.sky.disclosures.mapper.DisclosureNumberMapper;
import com.wc.single.sky.disclosures.service.DisclosureNumberService;
import com.wc.single.sky.disclosures.util.DisclosureNumberUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DisclosureNumberServiceImpl implements DisclosureNumberService {

    @Autowired
    private DisclosureNumberMapper disclosureNumberMapper;

    @Autowired
    private DisclosureNumberUtils disclosureNumberUtils;

    @Override
    public String getDisclosureNumber(String type) {
//        return getDisNumber(type);
        return disclosureNumberUtils.generateDisclosureNum(6, type);
    }

    private String getDisNumber(String type) {
        String dateTime = getNowTime("yyyyMMdd");
        DisclosureNumberDto number = null;
        Example example = new Example(DisclosureNumberDto.class);
        example.createCriteria().andEqualTo("dateTime",dateTime).andEqualTo("type", type);
        List<DisclosureNumberDto> disclosureNumberDtoList = disclosureNumberMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(disclosureNumberDtoList)) {
            number = disclosureNumberDtoList.get(0);
        }
        int i = 0;
        if (number != null){
            Integer num= Integer.parseInt(number.getNumber())+1;
            number.setNumber(String.format("%06d",num));
            i = disclosureNumberMapper.updateDisclosureNumber(number);
        }else {
            number = new DisclosureNumberDto(dateTime,"000001", type, "0");
            i = disclosureNumberMapper.insertSelective(number);
        }
        if (i > 0){
            return type + dateTime + number.getNumber();
        }else {
            return "";
        }
    }

    private String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        return dateFormat.format(now);
    }
}
