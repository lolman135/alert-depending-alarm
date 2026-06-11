package labs.edgeservice.usecase

import labs.edgeservice.service.extrernal.ExternalApiCaller
import labs.edgeservice.usecase.exception.ResourceUnavailableException
import org.springframework.stereotype.Component
import org.springframework.web.client.ResourceAccessException

@Component
class CheckExternalApiHealthUseCase(
    private val caller: ExternalApiCaller
) : UseCase<Unit, String> {

    override fun execute(inboundCommand: Unit): String {
        return try {
            caller.checkHealth()
        } catch (_: ResourceAccessException){
            throw ResourceUnavailableException("Watcher")
        }
    }
}