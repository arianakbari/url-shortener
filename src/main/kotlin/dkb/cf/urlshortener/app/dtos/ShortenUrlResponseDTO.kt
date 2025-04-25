package dkb.cf.urlshortener.app.dtos

import dkb.cf.urlshortener.domain.entities.UrlMapping

data class ShortenUrlResponseDTO(
    val id: String,
    val originalUrl: String,
    val token: String,
) {
    companion object {
        fun fromEntity(entity: UrlMapping): ShortenUrlResponseDTO =
            ShortenUrlResponseDTO(
                id = entity.id!!,
                originalUrl = entity.originalUrl,
                token = entity.token,
            )
    }
}
