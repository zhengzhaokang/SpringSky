package com.wc.single.sky.disclosures.config;

import com.amazonaws.regions.Regions;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AwsS3Config {
    //所属地区
    private final Regions region = Regions.US_EAST_1;


    //accessKeyID
    @Value("${aws.accesskey}")
    private String accesskey;

    //secretKey
    @Value("${aws.secretkey}")
    private String secretkey;

    //endpoint
    @Value("${aws.endpoint}")
    private String endpoint;
}
