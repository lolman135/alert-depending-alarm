package labs.edgeservice.service.token

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class BasicTokenProviderImpl(@Value($$"${app.key}") private val appKey: String) : TokenProvider {
    override fun compareToken(token: String) = token == appKey
}