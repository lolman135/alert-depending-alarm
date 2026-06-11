package labs.edgeservice.usecase

import kotlinx.coroutines.runBlocking
import labs.edgeservice.service.notification.NotificationService
import labs.edgeservice.usecase.info.NotificationStatusInfo
import org.springframework.stereotype.Component

@Component
class NotifyAlarmUseCase(private val notificationService: NotificationService) : UseCase<Unit, NotificationStatusInfo> {

    override fun execute(inboundCommand: Unit): NotificationStatusInfo {
        runBlocking {
            notificationService.notifyAllClear()
        }
        return NotificationStatusInfo("sent", "Message sent successfully")
    }
}