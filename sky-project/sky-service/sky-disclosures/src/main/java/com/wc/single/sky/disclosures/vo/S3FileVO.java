package com.wc.single.sky.disclosures.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class S3FileVO {

    private MultipartFile multipartFile;

    private String level;

    private String s3Bucket;

    private String s3FileType;

    private String ticketNumber;

    private String dataId;

    private String role;

    private String createUser;
}
