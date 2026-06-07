from pathlib import Path

from pydantic_settings import BaseSettings, SettingsConfigDict

class Settings(BaseSettings):
    host: str = "localhost"
    port: int = 5000

    model_config = SettingsConfigDict(
        env_file=str(Path(__file__).parent / "../../.env"),
        env_file_encoding="utf-8",
        env_prefix="WATCHER_",
        extra="ignore"
    )

settings = Settings()