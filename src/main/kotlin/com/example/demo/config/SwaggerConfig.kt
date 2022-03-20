package com.example.demo.config

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import io.swagger.v3.oas.models.servers.ServerVariables
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun publicApi(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("tdc-public")
            .pathsToMatch("/**")
            .build()

    @Bean
    fun springShopOpenAPI(): OpenAPI? {
        return OpenAPI()
            .info(
                Info().title("Spring API")
                    .description("Spring sample application")
                    .version("v0.0.1")
                    .license(License().name("Apache 2.0").url("https://springdoc.org"))
            )
            .servers(
                listOf(
                    Server().apply {
                        url = "http://localhost:8080"
                        description = "Local"
//                        variables = ServerVariables().apply { addServerVariable("http", listOf("https")) }
                    },
                    Server().apply {
                        url = "https://tdc-xih52cdawq-rj.a.run.app"
                        description = "Cloud Run"
                    })
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("SpringShop Wiki Documentation")
                    .url("https://springshop.wiki.github.org/docs")
            )
    }

}
