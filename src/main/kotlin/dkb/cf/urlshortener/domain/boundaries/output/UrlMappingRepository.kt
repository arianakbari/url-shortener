package dkb.cf.urlshortener.domain.boundaries.output

import dkb.cf.urlshortener.domain.entities.UrlMapping

interface UrlMappingRepository {
    fun findByToken(token: String): UrlMapping?

    fun save(urlMapping: UrlMapping): UrlMapping
}
