package com.wc.single.sky.disclosures.util;

import com.alibaba.fastjson.JSON;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.wc.single.sky.common.utils.spring.SpringUtils;
import com.wc.single.sky.disclosures.config.AwsS3Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Desc 亚马逊S3文件服务工具类
 * @Author yangshuai11
 * @Date 2022.11.08
 */
@Slf4j
public class AWSS3Util {

    private static AmazonS3 s3;

    /**
     * @Desc 上传文件
     * @Author yangshuai11
     * @Date 2022.11.08
     */
    public static boolean putObject(String bucketName, String fileKey, File file) {
        try {
            //桶名强制小写
            bucketName = bucketName.toLowerCase();
            //初始化Bucket
            initBucket(bucketName);
            //上传文件
            PutObjectResult s = getClient().putObject(bucketName, fileKey, file);
            log.info("S3上传文件！bucketName：" + bucketName + "；文件名：" + file.getName() + "；返回值：" + JSON.toJSONString(s));
            return true;
        } catch (AmazonServiceException e) {
            log.error("S3上传文件时出错！bucketName：" + bucketName + "；文件名：" + file.getName() + "；报错信息：" + e.getErrorMessage());
            return false;
        }
    }

    /**
     * @Desc 上传文件
     * @Author yangshuai11
     * @Date 2023.2.28
     */
    public static boolean putObject(PutObjectRequest putObjectRequest) {
        try {
            //桶名强制小写
            String bucketName = putObjectRequest.getBucketName();
            bucketName = bucketName.toLowerCase();
            //初始化Bucket
            initBucket(bucketName);
            //上传文件
            PutObjectResult s = getClient().putObject(putObjectRequest);
            log.info("S3上传文件！putObjectRequest：{}", JSON.toJSONString(putObjectRequest));
            return true;
        } catch (AmazonServiceException e) {
            log.error("S3上传文件时出错！putObjectRequest：{}", JSON.toJSONString(putObjectRequest));
            return false;
        }
    }


    /**
     * @Desc 下载文件
     * @Author yangshuai11
     * @Date 2022.11.08
     */
    public static S3ObjectInputStream getObject(String bucketName, String fileKey) {
        //桶名强制小写
        bucketName = bucketName.toLowerCase();

        S3Object o = getClient().getObject(bucketName, fileKey);
        S3ObjectInputStream s3is = o.getObjectContent();
        return s3is;
    }

    /**
     * 获取URL下载
     * @param bucketName
     * @param fileKey
     * @return
     */
    public static String getUrl(String bucketName, String fileKey,String fileName) {
        //桶名强制小写
        bucketName = bucketName.toLowerCase();
        GeneratePresignedUrlRequest httpRequest = new GeneratePresignedUrlRequest(bucketName, fileKey);
        ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides();
        responseHeaders.setContentDisposition("attachment; filename="+ fileName);
        httpRequest.setResponseHeaders(responseHeaders);
        String url = getClient().generatePresignedUrl(httpRequest).toString();
        return url;
    }

    /**
     * @Desc 删除文件
     * @Author yangshuai11
     * @Date 2022.12.12
     */
    public static boolean deleteObject(String bucketName, String fileKey) {
        //桶名强制小写
        bucketName = bucketName.toLowerCase();

        try {
            getClient().deleteObject(bucketName, fileKey);
            return true;
        } catch (AmazonServiceException e) {
            log.error("S3删除文件失败；bucketName:{};fileKey{}", bucketName, fileKey, e);
            return false;
        }
    }

