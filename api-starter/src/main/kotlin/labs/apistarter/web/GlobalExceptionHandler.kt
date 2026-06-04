package labs.apistarter.web

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import java.net.URI

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    private val log = LoggerFactory.getLogger(this::class.java)

    // later will add more exceptions

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, request: HttpServletRequest): ProblemDetail {
        val problem =
            ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.message ?: "Internal Server Error")
        problem.title = "Internal Server Error"
        problem.type = URI.create("https://example.com/errors/internal-server-error")
        problem.instance = URI.create(request.requestURI)
        log.warn(ex.message)
        return problem
    }
}