package dkb.cf.urlshortener.infrastructure.persistence.repositories

import dkb.cf.urlshortener.infrastructure.persistence.models.UrlMapping
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UrlMappingMongoRepository : MongoRepository<UrlMapping, UUID> {
    fun findByToken(token: String): UrlMapping?
}
