package br.com.techthordev.employee_management_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class VersionConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

        // API v1
        configurer.addPathPrefix(
                "/v1",
                HandlerTypePredicate.forAnnotation(ApiVersion.class)
                        .and(handlerType -> hasVersion(handlerType, 1))
        );

        // 👉 preparation for v2 (only to activate)
        /*
        configurer.addPathPrefix(
                "/v2",
                HandlerTypePredicate.forAnnotation(ApiVersion.class)
                        .and(handlerType -> hasVersion(handlerType, 2))
        );
        */
    }

    private boolean hasVersion(Class<?> handlerType, int version) {
        ApiVersion apiVersion = handlerType.getAnnotation(ApiVersion.class);
        return apiVersion != null && apiVersion.value() == version;
    }
}
