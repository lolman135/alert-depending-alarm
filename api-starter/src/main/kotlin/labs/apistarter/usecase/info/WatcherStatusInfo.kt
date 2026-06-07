package labs.apistarter.usecase.info

data class WatcherStatusInfo(
    val status: String,
    val description: String,
    val pid: Int? = null
)