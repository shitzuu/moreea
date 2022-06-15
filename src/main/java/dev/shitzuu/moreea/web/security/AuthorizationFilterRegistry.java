package dev.shitzuu.moreea.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationFilterRegistry {

    private final AutowireCapableBeanFactory beanFactory;

    @Autowired
    public AuthorizationFilterRegistry(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> registerFilters() {
        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();

        AuthorizationFilter filter = new AuthorizationFilter();
        beanFactory.autowireBean(filter);
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/api/v1_0_0/verification/*");
        return registrationBean;
    }
}
