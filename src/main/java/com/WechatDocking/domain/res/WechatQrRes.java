package com.WechatDocking.domain.res;

import lombok.Data;

/**
 * @author axr
 * @description 生成带参数的二维码的返回对象
 * @create 2024/11/22 08:16
 */
@Data
public class WechatQrRes {

    /**
     * 获取的二维码ticket
     */
    private String ticket;
    private int expire_seconds;
    private String url;

}
