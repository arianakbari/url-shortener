package dkb.cf.urlshortener.domain.boundaries.input

import dkb.cf.urlshortener.domain.entities.UrlMapping

interface UrlService {
    fun shortenUrl(originalUrl: String): UrlMapping

    fun getOriginalUrl(token: String): String
}
