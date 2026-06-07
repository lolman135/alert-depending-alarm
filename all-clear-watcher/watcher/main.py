import sys, threading, os

from watcher import main

# Parents process lifecycle monitoring
def watch_parent_death():
    try:
        sys.stdin.read()
    except Exception:
        pass

    print("\n[Watcher] Parent process (api.py) died! Committing seppuku...")
    os._exit(0)

threading.Thread(target=watch_parent_death, daemon=True).start()


print("[Watcher] Started successfully. Waiting for tasks...")
try:
    main()
except KeyboardInterrupt:
    print("[Watcher] Stopped by user.")