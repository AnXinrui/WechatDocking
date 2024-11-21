package com.WechatDocking.service.wechat;

import com.WechatDocking.domain.res.AccessTokenRes;
import com.WechatDocking.domain.res.WechatTemplateRes;
import com.WechatDocking.domain.vo.WechatTemplateMessage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import javax.annotation.Resource;

/**
 * @author axr
 * @description 微信API服务 retrofit2
 * @create 2024/11/20 11:56
 */
public interface IWechatApiService {

    /**
     *  获取 access token
     */
    @GET("cgi-bin/token")
    Call<AccessTokenRes> getToken(@Query("grant_type") String grantType,
                                  @Query("appid") String appId,
                                  @Query("secret") String appSecret);

    /**
     * 发送模板消息
     */
    @POST("cgi-bin/message/template/send")
    Call<Void> sendMessage(@Query("access_token") String accessToken, @Body WechatTemplateMessage wechatTemplateMessage);

}
