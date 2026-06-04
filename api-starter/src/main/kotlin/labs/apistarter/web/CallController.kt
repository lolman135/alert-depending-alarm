package labs.apistarter.web

import labs.apistarter.dto.OutBoundStatusDto
import labs.apistarter.usecase.StopWatchingAlertStatusUseCase
import labs.apistarter.usecase.StratWatchingAlertStatusUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/watcher")
class CallController(
    private val stratWatchingAlertStatusUseCase: StratWatchingAlertStatusUseCase,
    private val stopWatchingAlertStatusUseCase: StopWatchingAlertStatusUseCase
) {

    @PostMapping("/start")
    fun start(): ResponseEntity<OutBoundStatusDto> {
        stratWatchingAlertStatusUseCase.execute(Unit)
        return ResponseEntity.ok(OutBoundStatusDto("Started", "Watcher started to monitor all-clear status",  200))
    }

    @PostMapping("/stop")
    fun stop(): ResponseEntity<OutBoundStatusDto> {
        stopWatchingAlertStatusUseCase.execute(Unit)
        return ResponseEntity.ok(OutBoundStatusDto("Stopped", "Watcher manually stopped by user", 200))
    }
}