package com.wc.single.sky.system.domain.dto;

import com.wc.single.sky.common.core.page.RemoteResponse;
import lombok.Data;

@Data
public class LoginTokenResponse extends RemoteResponse {

    private String token;
}
