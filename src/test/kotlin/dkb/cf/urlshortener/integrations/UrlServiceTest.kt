package dkb.cf.urlshortener.integrations

import dkb.cf.urlshortener.domain.boundaries.input.UrlService
import dkb.cf.urlshortener.domain.exceptions.UrlMappingNotFoundException
import dkb.cf.urlshortener.infrastructure.persistence.models.UrlMapping
import dkb.cf.urlshortener.infrastructure.persistence.repositories.UrlMappingMongoRepository
import dkb.cf.urlshortener.utils.MongoDBTestContainer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UrlServiceTest : MongoDBTestContainer() {
    @Autowired
    private lateinit var urlService: UrlService

    @Autowired
    private lateinit var urlMappingRepository: UrlMappingMongoRepository

    @AfterEach
    fun cleanUp() {
        urlMappingRepository.deleteAll()
    }

    @Test
    fun `should shorten URL successfully`() {
        val url = "https://example.com"
        val response =
            urlService.shortenUrl(
                originalUrl = url,
            )

        assertEquals("https://example.com", response.originalUrl)
        val token = response.token

        val savedEntity = urlMappingRepository.findByToken(token)
        assertNotNull(savedEntity)
        assertEquals("https://example.com", savedEntity?.originalUrl)
        assertEquals(token, savedEntity?.token)
        assertEquals(response.id, savedEntity?.id)
    }

    @Test
    fun `should return existing URL for duplicate short code`() {
        val url = "https://example.com"
        val firstResponse =
            urlService.shortenUrl(
                originalUrl = url,
            )

        val secondResponse =
            urlService.shortenUrl(
                originalUrl = url,
            )

        assertEquals(firstResponse.token, secondResponse.token)
        assertEquals(firstResponse.id, secondResponse.id)
        assertEquals(firstResponse.originalUrl, secondResponse.originalUrl)
        assertEquals("https://example.com", firstResponse.originalUrl)

        val entities = urlMappingRepository.findAll()
        assertEquals(1, entities.size)
    }

    @Test
    fun `should retrieve original URL for valid short code`() {
        val token = "fXkwN6B"
        val originalUrl = "https://example.com"
        val urlEntity =
            UrlMapping(
                originalUrl = originalUrl,
                token = token,
            )
        urlMappingRepository.save(urlEntity)

        val result =
            urlService.getOriginalUrl(
                token = token,
            )

        assertEquals(originalUrl, result)
    }

    @Test
    fun `should throw UrlNotFoundException for invalid short code`() {
        val invalidToken = "invalid"

        assertThrows<UrlMappingNotFoundException> {
            urlService.getOriginalUrl(
                token = invalidToken,
            )
        }
    }
}
