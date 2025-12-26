package devcavin.pesacore.exception

import devcavin.pesacore.dto.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalExceptionHandler {
    // Account not found
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(e: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            message = e.message
        ), HttpStatus.NOT_FOUND)
    }

    // Insufficient balance
    @ExceptionHandler(InsufficientBalanceException::class)
    fun handleInsufficientBalance(e: InsufficientBalanceException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = HttpStatus.BAD_REQUEST.reasonPhrase,
                message = e.message
            ), HttpStatus.BAD_REQUEST
        )
    }

    // Type mismatch errors
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleTypeMismatchErrors(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = HttpStatus.BAD_REQUEST.reasonPhrase,
                message = "Type of ${e.requiredType?.simpleName} was expected"
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    // Duplicate field
    @ExceptionHandler(DuplicateFieldException::class)
    fun handleDuplicateField(e: DuplicateFieldException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ErrorResponse(
                status = HttpStatus.CONFLICT.value(),
                error = HttpStatus.CONFLICT.reasonPhrase,
                message = e.message
            )
        )
    }

    // Fallback handler
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        e.printStackTrace()
        return ResponseEntity(
            ErrorResponse(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                error = "Internal Server Error",
                message = "Something went wrong, please try again later"
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}

class ResourceNotFoundException(message: String) : RuntimeException(message)
class InsufficientBalanceException(message: String) : RuntimeException(message)
class DuplicateFieldException(message: String) : RuntimeException(message)