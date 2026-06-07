package labs.apistarter.service.extrernal.v1

import labs.apistarter.service.extrernal.ExternalApiCaller
import labs.apistarter.service.extrernal.WatcherStatusResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class ExternalApiCallerImpl(
    @Value("\${watcher.api.url.v1}") private val watcherUrl: String
) : ExternalApiCaller {

    private val client = RestClient.create(watcherUrl)

    override fun callStart(): WatcherStatusResponse {
        return client.post()
            .uri("/start")
            .retrieve()
            .body(WatcherStatusResponse::class.java)!!
    }

    override fun callStop(): WatcherStatusResponse {
        return client.post()
            .uri("/stop")
            .retrieve()
            .body(WatcherStatusResponse::class.java)!!
    }

    override fun callStatus(): WatcherStatusResponse {
        return client.get()
            .uri("/status")
            .retrieve()
            .body(WatcherStatusResponse::class.java)!!
    }
}