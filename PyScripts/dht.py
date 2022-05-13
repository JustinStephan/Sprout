# Libraries
import Adafruit_DHT as dht
from time import sleep
import sys

# Set DATA pin
pin = sys.argv[1]

# Read Temp and Hum from DHT22
h, t = dht.read_retry(dht.DHT22, pin)
# Print Temperature and Humidity on Shell window
print(h, ":", t)

