package dkb.cf.urlshortener.utils

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration

@Testcontainers
abstract class MongoDBTestContainer {
    companion object {
        @Container
        private val mongoDBContainer =
            MongoDBContainer(
                "mongo:7.0",
            ).withExposedPorts(27017).withStartupTimeout(Duration.ofSeconds(60))

        @DynamicPropertySource
        @JvmStatic
        fun setMongoDbProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri") { mongoDBContainer.replicaSetUrl }
        }
    }
}
