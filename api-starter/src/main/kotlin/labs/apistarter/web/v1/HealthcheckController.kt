package labs.apistarter.web.v1

import labs.apistarter.service.health.HealthcheckService
import labs.apistarter.usecase.CheckExternalApiHealthUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/healthcheck")
class HealthcheckController(
    private val healthcheckService: HealthcheckService,
    private val checkExternalApiHealthUseCase: CheckExternalApiHealthUseCase
) {

    @GetMapping
    fun getHealth(): ResponseEntity<String> = ResponseEntity.ok(healthcheckService.check())

    @GetMapping("/external")
    fun getHealthExternal(): ResponseEntity<String> = ResponseEntity.ok(checkExternalApiHealthUseCase.execute(Unit))
}