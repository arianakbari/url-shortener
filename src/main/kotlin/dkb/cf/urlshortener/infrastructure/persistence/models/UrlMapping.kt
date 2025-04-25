package dkb.cf.urlshortener.infrastructure.persistence.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import dkb.cf.urlshortener.domain.entities.UrlMapping as UrlMappingEntity

@Document(collection = "url_mappings")
class UrlMapping(
    @Id
    var id: String? = null,
    var originalUrl: String,
    @Indexed(unique = true)
    var token: String,
) {
    companion object {
        fun toDomain(urlMapping: UrlMapping): UrlMappingEntity =
            UrlMappingEntity(
                id = urlMapping.id,
                originalUrl = urlMapping.originalUrl,
                token = urlMapping.token,
            )
    }
}
