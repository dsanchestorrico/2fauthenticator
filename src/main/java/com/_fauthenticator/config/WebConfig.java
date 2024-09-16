package com._fauthenticator.config;

import com._fauthenticator.filter.SecretKeyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<SecretKeyFilter> secretKeyFilterRegistration() {
        FilterRegistrationBean<SecretKeyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SecretKeyFilter());
        registrationBean.addUrlPatterns("/*"); // Aplica el filtro a todas las URL
        return registrationBean;
    }
}
