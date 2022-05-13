# Libraries
import Adafruit_DHT as dht
from time import sleep

# Set DATA pin
pin = int(sys.argv[1])

# Read Temp and Hum from DHT22
h, t = dht.read_retry(dht.DHT22, pin)
# Print Temperature and Humidity on Shell window
print(h, ":", t)

