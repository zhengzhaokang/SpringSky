package com.wc.single.sky.system.feign.factory;

import com.wc.single.sky.common.core.domain.ResultDTO;
import com.wc.single.sky.system.domain.SysOss;
import com.wc.single.sky.system.feign.RemoteOssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class RemoteOssFallbackFactory implements RemoteOssService {

    @Override
    public ResultDTO<SysOss> upload(MultipartFile file, String module) {
        return null;
    }

    @Override
    public ResultDTO<SysOss> fileUpload(MultipartFile file, String module, String status) {
        return null;
    }

    @Override
    public void downloadByOss(SysOss sysOss) {
    }

    @Override
    public ResultDTO removeByObj(SysOss sysOss) {
        return null;
    }

    @Override
    public ResultDTO saveOss(SysOss sysOss) {
        return null;
    }
}
