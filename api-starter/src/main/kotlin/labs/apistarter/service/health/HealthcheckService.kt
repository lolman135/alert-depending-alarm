package labs.apistarter.service.health

interface HealthcheckService {
    fun check(): String
}