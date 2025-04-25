package dkb.cf.urlshortener.app.configurations

import dkb.cf.urlshortener.app.dtos.ErrorResponseDTO
import dkb.cf.urlshortener.domain.exceptions.UrlMappingNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler
    fun handleUrlMappingNotFoundException(ex: UrlMappingNotFoundException): ResponseEntity<ErrorResponseDTO> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponseDTO(
                message = ex.message,
                status = HttpStatus.NOT_FOUND.value(),
            ),
        )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponseDTO> {
        val errors =
            ex.bindingResult.fieldErrors.map { error ->
                error.defaultMessage ?: "Invalid value"
            }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponseDTO(
                message = errors.joinToString(", "),
                status = HttpStatus.BAD_REQUEST.value(),
            ),
        )
    }
}
