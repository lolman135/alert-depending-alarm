package labs.apistarter.web

import labs.apistarter.service.health.HealthcheckService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/healthcheck")
class HealthcheckController(private val healthcheckService: HealthcheckService) {

    @GetMapping
    fun getHealth(): ResponseEntity<String> = ResponseEntity.ok(healthcheckService.check())
}