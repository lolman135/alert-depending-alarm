package labs.apistarter.usecase

import labs.apistarter.service.extrernal.ExternalApiCaller
import org.springframework.stereotype.Service

@Service
class StratWatchingAlertStatusUseCase(private val caller: ExternalApiCaller) : UseCase<Unit, Unit> {
    override fun execute(inboundCommand: Unit): Unit {
        caller.callStart()
    }
}