    /**
     * @Desc 批量删除文件
     * @Author yangshuai11
     * @Date 2022.12.12
     */
    public static boolean deleteObjects(String bucketName, String[] fileKey) {
        //桶名强制小写
        bucketName = bucketName.toLowerCase();

        try {
            DeleteObjectsRequest dor = new DeleteObjectsRequest(bucketName)
                    .withKeys(fileKey);
            getClient().deleteObjects(dor);
            return true;
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * @Desc 上传文件前需要先有桶
     * @Author yangshuai11
     * @Date 2022.11.08
     */
    public static void initBucket(String bucketName) {
        //桶名强制小写
        bucketName = bucketName.toLowerCase();

        //检验桶是否存在
        boolean exist = getClient().doesBucketExistV2(bucketName);

        //不存在则创建
        if (!exist) {
            try {
                getClient().createBucket(bucketName);
            } catch (AmazonS3Exception e) {
                log.error("S3创建桶时出错！bucketName：" + bucketName + "；报错信息：" + e.getErrorMessage());
            }
        }
    }


    /**
     * @Desc 创建桶
     * @Author yangshuai11
     * @Date 2022.11.08
     */
    public static Bucket createBucket(String bucketName) {
        //桶名强制小写
        bucketName = bucketName.toLowerCase();

        Bucket b = null;
        boolean exist = getClient().doesBucketExistV2(bucketName);
        if (exist) {
            b = getBucket(bucketName);
        } else {
            try {
                b = getClient().createBucket(bucketName);
            } catch (AmazonS3Exception e) {
                log.error("S3创建桶时出错！bucketName：" + bucketName + "；报错信息：" + e.getErrorMessage());
            }
        }
        return b;
    }


    /**
     * @Desc 获取桶
     * @Author yangshuai11
     * @Date 2022.11.08
     */
    public static Bucket getBucket(String bucketName) {
        //桶名强制小写
        bucketName = bucketName.toLowerCase();

        Bucket namedBucket = null;
        //获取所有桶
        List<Bucket> buckets = listBuckets();

        for (Bucket b : buckets) {
            if (b.getName().equals(bucketName)) {
                namedBucket = b;
            }
        }
        return namedBucket;
    }


    /**
     * @Desc 查看所有桶
     * @Author yangshuai11
     * @Date 2022.11.08
     */
    public static List<Bucket> listBuckets() {
        List<Bucket> buckets = getClient().listBuckets();

        /*for (Bucket b : buckets) {
            System.out.println("查看所有桶：");
            System.out.println(" * " + b.getName());
        }*/

        return buckets;
    }


    /**
     * @Desc 读取某个bucket下的所有文件
     * @Author yangshuai11
     * @Date 2022.11.08
     */
    public static List<S3ObjectSummary> getFiles(String bucketName) {
        //桶名强制小写
        bucketName = bucketName.toLowerCase();

        ListObjectsV2Result result = getClient().listObjectsV2(bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        /*for (S3ObjectSummary os : objects) {
            System.out.println("* " + os.getKey());
        }*/
        return objects;
    }

    /**
     * @Desc 构建客户端对象
     * @Author yangshuai11
     * @Date 2022.11.08
     */
    private static AmazonS3 getClient() {
        if (s3 == null) {
            AwsS3Config awsS3Config = SpringUtils.getBean("awsS3Config");


            //访问凭证对象
            BasicAWSCredentials credentials = new BasicAWSCredentials(awsS3Config.getAccesskey(), awsS3Config.getSecretkey());
//            BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyID, secretKey);

            ClientConfiguration configuration = new ClientConfiguration();
            configuration.setProtocol(Protocol.HTTP);
            configuration.setSignerOverride("S3SignerType");

            //禁止SDK内部的域名加工，否则会出问题
            AmazonS3ClientBuilder builder = AmazonS3Client.builder().enablePathStyleAccess();

            builder.setClientConfiguration(configuration);
            builder.setCredentials(new AWSStaticCredentialsProvider(credentials));
//            builder.setEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region.getName()));
            builder.setEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsS3Config.getEndpoint(), awsS3Config.getRegion().getName()));
            s3 = builder.build();
        }
        return s3;
    }


    /**
     * @Desc multipartFile转成File
     * @Author yangshuai11
     * @Date 2022.11.08
     */
    public static File transferToFile(MultipartFile multipartFile) {
        //选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        String originalFilename = multipartFile.getOriginalFilename();
        try {

            String[] filename = originalFilename.split("\\.");
            String fileName=filename[0];
            file = File.createTempFile(StringUtils.rightPad(fileName,3,"_"), filename[1] + ".");
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.error("multipartFile转成File。文件名：" + originalFilename);
        }
        return file;
    }

}
