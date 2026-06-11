package labs.edgeservice.service.health

import org.springframework.stereotype.Service

@Service
class HealthcheckServiceImpl : HealthcheckService {
    override fun check(): String {
        return "Everything is ok!"
    }
}