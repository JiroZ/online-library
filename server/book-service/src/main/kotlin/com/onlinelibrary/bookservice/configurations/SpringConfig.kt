package com.onlinelibrary.bookservice.configurations

import com.onlinelibrary.bookservice.dto.user.ServerUser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class SpringConfig {
    @Bean
    fun validatingMongoEventListener(): ValidatingMongoEventListener {
        return ValidatingMongoEventListener(validator())
    }

    @Bean
    fun validator(): LocalValidatorFactoryBean {
        return LocalValidatorFactoryBean()
    }

    @Bean
    fun serverUserBean(): ServerUser {
        return ServerUser("", false, "")
    }
}