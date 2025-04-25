package dkb.cf.urlshortener.domain.entities

data class UrlMapping(
    var id: String? = null,
    var token: String,
    var originalUrl: String,
)
