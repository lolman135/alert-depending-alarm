package labs.edgeservice.service.extrernal

interface ExternalApiCaller {
    fun callStart(): WatcherStatusResponse
    fun callStop(): WatcherStatusResponse
    fun callStatus(): WatcherStatusResponse
    fun checkHealth(): String
}