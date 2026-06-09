package labs.apistarter.config

import labs.apistarter.security.CustomTokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
class SecurityConfig(private val tokenFilter: CustomTokenFilter) {
    
    // TODO: Create and add here webhook filter to process separate token specified only for 
    // running webhook that yanks email sender
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain{
        http{
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