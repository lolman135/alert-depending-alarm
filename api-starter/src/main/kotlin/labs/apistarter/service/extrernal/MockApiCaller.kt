package labs.apistarter.service.extrernal

import org.springframework.stereotype.Service

@Service
class MockApiCaller : ExternalApiCaller {
    override fun callStart() {
        println("Request has sent to start watching all-clear")
    }

    override fun callStop() {
        println("Watcher stopped manually")
    }
}