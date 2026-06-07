import certifi, os, yaml

os.environ["SSL_CERT_FILE"] = certifi.where()
os.environ["WEBSOCKET_CLIENT_CA_BUNDLE"] = certifi.where()

import sys, threading, asyncio
from functional.interrupts import WatcherFinishWorkInterrupt
from functional.watcher import do_work

# preparing functions
def watch_parent_death():
    try:
        sys.stdin.read()
    except Exception:
        pass

    print("\n[Watcher] Parent process died. Committing seppuku...")
    os._exit(0)

def parse_config():
    with open("config.yaml", "r", encoding="utf-8") as config_file:
        return yaml.safe_load(config_file)

def get_time_check_flag(config):
    return bool(config["check-flag"]["time"])

def get_test_check_flag(config):
    return bool(config["check-flag"]["test"])

# run
if __name__ == "__main__":
    threading.Thread(target=watch_parent_death, daemon=True).start()

    print("[Watcher] Started successfully.")
    try:
        asyncio.run(do_work(get_time_check_flag(parse_config()), get_test_check_flag(parse_config())))
    except KeyboardInterrupt:
        print("[Watcher] Stopped by user.")
    except WatcherFinishWorkInterrupt:
        print("[Watcher] Finished work.")