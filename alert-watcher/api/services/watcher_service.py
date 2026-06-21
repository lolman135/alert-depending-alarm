import signal
import subprocess
from pathlib import Path
from api.settings import settings

BASE_DIR = Path(__file__).parent.parent
WATCHER_FILE = str(BASE_DIR.parent / "watcher" / "main.py")

class WatcherService:
    _instance = None

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super().__new__(cls)
            cls._instance.process = None
        return cls._instance

    def is_running(self) -> bool:
        return self.process is not None and self.process.poll() is None

    def get_pid(self) -> int | None:
        return self.process.pid if self.is_running() else None

    def start_watcher(self) -> int:
        if self.is_running():
            return self.process.pid

        import sys
        self.process = subprocess.Popen(
            [
                sys.executable, "-u", WATCHER_FILE,
                "--receiver-url", settings.receiver_url,
                "--api-key", settings.alerts_api_key,
                "--webhook-key", settings.webhook_key
            ],
            stdin=subprocess.PIPE,
            start_new_session=True
        )
        return self.process.pid

    def stop_watcher(self) -> bool:
        if not self.is_running():
            return False

        self.process.send_signal(signal.SIGTERM)
        try:
            self.process.wait(timeout=5)
        except subprocess.TimeoutExpired:
            self.process.kill()

        self.process = None
        return True

watcher_service = WatcherService()