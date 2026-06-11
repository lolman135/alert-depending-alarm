from fastapi import APIRouter
from api.v1.schemas import StatusResponse
from api.services.watcher_service import watcher_service

router = APIRouter()


@router.post("/start", response_model=StatusResponse)
async def start():
    if watcher_service.is_running():
        return StatusResponse(
            status="already_running",
            description="Watcher is already running.",
            pid=watcher_service.get_pid()
        )

    pid = watcher_service.start_watcher()
    return StatusResponse(
        status="started",
        description="Watcher started successfully.",
        pid=pid
    )


@router.post("/stop", response_model=StatusResponse)
async def stop():
    stopped = watcher_service.stop_watcher()
    if not stopped:
        return StatusResponse(status="not_running", description="Watcher is already stopped.")

    return StatusResponse(status="stopped", description="Watcher stopped successfully.")


@router.get("/status", response_model=StatusResponse)
async def status():
    if not watcher_service.is_running():
        return StatusResponse(status="stopped", description="Watcher is currently not running or stopped manually.")

    return StatusResponse(
        status="running",
        description="Watcher is currently running",
        pid=watcher_service.get_pid()
    )

@router.get(path="/health", response_model=str)
async def health():
    return "Everything is ok! Watcher API is running"