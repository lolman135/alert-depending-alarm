package labs.edgeservice.service.health

interface HealthcheckService {
    fun check(): String
}