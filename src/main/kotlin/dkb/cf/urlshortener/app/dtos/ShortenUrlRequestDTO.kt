package dkb.cf.urlshortener.app.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.URL

data class ShortenUrlRequestDTO(
    @field:NotBlank(message = "URL cannot be empty or null")
    @field:NotNull(message = "URL cannot be empty or null")
    @field:URL(message = "URL must be a valid URL (e.g., https://example.com)")
    val originalUrl: String,
)
