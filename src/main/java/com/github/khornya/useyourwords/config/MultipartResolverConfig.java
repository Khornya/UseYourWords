package com.github.khornya.useyourwords.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@Component
public class MultipartResolverConfig {

    /**
     * Create the multipart resolver bean
     * Set the maximum upload file size as defined in the configuration file
     * @return the multipartResolver bean
     */
    @Bean(name="multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10000000);
        multipartResolver.setMaxUploadSizePerFile(10000000);
        return multipartResolver;
    }
}
