import asyncio

compare_holder = [None, None]

async def detect(token: str, region_uid: int, get_status) -> bool:
    first = compare_holder[0] if compare_holder[0] is not None else await get_status(token, region_uid)
    compare_holder[0] = first
    log1 = f"[Detector]: previous state: {first}"
    print(log1)

    await asyncio.sleep(10)

    second = await get_status(token, region_uid)
    compare_holder[1] = second
    log2 = f"[Detector]: current state: {second}"
    print(log2)

    if first == "active" and second == "no_alert":
        log3 = f"[Detector]: congrats!! all-clear, ready for alarm to wake up"
        print(log3)
        return True
    else:
        log3 = f"[Detector]: No all-clear. Going to retry after 30 sec of waiting"
        print(log3)
        current = compare_holder[1]
        compare_holder[0] = current
        compare_holder[1] = None
        return False