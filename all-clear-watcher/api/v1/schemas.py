from pydantic import BaseModel

class StatusResponse(BaseModel):
    status: str
    description: str
    pid: int | None = None