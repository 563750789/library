package com.tencent.library.config;

import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Markjrzhang
 * @Date 2021/11/27 2:14
 */
@Configuration
public class CosConfig {
    @Value("${cos.sid}")
    private String sid;

    @Value("${cos.skey}")
    private String skey;

    @Value("${cos.region}")
    private String region;

    @Bean
    public COSCredentials getCredentials() {
        return new BasicCOSCredentials(sid, skey);
    }

    @Bean
    public ClientConfig getClientConfig() {
        return new ClientConfig(new Region(this.region));
    }
}
