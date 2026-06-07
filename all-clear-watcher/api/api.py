import signal, sys, subprocess
from pathlib import Path
sys.path.append(str(Path(__file__).parent))
from settings import settings
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()
process: subprocess.Popen | None = None

BASE_DIR = Path(__file__).parent
watcher_file = str(BASE_DIR / "../watcher/main.py")

class StatusResponse(BaseModel):
    status: str
    description: str
    pid: int | None = None

@app.post("/start", response_model=StatusResponse)
async def start():
    global process

    if process is not None and process.poll() is None:
        return StatusResponse(status="already_running", description="Watcher is already running.", pid=process.pid)

    process = subprocess.Popen(
        [
            "python", watcher_file,
            "--receiver-url", settings.receiver_url,
            "--api-key", settings.alerts_api_key
        ],
        stdin=subprocess.PIPE,
        start_new_session=True
    )

    return StatusResponse(
        status="started",
        description="Watcher started successfully.",
        pid=process.pid
    )


@app.post("/stop", response_model=StatusResponse)
async def stop():
    global process

    if process is None or process.poll() is not None:
        return StatusResponse(status="not_running", description="Watcher is already stopped.",)

    process.send_signal(signal.SIGTERM)
    try:
        process.wait(timeout=5)
    except subprocess.TimeoutExpired:
        process.kill()

    process = None
    return StatusResponse(status="stopped", description="Watcher stopped successfully.",)


@app.get("/status", response_model=StatusResponse)
async def status():
    if process is None or process.poll() is not None:
        return StatusResponse(status="stopped", description="Watcher is currently not running or stopped manually.")
    return StatusResponse(status="running", description="Watcher is currently running", pid=process.pid)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host=settings.host, port=settings.port)