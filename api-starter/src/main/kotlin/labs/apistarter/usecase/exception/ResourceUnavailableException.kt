package labs.apistarter.usecase.exception

class ResourceUnavailableException(resource: String) : Exception("$resource service is unavailable") {
}