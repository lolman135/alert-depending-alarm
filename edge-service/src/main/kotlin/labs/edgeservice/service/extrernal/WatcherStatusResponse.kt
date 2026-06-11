package labs.edgeservice.service.extrernal

data class WatcherStatusResponse(
    val status: String,
    val description: String,
    val pid: Int?
)
