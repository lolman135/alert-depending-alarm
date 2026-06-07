from operator import truediv

from alerts_in_ua import Client as AlertsClient
import time

def __get_alert_status(token: str, region_uid: int):
    alerts_client = AlertsClient(token=token)
    return alerts_client.get_air_raid_alert_status(region_uid)

def detect(token, region_uid):
    while True:
        allertStatus = __get_alert_status(token, region_uid)
        print(allertStatus)
        time.sleep(1)
        if True:
            break

    return True