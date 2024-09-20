package com.wc.single.sky.disclosures.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.wc.single.sky.common.utils.SnowFlakeUtil;
import com.wc.single.sky.common.utils.StringUtils;
import com.wc.single.sky.common.utils.bean.BeanUtils;
import com.wc.single.sky.disclosures.common.DisclosureConstant;
import com.wc.single.sky.disclosures.common.enums.S3FileTypeEnum;
import com.wc.single.sky.disclosures.dto.DisclosureS3AttachmentDto;
import com.wc.single.sky.disclosures.mapper.DisclosureS3AttachmentMapper;
import com.wc.single.sky.disclosures.service.IoS3AttachmentService;
import com.wc.single.sky.disclosures.util.AWSS3Util;
import com.wc.single.sky.disclosures.vo.DisclosureS3AttachmentVO;
import com.wc.single.sky.disclosures.vo.S3FileVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class IoS3AttachmentServiceImpl implements IoS3AttachmentService {

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Autowired
    private DisclosureS3AttachmentMapper disclosureS3AttachmentMapper;

    @Value("${aws.bucketname}")
    private String bucket;

    @Override
    public Boolean deleteFile(String fileKey) {
        boolean flag = false;
        DisclosureS3AttachmentDto disclosureS3AttachmentDto = getDisclosureS3AttachmentDto(fileKey);
        if (disclosureS3AttachmentDto != null) {
            String bucket = disclosureS3AttachmentDto.getBucket();
            return AWSS3Util.deleteObject(bucket, fileKey);
        }
        log.info("### IoS3AttachmentServiceImpl deleteFile fileKey:{}, flag:{}", fileKey, flag);
        return false;
    }

    private DisclosureS3AttachmentDto getDisclosureS3AttachmentDto(String fileKey) {
        Example example = new Example(DisclosureS3AttachmentDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.FILE_KEY, fileKey).andEqualTo(DisclosureConstant.DEL_FLAG, DisclosureConstant.ENABLE);
        return disclosureS3AttachmentMapper.selectOneByExample(example);
    }

    @Override
    public List<DisclosureS3AttachmentVO> getS3AttachmentByTicketNumber(String ticketNumber) {
        Example example = new Example(DisclosureS3AttachmentDto.class);
        example.createCriteria().andEqualTo(DisclosureConstant.TICKET_NUMBER, ticketNumber).andEqualTo(DisclosureConstant.DEL_FLAG, DisclosureConstant.ENABLE);
        List<DisclosureS3AttachmentDto> disclosureS3AttachmentDtoList = disclosureS3AttachmentMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(disclosureS3AttachmentDtoList)) {
            log.warn("### IoS3AttachmentServiceImpl getS3AttachmentByTicketNumber ticketNumber:{}, disclosureS3AttachmentDtoList is null", ticketNumber);
            return null;
        }
        List<DisclosureS3AttachmentVO> disclosureS3AttachmentVOList = new ArrayList<>();
        for (int i = 0; i < disclosureS3AttachmentDtoList.size(); i++) {
            DisclosureS3AttachmentDto item = disclosureS3AttachmentDtoList.get(i);
            String fileKey = item.getFileKey();
            DisclosureS3AttachmentVO s3AttachmentVO = new DisclosureS3AttachmentVO();
            BeanUtils.copyProperties(item, s3AttachmentVO);
            s3AttachmentVO.setDownloadUrl("/disclosures/S3File/downloadFileUrl?fileKey=" + fileKey);
            s3AttachmentVO.setAttachmentRole(item.getRole());
            s3AttachmentVO.setAttachmentNumber(String.valueOf(i + 1));
            s3AttachmentVO.setAttachmentName(item.getFileName());
            disclosureS3AttachmentVOList.add(s3AttachmentVO);
        }
        return disclosureS3AttachmentVOList;
    }

    @Override
    public Boolean downloadCors(HttpServletRequest req, HttpServletResponse res, String fileKey) {
        DisclosureS3AttachmentDto disclosureS3AttachmentDto = getDisclosureS3AttachmentDto(fileKey);
        if (disclosureS3AttachmentDto == null) {
            log.error("### IoS3AttachmentServiceImpl downloadCors fileKey:{} is null", fileKey);
            return false;
        }

        String bucketName = disclosureS3AttachmentDto.getBucket();
        String fileName = disclosureS3AttachmentDto.getFileName();

        //下载文件
        return downloadCors(req, res, bucketName, fileKey, fileName);
    }

    public Boolean downloadCors(HttpServletRequest request, HttpServletResponse res, String bucketName, String fileKey, String fileName) {
        S3ObjectInputStream s3is = null;
        ServletOutputStream output = null;
        try {
            s3is = AWSS3Util.getObject(bucketName, fileKey);
            res.reset();

            String userAgent = request.getHeader("User-agent");
            byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8");
            fileName = new String(bytes, "GBK");
            res.setHeader("filename", fileName);
            res.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));

            res.addHeader("Content-Length", "" + s3is.available());
