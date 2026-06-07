import sys
from pathlib import Path

BASE_DIR = Path(__file__).parent
sys.path.append(str(BASE_DIR))

import uvicorn
from fastapi import FastAPI
from settings import settings
from v1.routers import router as v1_router

app = FastAPI(
    title="Air Raid Alert Watcher API",
    version="1.0.0"
)

app.include_router(v1_router, prefix="/api/v1", tags=["v1"])

@app.get("/")
async def root():
    return {"message": "Welcome to Alert Watcher API. Use /api/v1/docs for Swagger"}

if __name__ == "__main__":
    uvicorn.run(app, host=settings.host, port=settings.port)