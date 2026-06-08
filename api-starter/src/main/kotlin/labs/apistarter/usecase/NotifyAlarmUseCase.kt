package labs.apistarter.usecase

import labs.apistarter.service.notification.NotificationService
import labs.apistarter.usecase.info.NotificationStatusInfo
import org.springframework.stereotype.Component

@Component
class NotifyAlarmUseCase(private val notificationService: NotificationService) : UseCase<Unit, NotificationStatusInfo> {

    override fun execute(inboundCommand: Unit): NotificationStatusInfo {
        suspend { notificationService.notifyAllClear() }
        return NotificationStatusInfo("sent", "Message sent successfully")
    }
}