package dkb.cf.urlshortener.app.services

import dkb.cf.urlshortener.domain.boundaries.output.ConfigService
import org.springframework.stereotype.Service

@Service
class ConfigServiceImpl : ConfigService {
    // Can be extracted into an environment variable
    override fun getTokenLength(): Int = 8
}
