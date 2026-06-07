import argparse
import asyncio
from alerts_in_ua import AsyncClient as AsyncAlertsClient
from .all_clear_detector import detect
from .interrupts import WatcherFinishWorkInterrupt

async def get_alert_status(token: str, region_uid: int):
    alerts_client = AsyncAlertsClient(token=token)
    statusObj = await alerts_client.get_air_raid_alert_status(region_uid)
    return statusObj.status


status_stack = ["no_alert", "active", "active", "no_alert", "no_alert"]

async def mock_get_alert_status(token: str, region_uid: int):
    return status_stack.pop()


async def do_work(time_check_flag: bool, test_check_flag: bool):
    # TODO: delete after implementing switching mode
    print(f"Time check flag: {time_check_flag}")
    parser = argparse.ArgumentParser()
    parser.add_argument("--receiver-url", required=True)
    parser.add_argument("--api-key", required=True)
    args = parser.parse_args()

    alerts_api_key = args.api_key
    receiver_url = args.receiver_url
    region_uid = 31
    flag = False

    while not flag:
        try:
            alert_status_provider =  mock_get_alert_status if time_check_flag else get_alert_status
            flag = await detect(token=alerts_api_key, region_uid=region_uid, get_status=alert_status_provider)
            await asyncio.sleep(30)
        except Exception:
            log = f"[Watcher]: failed with some error"
            print(log)

    raise WatcherFinishWorkInterrupt()
