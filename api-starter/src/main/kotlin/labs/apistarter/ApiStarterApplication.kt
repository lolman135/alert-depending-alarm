package labs.apistarter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiStarterApplication

fun main(args: Array<String>) {
    runApplication<ApiStarterApplication>(*args)
}
