# Libraries
import Adafruit_DHT as dht
from time import sleep

# Set DATA pin
pin = 19

#
# def getTempAndHumid():
# 	h, t = dht.read_retry(dht.DHT22, pin)
#
# 	return h, t
#
#
# def alwaysRead():

# Read Temp and Hum from DHT22
h, t = dht.read_retry(dht.DHT22, pin)
# Print Temperature and Humidity on Shell window
print('Temp={0:0.1f}*C  Humidity={1:0.1f}%'.format(t, h))

