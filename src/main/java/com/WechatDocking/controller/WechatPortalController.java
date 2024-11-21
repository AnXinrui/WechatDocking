package com.WechatDocking.controller;

import com.WechatDocking.common.TextMessage;
import com.WechatDocking.domain.res.AccessTokenRes;
import com.WechatDocking.domain.res.WechatTemplateRes;
import com.WechatDocking.domain.vo.WechatTemplateMessage;
import com.WechatDocking.service.wechat.IWechatApiService;
import com.WechatDocking.utils.Nimo;
import com.WechatDocking.utils.SignatureValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author axr
 * @description 微信接收消息，地址 <a href="http://axr.natapp1.cc/wechat/portal/receive" />
 * @create 2024/11/10 17:25
 */
@Slf4j
@RestController
@RequestMapping("wechat")
public class WechatPortalController {

    @Value("${wechat.config.token}")
    private String TOKEN;

    @Value("${wechat.config.original_id}")
    private String originalId;

    @Value("${wechat.config.appID}")
    private String appID;

    @Value("${wechat.config.appSecret}")
    private String appSecret;

    @Resource
    private IWechatApiService wechatApiService;

    @GetMapping(value = "portal/receive")       // 指定字符串编码，防止乱码？？？ , produces = "text/plain;charset=utf-8"
    public String validate(@RequestParam(value = "signature") String signature,
                           @RequestParam(value = "timestamp") String timestamp,
                           @RequestParam(value = "nonce") String nonce,
                           @RequestParam(value = "echostr") String echoStr) {
        log.info("开始验证消息, signature={}, timestamp={}, nonce={}, echostr={}, token={}", signature, timestamp, nonce, echoStr, TOKEN);
        if (SignatureValidator.validateSignature(TOKEN, signature, timestamp, nonce)) {
            log.info("验证消息成功~");
            return echoStr;
        }
        log.error("验证消息失败！");
        return null;
    }

    @PostMapping("portal/receive")
    public String post(@RequestBody String requestBody) {
        // log.info("RequestBody:\n {}", requestBody);
        XmlMapper xmlMapper = new XmlMapper();

        try {
            TextMessage message = xmlMapper.readValue(requestBody, TextMessage.class);
            log.info("接收{}的微信公众号信息  :  {} ", message.getFromUserName(), message.getContent());

            if (message.getContent() != null && !message.getContent().isEmpty() && message.getContent().equals("模板消息")) {
                WechatTemplateMessage wechatTemplateMessage = new WechatTemplateMessage(message.getFromUserName(), "7JM3NaWv-4GwxkerZ0LpbbyxTychRqkiizRXNhkFw0Y");
                Map<String, Map<String, String>> data = new HashMap<>();
                WechatTemplateMessage.put(data, "user", message.getFromUserName());
                wechatTemplateMessage.setData(data);

                Call<AccessTokenRes> accessTokenResCall = wechatApiService.getToken(
                        "client_credential",
                        appID,
                        appSecret);
                AccessTokenRes res = accessTokenResCall.execute().body();
                assert null != res;
                Call<Void> call = wechatApiService.sendMessage(res.getAccess_token(), wechatTemplateMessage);
                call.execute();
                // System.out.println(call.execute().body());
                return "";
            }

            String content = message.getContent();
            assert content != null && !content.isEmpty();
            return buildTextMessage(message.getFromUserName(), Nimo.reply(content));
            // return buildTextMessage(message.getFromUserName(), "你好，" + message.getContent());
        } catch (Exception e) {
            log.error("接收微信公众号信息请求失败 {}", requestBody, e);
            return "";
        }
    }

    private String buildTextMessage(String openid, String content) throws JsonProcessingException {
        TextMessage res = new TextMessage();
        // 公众号分配的ID
        res.setFromUserName(originalId);
        res.setToUserName(openid);
        res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
        res.setMsgType("text");
        res.setContent(content);
        XmlMapper xmlMapper = new XmlMapper();
        System.out.println(xmlMapper.writeValueAsString(res));
        return xmlMapper.writeValueAsString(res);
    }

}
