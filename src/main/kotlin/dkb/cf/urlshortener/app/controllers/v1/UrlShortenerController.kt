package dkb.cf.urlshortener.app.controllers.v1

import dkb.cf.urlshortener.app.dtos.ErrorResponseDTO
import dkb.cf.urlshortener.app.dtos.GetOriginalUrlResponseDTO
import dkb.cf.urlshortener.app.dtos.ShortenUrlRequestDTO
import dkb.cf.urlshortener.app.dtos.ShortenUrlResponseDTO
import dkb.cf.urlshortener.domain.boundaries.input.UrlService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "URL Shortener", description = "API for shortening and retrieving original URLs")
@RestController
@RequestMapping("/api/v1/urls")
class UrlShortenerController {
    @Autowired
    private lateinit var service: UrlService

    @Operation(summary = "Shorten a URL", description = "Creates a token for a given original URL")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Token created successfully",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ShortenUrlResponseDTO::class),
                ),
            ],
        ),
        ApiResponse(responseCode = "400", description = "Invalid URL format", content = [Content()]),
    )
    @PostMapping
    fun shortenUrl(
        @Valid @RequestBody request: ShortenUrlRequestDTO,
    ): ResponseEntity<ShortenUrlResponseDTO> =
        ResponseEntity.ok(
            ShortenUrlResponseDTO.fromEntity(
                entity = service.shortenUrl(originalUrl = request.originalUrl),
            ),
        )

    @Operation(summary = "Retrieve original URL", description = "Retrieves the original URL associated with the token")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Retrieve the original URL",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = GetOriginalUrlResponseDTO::class),
                ),
            ],
        ),
        ApiResponse(
            responseCode = "404",
            description = "Url mapping not found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponseDTO::class),
                ),
            ],
        ),
    )
    @GetMapping("/{token}")
    fun getOriginalUrl(
        @PathVariable("token") token: String,
    ): ResponseEntity<GetOriginalUrlResponseDTO> =
        ResponseEntity.ok(
            GetOriginalUrlResponseDTO(
                originalUrl = service.getOriginalUrl(token),
            ),
        )
}
