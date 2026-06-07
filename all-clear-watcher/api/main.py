import subprocess
import signal
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()
process: subprocess.Popen | None = None


class StatusResponse(BaseModel):
    status: str
    pid: int | None = None


@app.post("/start", response_model=StatusResponse)
async def start():
    global process

    if process is not None and process.poll() is None:
        return StatusResponse(status="already_running", pid=process.pid)

    process = subprocess.Popen(
        ["python3", "script.py"],
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE
    )

    return StatusResponse(status="started", pid=process.pid)


@app.post("/stop", response_model=StatusResponse)
async def stop():
    global process

    if process is None or process.poll() is not None:
        return StatusResponse(status="not_running")

    process.send_signal(signal.SIGTERM)
    try:
        process.wait(timeout=5)
    except subprocess.TimeoutExpired:
        process.kill()

    process = None
    return StatusResponse(status="stopped")


@app.get("/status", response_model=StatusResponse)
async def status():
    if process is None or process.poll() is not None:
        return StatusResponse(status="stopped")
    return StatusResponse(status="running", pid=process.pid)