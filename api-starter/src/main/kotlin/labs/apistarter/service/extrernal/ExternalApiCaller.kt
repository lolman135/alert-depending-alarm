package labs.apistarter.service.extrernal

interface ExternalApiCaller {
    fun callStart(): WatcherStatusResponse
    fun callStop(): WatcherStatusResponse
    fun callStatus(): WatcherStatusResponse
}