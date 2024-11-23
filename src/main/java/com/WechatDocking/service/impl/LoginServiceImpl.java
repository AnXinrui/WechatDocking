package com.WechatDocking.service.impl;

import com.WechatDocking.domain.req.WechatQrReq;
import com.WechatDocking.domain.res.AccessTokenRes;
import com.WechatDocking.domain.res.WechatQrRes;
import com.WechatDocking.service.ILoginService;
import com.WechatDocking.service.wechat.IWechatApiService;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author axr
 * @description
 * @create 2024/11/22 08:42
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    private IWechatApiService wechatApiService;

    @Override
    public String createQrCodeTicket() throws Exception {
        // 获取 access_token
        Call<AccessTokenRes> accessTokenResCall = wechatApiService.getToken(
                "client_credential",
                "wx5bd6903745bbd806",
                "3fe79a52dbdb56866ebca97f034943df");
        AccessTokenRes accessTokenRes = accessTokenResCall.execute().body();
        assert accessTokenRes != null;
        String accessToken = accessTokenRes.getAccess_token();

        // 获取 ticket
        WechatQrReq weixinQrCodeReq = WechatQrReq.builder()
                .expire_seconds(2592000)
                .action_name(WechatQrReq.ActionNameTypeVO.QR_SCENE.getCode())
                .action_info(WechatQrReq.ActionInfo.builder()
                        .scene(WechatQrReq.ActionInfo.Scene.builder()
                                .scene_id(100601)
                                .build())
                        .build())
                .build();


        Call<WechatQrRes> wechatQrResCall = wechatApiService.createQrCode(accessToken, weixinQrCodeReq);
        return Objects.requireNonNull(wechatQrResCall.execute().body()).getTicket();
    }
}
