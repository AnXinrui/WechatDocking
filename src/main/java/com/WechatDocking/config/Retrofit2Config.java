package com.WechatDocking.config;

import com.WechatDocking.service.wechat.IWechatApiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author axr
 * @description
 * @create 2024/11/20 13:20
 */
@Configuration
public class Retrofit2Config {

    private static final String BASE_URL = "https://api.weixin.qq.com";

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Bean
    public IWechatApiService wechatApiService(Retrofit retrofit) {
        return retrofit.create(IWechatApiService.class);
    }

}
