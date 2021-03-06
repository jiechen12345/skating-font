package com.ctbc.skatingfont.config;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.util.TrustManagerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.session.DefaultFtpsSessionFactory;


/**
 * Created by JieChen on 2018/12/9.
 */
@Configuration
public class FtpConfig {
    @Autowired
    private FtpProperties ftpProperties;

    @Bean
    public SessionFactory<FTPFile> ftpSessionFactory() {
        DefaultFtpsSessionFactory sf = new DefaultFtpsSessionFactory();
        sf.setHost(ftpProperties.getFtpHost());
        sf.setPort(Integer.parseInt(ftpProperties.getFtpPort()));
        sf.setUsername(ftpProperties.getFtpUsername());
        sf.setPassword(ftpProperties.getFtpPassword());
        sf.setBufferSize(3276800);
        sf.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());
        // sf.setTrustManager(new TrustManager());
        sf.setClientMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);

//        sf.setFileType(2);
//        sf.setUseClientMode(true);
//        sf.setImplicit(true);
//        sf.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());
//        sf.setProt("P");
//        sf.setProtocol("TLSv1.2");
//        sf.setProtocols(new String[]{"TLSv1.2"});
//        sf.setSessionCreation(true);
//        sf.setCipherSuites(new String[]{"TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256"});
        return new CachingSessionFactory<>(sf);
    }


}