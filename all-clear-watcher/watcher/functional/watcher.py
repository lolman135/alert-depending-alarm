import argparse
from .all_clear_detector import detect
from .interrupts import WatcherFinishWorkInterrupt


def main():
    region_uid = 31

    parser = argparse.ArgumentParser()
    parser.add_argument("--receiver-url", required=True)
    parser.add_argument("--api-key", required=True)
    args = parser.parse_args()

    alerts_api_key = args.api_key
    receiver_url = args.receiver_url

    sendFlag = detect(token=alerts_api_key, region_uid=region_uid)

    # Finishing work
    raise WatcherFinishWorkInterrupt()
