package labs.apistarter.config

import labs.apistarter.security.CustomTokenFilter
import labs.apistarter.security.WebhookTokenFilter
import labs.apistarter.service.token.TokenProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    @Value($$"${app.webhook.key}") private val webhookApiToken: String
) {

    @Bean
    @Order(1)
    fun webhookFilterChain(http: HttpSecurity): SecurityFilterChain{

        val tokenFilter = WebhookTokenFilter(webhookApiToken)

        http{
            securityMatcher("/api/v1/notify")
            csrf { disable() }
            cors { disable() }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(tokenFilter)
        }
        return http.build()
    }

    @Bean
    @Order(2)
    fun apiFilterChain(http: HttpSecurity): SecurityFilterChain{

        val tokenFilter = CustomTokenFilter(tokenProvider)

        http{
            securityMatcher("/**")
            csrf { disable() }
            cors { disable() }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(tokenFilter)
        }
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { throw UsernameNotFoundException("No users") }
    }
}