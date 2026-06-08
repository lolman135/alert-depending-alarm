import argparse
import asyncio
from datetime import datetime, time
from alerts_in_ua import AsyncClient as AsyncAlertsClient
from .notifier import detect, notify
from .interrupts import WatcherFinishWorkInterrupt

async def get_alert_status(token: str, region_uid: int):
    alerts_client = AsyncAlertsClient(token=token)
    statusObj = await alerts_client.get_air_raid_alert_status(region_uid)
    return statusObj.status


status_stack = ["no_alert", "active", "active", "no_alert", "no_alert"]

async def mock_get_alert_status(token: str, region_uid: int):
    return status_stack.pop()

async def do_work(time_check_flag: bool, test_flag: bool):
    # TODO: delete after implementing switching mode
    print(f"Time check flag: {time_check_flag}")
    parser = argparse.ArgumentParser()
    parser.add_argument("--receiver-url", required=True)
    parser.add_argument("--api-key", required=True)
    parser.add_argument("--app-key", required=True)
    args = parser.parse_args()

    alerts_api_key = args.api_key
    receiver_url = args.receiver_url
    region_uid = 31
    flag = False
    correct_time_flag = False
    global_sleep = 1 if test_flag else 30
    local_sleep = 1 if test_flag else 8
    start_time = time(0, 0, 0)
    end_time = time(23, 59, 59)
    current_time = datetime.now().time()


    while not correct_time_flag:
        try:
            while not flag:
                alert_status_provider =  mock_get_alert_status if test_flag else get_alert_status
                flag = await detect(
                    token=alerts_api_key,
                    region_uid=region_uid,
                    get_status=alert_status_provider,
                    local_sleep=local_sleep
                )
                await asyncio.sleep(global_sleep)

            if not time_check_flag:
                correct_time_flag = True
                break

        except Exception:
                log = f"[Watcher]: failed with some error"
                print(log)

    await notify(start_time, end_time, current_time, receiver_url)

    raise WatcherFinishWorkInterrupt()
