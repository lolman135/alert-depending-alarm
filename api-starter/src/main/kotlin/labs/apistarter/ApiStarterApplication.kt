package labs.apistarter

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiStarterApplication

fun main(args: Array<String>) {
    val dotenv = dotenv {
        directory = "../"
        ignoreIfMissing = true
    }
    
    dotenv.entries().forEach { entry ->
        System.setProperty(entry.key, entry.value)
    }
    runApplication<ApiStarterApplication>(*args)
}
