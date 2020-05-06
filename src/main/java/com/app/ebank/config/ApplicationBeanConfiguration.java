package com.app.ebank.config;

import com.app.ebank.util.ValidationUtil;
import com.app.ebank.util.impl.ValidationUtilImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder (){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public ValidationUtil validationUtil(){
        return new ValidationUtilImpl();
    }
}
