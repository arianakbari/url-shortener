package dkb.cf.urlshortener.domain.boundaries.output

interface TokenGeneratorService {
    fun generateToken(
        originalUrl: String,
        length: Int,
    ): String
}
