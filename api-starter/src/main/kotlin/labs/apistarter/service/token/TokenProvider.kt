package labs.apistarter.service.token

interface TokenProvider {
    fun compareToken(token: String): Boolean
}