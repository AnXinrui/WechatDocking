package com.WechatDocking.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import lombok.Data;

/**
 * @author axr
 * @description
 * @create 2024/11/17 19:43
 */
@Data
@JsonRootName("xml")
public class TextMessage {
    @JacksonXmlCData // 添加 <![CDATA[]]> 包裹
    @JsonProperty("ToUserName")
    private String toUserName;

    @JacksonXmlCData
    @JsonProperty("FromUserName")
    private String fromUserName;

    @JsonProperty("CreateTime")
    private String createTime;

    @JacksonXmlCData
    @JsonProperty("MsgType")
    private String msgType;

    @JacksonXmlCData
    @JsonProperty("Event")
    private String event;

    @JacksonXmlCData
    @JsonProperty("Content")
    private String content;

    @JsonProperty("MsgId")
    private String msgId;

    @JsonProperty("MsgID")
    private String msgID;

    @JacksonXmlCData
    @JsonProperty("Status")
    private String status;

}
