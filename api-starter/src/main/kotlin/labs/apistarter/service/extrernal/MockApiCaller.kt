package labs.apistarter.service.extrernal

@Ser
class MockApiCaller : ExternalApiCaller {
    override fun callStart() {
        println("Request has sent to start watching all-clear")
    }

    override fun callStop() {
        println("Watcher stopped manually")
    }
}