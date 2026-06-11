package labs.edgeservice.web.v1

import labs.edgeservice.dto.OutBoundStatusDto
import labs.edgeservice.usecase.GetStatusOfWatcherUseCase
import labs.edgeservice.usecase.StopWatchingAlertStatusUseCase
import labs.edgeservice.usecase.StratWatchingAlertStatusUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/watcher")
class CallController(
    private val stratWatchingAlertStatusUseCase: StratWatchingAlertStatusUseCase,
    private val stopWatchingAlertStatusUseCase: StopWatchingAlertStatusUseCase,
    private val getStatusOfWatcherUseCase: GetStatusOfWatcherUseCase
) {

    @PostMapping("/start")
    fun start(): ResponseEntity<OutBoundStatusDto> {
        val info = stratWatchingAlertStatusUseCase.execute(Unit)
        return ResponseEntity.ok(OutBoundStatusDto(info.status, info.description, info.pid))
    }

    @PostMapping("/stop")
    fun stop(): ResponseEntity<OutBoundStatusDto> {
        val info = stopWatchingAlertStatusUseCase.execute(Unit)
        return ResponseEntity.ok(OutBoundStatusDto(info.status, info.description, info.pid))
    }

    @GetMapping("/status")
    fun getStatus(): ResponseEntity<OutBoundStatusDto>{
        val info = getStatusOfWatcherUseCase.execute(Unit)
        return ResponseEntity.ok(OutBoundStatusDto(info.status, info.description, info.pid))
    }
}