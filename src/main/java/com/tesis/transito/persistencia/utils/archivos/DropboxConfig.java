package com.tesis.transito.persistencia.utils.archivos;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DropboxConfig {

    @Value("${dropbox.accessToken}")
    private String accessToken;

    @Value("${dropbox.refreshToken}")
    private String refreshToken;

    @Value("${dropbox.app.key}")
    private String appKey;

    @Value("${dropbox.app.secret}")
    private String appSecret;

    @Bean
    public DbxClientV2 dropboxClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("transito").build();

        DbxCredential credential = new DbxCredential(accessToken, -1L, refreshToken, appKey, appSecret);

        return new DbxClientV2(config, credential);
    }
}
