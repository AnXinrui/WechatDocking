package com.WechatDocking.domain.res;

import lombok.Data;

/**
 * @author axr
 * @description 获取 Access token DTO 对象
 * @create 2024-11-20 13:32
 */
@Data
public class AccessTokenRes {

    private String access_token;
    private int expires_in;
    private String errcode;
    private String errmsg;

}
