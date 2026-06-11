package labs.apistarter

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(properties = [
    "APP_KEY=test-key",
    "WATCHER_WEBHOOK_KEY=test-webhook-key",
    "SMTP_USER=test@gmail.com",
    "SMTP_PASSWORD=test",
    "SMTP_TO=test@gmail.com",
    "PORT_STARTER=8080",
    "HOST_STARTER=localhost",
    "WATCHER_HOST=localhost",
    "WATCHER_PORT=8081"
])
class ApiStarterApplicationTests {

    @Test
    fun contextLoads() {
    }

}
