package dkb.cf.urlshortener.app.configurations

import dkb.cf.urlshortener.domain.boundaries.input.UrlService
import dkb.cf.urlshortener.domain.boundaries.output.ConfigService
import dkb.cf.urlshortener.domain.boundaries.output.TokenGeneratorService
import dkb.cf.urlshortener.domain.boundaries.output.UrlMappingRepository
import dkb.cf.urlshortener.domain.services.UrlServiceImpl
import dkb.cf.urlshortener.infrastructure.persistence.repositories.UrlMappingMongoRepository
import dkb.cf.urlshortener.infrastructure.persistence.repositories.UrlMappingRepositoryImpl
import dkb.cf.urlshortener.infrastructure.services.TokenGeneratorServiceImpl
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {
    @Bean
    fun urlService(
        urlMappingRepository: UrlMappingRepository,
        tokenGeneratorService: TokenGeneratorService,
    ): UrlService =
        UrlServiceImpl(
            urlMappingRepository = urlMappingRepository,
            tokenGeneratorService = tokenGeneratorService,
        )

    @Bean
    fun urlMappingRepository(mongoRepository: UrlMappingMongoRepository): UrlMappingRepository =
        UrlMappingRepositoryImpl(
            mongoRepository = mongoRepository,
        )

    @Bean
    fun tokenGeneratorService(configService: ConfigService): TokenGeneratorService =
        TokenGeneratorServiceImpl(
            configService = configService,
        )

    @Bean
    fun openApi(): OpenAPI {
        val information: Info =
            Info()
                .title("Url shortener API")
                .version("1.0")
                .description("This API exposes endpoints to generate token and get original url with it")

        return OpenAPI().info(information)
    }
}
