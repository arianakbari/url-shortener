package dkb.cf.urlshortener.infrastructure.persistence.repositories

import dkb.cf.urlshortener.domain.boundaries.output.UrlMappingRepository
import dkb.cf.urlshortener.domain.entities.UrlMapping
import dkb.cf.urlshortener.infrastructure.persistence.models.UrlMapping as UrlMappingModel

class UrlMappingRepositoryImpl(
    private val mongoRepository: UrlMappingMongoRepository,
) : UrlMappingRepository {
    override fun findByToken(token: String): UrlMapping? {
        val urlMapping = mongoRepository.findByToken(token)

        return urlMapping?.let { UrlMappingModel.toDomain(urlMapping = it) }
    }

    override fun save(urlMappingEntity: UrlMapping): UrlMapping {
        val urlMapping =
            mongoRepository.save(
                UrlMappingModel(
                    id = urlMappingEntity.id,
                    originalUrl = urlMappingEntity.originalUrl,
                    token = urlMappingEntity.token,
                ),
            )

        return UrlMappingModel.toDomain(urlMapping = urlMapping)
    }
}
