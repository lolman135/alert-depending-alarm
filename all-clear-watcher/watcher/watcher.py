import argparse, time

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--receiver-url", required=True)
    parser.add_argument("--api-key", required=True)
    args = parser.parse_args()

    alerts_api_key = args.api_key
    receiver_url = args.receiver_url

    while True:
        print("Hello, world")
        time.sleep(5)
