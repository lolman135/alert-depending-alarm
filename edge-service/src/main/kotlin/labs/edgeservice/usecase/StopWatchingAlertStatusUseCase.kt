package labs.edgeservice.usecase

import labs.edgeservice.service.extrernal.ExternalApiCaller
import labs.edgeservice.usecase.exception.ResourceUnavailableException
import labs.edgeservice.usecase.info.WatcherStatusInfo
import org.springframework.stereotype.Service
import org.springframework.web.client.ResourceAccessException

@Service
class StopWatchingAlertStatusUseCase(private val caller: ExternalApiCaller) : UseCase<Unit, WatcherStatusInfo> {
    override fun execute(inboundCommand: Unit): WatcherStatusInfo {
        try {
            val response = caller.callStop()
            return WatcherStatusInfo(response.status, response.description, response.pid)
        } catch (_: ResourceAccessException){
            throw ResourceUnavailableException("Watcher")
        }
    }
}