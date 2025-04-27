package dkb.cf.urlshortener.infrastructure.services

import dkb.cf.urlshortener.domain.boundaries.output.TokenGeneratorService
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

class TokenGeneratorServiceImpl : TokenGeneratorService {
    override fun generateToken(
        originalUrl: String,
        length: Int,
    ): String {
        val md5Hash = getMd5Hash(originalUrl)
        return Base64
            .getUrlEncoder()
            .withoutPadding()
            .encodeToString(md5Hash.toByteArray(Charsets.UTF_8))
            .substring(0, length - 1)
    }

    private fun getMd5Hash(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}
