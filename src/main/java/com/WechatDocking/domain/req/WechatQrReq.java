package com.WechatDocking.domain.req;

import lombok.*;
import retrofit2.http.Body;

/**
 * @author axr
 * @description 创建二维码 ticket 请求参数
 * @create 2024/11/22 08:21
 */
@Data
@Builder
public class WechatQrReq {
    private int expire_seconds;
    private String action_name;
    private ActionInfo action_info;

    @Data
    @Builder
    public static class ActionInfo {

        Scene scene;

        @Data
        @Builder
        public static class Scene {
            private int scene_id;
            private int scene_str;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum ActionNameTypeVO {
        QR_SCENE("QR_SCENE", "临时的整型参数值"),
        QR_STR_SCENE("QR_STR_SCENE", "临时的字符串参数值"),
        QR_LIMIT_SCENE("QR_LIMIT_SCENE", "永久的整型参数值"),
        QR_LIMIT_STR_SCENE("QR_LIMIT_STR_SCENE", "永久的字符串参数值");

        private String code;
        private String info;
    }
}
