package labs.apistarter.web

import jakarta.servlet.http.HttpServletRequest
import labs.apistarter.usecase.exception.NotificationFailedException
import labs.apistarter.usecase.exception.ResourceUnavailableException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import java.net.URI

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    private val log = LoggerFactory.getLogger(this::class.java)

    // later will add more exceptions
    @ExceptionHandler(ResourceUnavailableException::class)
    fun handleResourceNotAvailable(ex: ResourceUnavailableException, request: HttpServletRequest): ProblemDetail {
        val problem =
            ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, ex.message ?: "Service Unavailable")
        problem.title = "Service Unavailable"
        problem.instance = URI.create(request.requestURI)
        log.warn(ex.message)
        return problem
    }

    @ExceptionHandler(NotificationFailedException::class)
    fun handleNotificationFailed(ex: NotificationFailedException, request: HttpServletRequest): ProblemDetail {
        val problem =
            ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, ex.message ?: "Service Unavailable")
        problem.title = "BAD GATEWAY"
        problem.instance = URI.create(request.requestURI)
        log.warn(ex.message)
        return problem
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotSupported(e: HttpRequestMethodNotSupportedException): ResponseEntity<Map<String, String>> {
        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(mapOf("error" to "Method '${e.method}' is not supported for this endpoint"))
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, request: HttpServletRequest): ProblemDetail {
        val problem =
            ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.message ?: "Internal Server Error")
        problem.title = "Internal Server Error"
        problem.instance = URI.create(request.requestURI)
        log.warn(ex.message)
        return problem
    }
}