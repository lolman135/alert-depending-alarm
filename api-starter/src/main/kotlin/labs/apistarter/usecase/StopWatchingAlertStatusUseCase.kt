package labs.apistarter.usecase

import labs.apistarter.service.extrernal.ExternalApiCaller
import org.springframework.stereotype.Service

@Service
class StopWatchingAlertStatusUseCase(private val caller: ExternalApiCaller) : UseCase<Unit, Unit> {
    override fun execute(inboundCommand: Unit): Unit {
        caller.callStop()
    }
}