package labs.edgeservice.service.token

interface TokenProvider {
    fun compareToken(token: String): Boolean
}