//            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            res.setContentType("application/octet-stream");
            res.setCharacterEncoding("utf-8");

            res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, CorsConfiguration.ALL);
            res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            res.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, CorsConfiguration.ALL);
            res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, CorsConfiguration.ALL);

            int count;
            byte[] by = new byte[1024];
            //通过response对象回去outputStream流
            output = res.getOutputStream();
            while ((count = s3is.read(by)) != -1) {
                //将缓存区的数据输出到浏览器
                output.write(by, 0, count);
            }
        } catch (AmazonServiceException e) {
            log.error("### IoS3AttachmentServiceImpl 0 S3下载文件时出错！bucketName：" + bucketName + "；文件key：" + fileKey + "；报错信息：" + e.getErrorMessage());
            return false;
        } catch (IOException e) {
            log.error("### IoS3AttachmentServiceImpl 1 S3下载文件时出错！bucketName：" + bucketName + "；文件key：" + fileKey + "；报错信息：" + e.getMessage());
            return false;
        } finally {
            try {
                if (s3is  != null) {
                    s3is.close();
                }
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException e) {
                log.error("### IoS3AttachmentServiceImpl 2 S3下载文件时出错！bucketName：" + bucketName + "；文件key：" + fileKey + "；报错信息：" + e.getMessage());
            }
        }

        return true;
    }

    @Override
    public String downloadUrl(String fileKey) {
        DisclosureS3AttachmentDto disclosureS3AttachmentDto = getDisclosureS3AttachmentDto(fileKey);
        if (disclosureS3AttachmentDto == null) {
            log.error("### IoS3AttachmentServiceImpl downloadUrl fileKey:{}, disclosureS3AttachmentDto is null", fileKey);
            return null;
        }
        String bucketName = disclosureS3AttachmentDto.getBucket();
        String fileName = disclosureS3AttachmentDto.getFileName();
        fileName = fileName.replaceAll(",", "");
        return AWSS3Util.getUrl(bucketName, fileKey, fileName);
    }

    @Override
    public DisclosureS3AttachmentVO uploadFile(S3FileVO s3FileVO) {
        if (s3FileVO == null) {
            log.error("### IoS3AttachmentServiceImpl uploadFile S3FileVO is null");
            return null;
        }
        //通过S3FileTypeEnum枚举和雪花ID，生成20位的唯一的fileKey
        String uuid = String.valueOf(SnowFlakeUtil.nextId());
        String s3FileType = s3FileVO.getS3FileType();
        S3FileTypeEnum s3FileTypeEnum = S3FileTypeEnum.getEnumByCode(s3FileType);
        if (s3FileTypeEnum == null) {
            log.error("### IoS3AttachmentServiceImpl uploadFile S3FileTypeEnum is null");
            return null;
        }
        String level = s3FileTypeEnum.getLevel();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String format = simpleDateFormat.format(new Date());

        MultipartFile multipartFile = s3FileVO.getMultipartFile();
        String fileName = multipartFile.getOriginalFilename();
        if (StringUtils.isBlank(fileName)) {
            log.error("### IoS3AttachmentServiceImpl uploadFile fileName is null");
            return null;
        }
        int lastIndexOf = fileName.lastIndexOf(".");
        String fileType = fileName.substring(lastIndexOf);
        String fileKey = format + "/" + level + uuid + fileType;
//        String fileKey = format + "/" + fileName;

        //multipartFile转成File
        File file = AWSS3Util.transferToFile(multipartFile);
        if (file == null) {
            log.error("### IoS3AttachmentServiceImpl uploadFile transferToFile fail");
            return null;
        }

        //上传到S3
        String bucketName = bucket + s3FileVO.getS3Bucket();
        boolean uploadFlag = AWSS3Util.putObject(bucketName, fileKey, file);
        log.info("### IoS3AttachmentServiceImpl uploadFile putObject uploadFlag is {}", uploadFlag);
        if (!uploadFlag) {
            log.error("### IoS3AttachmentServiceImpl uploadFile putObject fail");
            return null;
        }
        String downloadUrl = AWSS3Util.getUrl(bucketName, fileKey, fileName);
        DisclosureS3AttachmentVO disclosureS3AttachmentVO = DisclosureS3AttachmentVO.builder().role(s3FileVO.getRole()).ticketNumber(s3FileVO.getTicketNumber()).
                dataId(s3FileVO.getTicketNumber()).size(String.valueOf(multipartFile.getSize())).bucket(bucketName).downloadUrl(downloadUrl).
                fileKey(fileKey).fileName(fileName).createUser(s3FileVO.getCreateUser()).createDate(new Date()).build();
        //删除临时文件
        if (file.exists()) {
            file.delete();
        }
        return disclosureS3AttachmentVO;
    }
}
