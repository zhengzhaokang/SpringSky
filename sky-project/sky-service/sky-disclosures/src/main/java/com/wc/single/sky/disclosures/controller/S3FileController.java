package com.wc.single.sky.disclosures.controller;

import com.alibaba.fastjson.JSON;
import com.wc.single.sky.common.core.controller.BaseController;
import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.common.utils.StringUtils;
import com.wc.single.sky.disclosures.common.enums.S3BucketEnum;
import com.wc.single.sky.disclosures.common.enums.S3FileTypeEnum;
import com.wc.single.sky.disclosures.service.IoS3AttachmentService;
import com.wc.single.sky.disclosures.util.EnumUtil;
import com.wc.single.sky.disclosures.vo.DisclosureS3AttachmentVO;
import com.wc.single.sky.disclosures.vo.S3FileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * S3FileController For
 *
 * @author tsj
 * @date 2022/12/12 16:06
 * @since 1.8
 */
@RestController
@RequestMapping("/disclosures/S3File")
@Api(value = "S3 Files", tags = {"S3 Files"})
@Slf4j
public class S3FileController extends BaseController {

    @Autowired
    private IoS3AttachmentService ioS3AttachmentService;

    @ApiOperation(value = "deleteFile", notes = "delete File")
    @PostMapping(value = "/deleteFile")
    public ResultDTO<Object> deleteFile(@RequestParam("fileKey") String fileKey) {
        try {
            ioS3AttachmentService.deleteFile(fileKey);
            return ResultDTO.success();
        } catch (Exception e) {
            log.error("S3FileController deleteFile error:{}", e.getMessage(), e);
            return ResultDTO.fail("删除失败");
        }
    }

    @ApiOperation(value = "uploadAttachmentFile", notes = "upload Attachment")
    @PostMapping(value = "/uploadAttachmentFile")
    public ResultDTO<Object> uploadAttachmentFile(@RequestParam("fileUpload") MultipartFile[] files) {
        try {
            String s3Bucket = StringUtils.lowerCase(getRequest().getHeader("s3Bucket"));
            String s3FileType = getRequest().getHeader("s3FileType");
            String ticketNumber = getRequest().getHeader("ticketNumber");
            String createUser = getRequest().getHeader("createUser");
            log.info("S3FileController uploadAttachmentFile.s3Bucket:{}, s3FileType:{}, ticketNumber:{}, createUser:{}", s3Bucket, s3FileType, ticketNumber, createUser);
            if (files == null || !EnumUtil.isInclude(S3BucketEnum.class, s3Bucket) || !EnumUtil.isInclude(S3FileTypeEnum.class, s3FileType)) {
                return ResultDTO.fail("param error");
            }
            S3BucketEnum s3BucketEnum = EnumUtil.getEnumFromString(S3BucketEnum.class, s3Bucket);
            S3FileTypeEnum s3FileTypeEnum = EnumUtil.getEnumFromString(S3FileTypeEnum.class, s3FileType);
            if (null == s3FileTypeEnum || null == s3BucketEnum) {
                return ResultDTO.fail("param error");
            }
            List<DisclosureS3AttachmentVO> disclosureS3AttachmentVOList = new ArrayList<>();
            for (MultipartFile file : files) {
                String role = "Attachment";
                S3FileVO s3FileVO = S3FileVO.builder().role(role).s3FileType(s3FileType).s3Bucket(s3Bucket).createUser(createUser).
                        multipartFile(file).ticketNumber(ticketNumber).build();

                DisclosureS3AttachmentVO disclosureS3AttachmentVO = ioS3AttachmentService.uploadFile(s3FileVO);
                disclosureS3AttachmentVOList.add(disclosureS3AttachmentVO);
            }
            log.info("S3FileController uploadAttachmentFile.disclosureS3AttachmentVOList:{}, ticketNumber:{}", JSON.toJSONString(disclosureS3AttachmentVOList), ticketNumber);
            return ResultDTO.success(disclosureS3AttachmentVOList);
        } catch (Exception e) {
            log.error("S3FileController uploadAttachmentFile.error:{}", e.getMessage(), e);
            return ResultDTO.fail("upload fail");
        }
    }

    @ApiOperation(value = "getAttachment", notes = "根据ticketNumber获取全部文件信息")
    @GetMapping(value = "/getAttachment")
    public ResultDTO<Object> getAttachment(@RequestParam("ticketNumber") String ticketNumber) {
        try {
            List<DisclosureS3AttachmentVO> attachment = ioS3AttachmentService.getS3AttachmentByTicketNumber(ticketNumber);
            return ResultDTO.success(attachment);
        } catch (Exception e) {
            log.error("S3FileController getAttachment error:{}", e.getMessage(), e);
            return ResultDTO.fail(new ArrayList<>());
        }
    }


    @ApiOperation(value = "downloadFile", notes = "download File")
    @GetMapping(value = "/downloadFile")
    public void downloadFile(@RequestParam("fileKey") String fileKey) {
        try {
            ioS3AttachmentService.downloadCors(getRequest(), getResponse(), fileKey);
        } catch (Exception e) {
            log.error("S3FileController downloadFile error:{}", e.getMessage(), e);
        }

    }

    @ApiOperation(value = "downloadFileUrl", notes = "download File URL")
    @GetMapping(value = "/downloadFileUrl")
    public ResultDTO<Object> downloadFileUrl(@RequestParam("fileKey") String fileKey) {
        try {
            return ResultDTO.success(ioS3AttachmentService.downloadUrl(fileKey));
        } catch (Exception e) {
            log.error("S3FileController.downloadFileUrl error:{}", e.getMessage(), e);
            return ResultDTO.fail("get downloadFileUrl fail");
        }
    }
}
