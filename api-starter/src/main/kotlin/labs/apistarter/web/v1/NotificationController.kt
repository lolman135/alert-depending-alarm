package labs.apistarter.web.v1

import labs.apistarter.dto.OutBoundStatusDto
import labs.apistarter.usecase.NotifyAlarmUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/notify")
class NotificationController(
    private val useCase: NotifyAlarmUseCase
) {

    @PostMapping
    fun notify(): ResponseEntity<OutBoundStatusDto>{
        val statusInfo = useCase.execute(Unit)
        return ResponseEntity.ok(OutBoundStatusDto(statusInfo.status, statusInfo.message))
    }
}