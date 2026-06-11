package labs.edgeservice.usecase.exception

class ResourceUnavailableException(resource: String) : Exception("$resource service is unavailable") {
}