import certifi, os

os.environ["SSL_CERT_FILE"] = certifi.where()
os.environ["WEBSOCKET_CLIENT_CA_BUNDLE"] = certifi.where()

import sys, threading, asyncio
from functional.interrupts import WatcherFinishWorkInterrupt
from functional.watcher import do_work

def watch_parent_death():
    try:
        sys.stdin.read()
    except Exception:
        pass

    print("\n[Watcher] Parent process died. Committing seppuku...")
    os._exit(0)

if __name__ == "__main__":
    threading.Thread(target=watch_parent_death, daemon=True).start()

    print("[Watcher] Started successfully.")
    try:
        asyncio.run(do_work())
    except KeyboardInterrupt:
        print("[Watcher] Stopped by user.")
    except WatcherFinishWorkInterrupt:
        print("[Watcher] Finished work.")