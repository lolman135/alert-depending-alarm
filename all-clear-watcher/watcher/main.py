import sys, threading, os

from functional.interrupts import WatcherFinishWorkInterrupt
from functional.watcher import main


# Parents process lifecycle monitoring
def watch_parent_death():
    try:
        sys.stdin.read()
    except Exception:
        pass

    print("\n[Watcher] Parent process died.")
    os._exit(0)

threading.Thread(target=watch_parent_death, daemon=True).start()


print("[Watcher] Started successfully.")
try:
    main()
except KeyboardInterrupt:
    print("[Watcher] Stopped by user.")
except WatcherFinishWorkInterrupt:
    print("[Watcher] Finished work.")