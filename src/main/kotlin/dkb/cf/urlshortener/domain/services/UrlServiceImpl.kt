package dkb.cf.urlshortener.domain.services

import dkb.cf.urlshortener.domain.boundaries.input.UrlService
import dkb.cf.urlshortener.domain.boundaries.output.TokenGeneratorService
import dkb.cf.urlshortener.domain.boundaries.output.UrlMappingRepository
import dkb.cf.urlshortener.domain.entities.UrlMapping
import dkb.cf.urlshortener.domain.exceptions.UrlMappingNotFoundException

class UrlServiceImpl(
    private val urlMappingRepository: UrlMappingRepository,
    private val tokenGeneratorService: TokenGeneratorService,
) : UrlService {
    override fun shortenUrl(originalUrl: String): UrlMapping {
        val token = tokenGeneratorService.generateToken(originalUrl = originalUrl)

        return urlMappingRepository.findByToken(token) ?: run {
            urlMappingRepository.save(
                urlMapping = UrlMapping(originalUrl = originalUrl, token = token),
            )
        }
    }

    override fun getOriginalUrl(token: String): String {
        val mapping =
            urlMappingRepository.findByToken(token) ?: run {
                throw UrlMappingNotFoundException(message = "Wrong token!")
            }

        return mapping.originalUrl
    }
}
