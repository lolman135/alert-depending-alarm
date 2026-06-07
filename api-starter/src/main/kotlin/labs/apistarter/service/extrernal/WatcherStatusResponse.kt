package labs.apistarter.service.extrernal

data class WatcherStatusResponse(
    val status: String,
    val description: String,
    val pid: Int?
)
