package labs.edgeservice.dto

data class OutBoundStatusDto(
    val status: String,
    val description: String,
    val pid: Int? = null
)
