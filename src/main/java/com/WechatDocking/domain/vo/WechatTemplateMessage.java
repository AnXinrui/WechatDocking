package com.WechatDocking.domain.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author axr
 * @description
 * @create 2024/11/20 14:45
 */

@Data
public class WechatTemplateMessage {

    private String touser;
    private String template_id;
    private String url = "https://weixin.qq.com";
    private Map<String, Map<String, String>> data = new HashMap<>();

    public WechatTemplateMessage(String touser, String template_id) {
        this.touser = touser;
        this.template_id = template_id;
    }

    public static void put(Map<String, Map<String, String>> data, String key, String value) {
        data.put(key, new HashMap<String, String>() {
            { put("value", value); }
        });
    }
}
