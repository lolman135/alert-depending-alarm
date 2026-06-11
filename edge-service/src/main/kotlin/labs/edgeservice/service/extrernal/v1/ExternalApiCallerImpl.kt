package labs.edgeservice.service.extrernal.v1

import labs.edgeservice.service.extrernal.ExternalApiCaller
import labs.edgeservice.service.extrernal.WatcherStatusResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class ExternalApiCallerImpl(
    @Value($$"${watcher.api.url.v1}") private val watcherUrl: String
) : ExternalApiCaller {

    private val client = RestClient.create(watcherUrl)

    override fun callStart(): WatcherStatusResponse {
        return client.post()
            .uri("/start")
            .retrieve()
            .body<WatcherStatusResponse>()!!
    }

    override fun callStop(): WatcherStatusResponse {
        return client.post()
            .uri("/stop")
            .retrieve()
            .body<WatcherStatusResponse>()!!
    }

    override fun callStatus(): WatcherStatusResponse {
        return client.get()
            .uri("/status")
            .retrieve()
            .body<WatcherStatusResponse>()!!
    }

    override fun checkHealth(): String {
        return client.get()
            .uri("/health")
            .retrieve()
            .body<String>()!!
    }
}