package br.com.techthordev.backend.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class VersionConfig implements WebMvcConfigurer {

    private static final List<Integer> SUPPORTED_VERSION = List.of(1);

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

        SUPPORTED_VERSION.forEach(version ->

            configurer.addPathPrefix(
                "/api/v" + version,
                HandlerTypePredicate.forAnnotation(ApiVersionController.class)
            )
        );
        // v2 preparation — uncomment when needed
        /*
        configurer.addPathPrefix(
                "/api/v2",
                HandlerTypePredicate.forAnnotation(ApiVersion.class)
                        .and(handlerType -> hasVersion(handlerType, 2))
        );
        */
    }

}
