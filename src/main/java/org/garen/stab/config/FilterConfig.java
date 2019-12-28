package org.garen.stab.config;

import org.garen.stab.filter.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig
{
    @Bean
    public FilterRegistrationBean requestFilter()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean(new RequestFilter(), new ServletRegistrationBean[0]);
        registration.addUrlPatterns(new String[] { "/stab/*" });
        return registration;
    }
}
