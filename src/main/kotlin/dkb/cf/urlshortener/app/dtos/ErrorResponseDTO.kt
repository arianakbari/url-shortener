package dkb.cf.urlshortener.app.dtos

data class ErrorResponseDTO(
    val message: String?,
    val status: Int,
)
