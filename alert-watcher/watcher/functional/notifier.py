import asyncio, aiohttp
from datetime import datetime

compare_holder = [None, None]

async def detect(token: str, region_uid: int, get_status, local_sleep: int, global_sleep: int) -> bool:
    first = compare_holder[0] if compare_holder[0] is not None else await get_status(token, region_uid)
    compare_holder[0] = first
    log1 = f"[Detector]:[{datetime.now().strftime("%H:%M:%S %d.%m.%Y")}]: previous state: {first}"
    print(log1)

    await asyncio.sleep(local_sleep)

    second = await get_status(token, region_uid)
    compare_holder[1] = second
    now = datetime.now().strftime("%H:%M:%S %d.%m.%Y")
    log2 = f"[Detector]:[{datetime.now().strftime("%H:%M:%S %d.%m.%Y")}]: current state: {second}"
    print(log2)

    if first == "active" and second == "no_alert":
        log3 = f"[Detector]:[{datetime.now().strftime("%H:%M:%S %d.%m.%Y")}]: congrats!! all-clear, ready for alarm to wake up"
        print(log3)
        return True
    else:
        log3 = f"[Detector]:[{datetime.now().strftime("%H:%M:%S %d.%m.%Y")}]: No all-clear. Going to retry after {global_sleep} sec of waiting"
        print(log3)
        current = compare_holder[1]
        compare_holder[0] = current
        compare_holder[1] = None
        return False


async def notify(url: str, webhook_key: str):
    try:
        headers = {"Authorization": f"Bearer {webhook_key}"}
        async with aiohttp.ClientSession() as session:
            async with session.post(url, headers=headers) as response:
                status = response.status
                body = await response.text()

                if status == 200:
                    print(f"[Notifier]:[{datetime.now().strftime("%H:%M:%S %d.%m.%Y")}]: Success! Body: {body}")
                else:
                    print(f"[Notifier]:[{datetime.now().strftime("%H:%M:%S %d.%m.%Y")}]: Warning! Error: {body}")

    except Exception as e:
        print(f"[Notifier]:[{datetime.now().strftime("%H:%M:%S %d.%m.%Y")}]: Network or connection error occurred: {e}")
