package com.example.demo.config

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun publicApi(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("tdc-api")
            .pathsToMatch("/**")
            .build()

    @Bean
    fun springShopOpenAPI() =
        OpenAPI()
            .info(
                Info().title("TDC API")
                    .description("TDC sample application")
                    .version("v0.0.1")
                    .license(License().name("Apache 2.0").url("https://springdoc.org"))
            )
            .addServersItem(Server().url("/"))
            .externalDocs(
                ExternalDocumentation()
                    .description("Spring Wiki Documentation")
                    .url("https://spring.wiki.github.org/docs")
            )
}
