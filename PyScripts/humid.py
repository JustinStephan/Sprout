# Libraries
import Adafruit_DHT as dht
import PINS

# Set DATA pin
pin = PINS.TEMPHUMID

# Read Temp and Hum from DHT22
h, t = dht.read_retry(dht.DHT22, pin)
# Print Temperature and Humidity on Shell window
print(h)

