# Libraries
import Adafruit_DHT as dht
from time import sleep

# Set DATA pin
DHT = 4


def getTempAndHumid():
	h, t = dht.read_retry(dht.DHT22, DHT)

	return h, t


def alwaysRead():
	while True:
		# Read Temp and Hum from DHT22
		h, t = getTempAndHumid()
		# Print Temperature and Humidity on Shell window
		print('Temp={0:0.1f}*C  Humidity={1:0.1f}%'.format(t, h))
		sleep(5)  # Wait 5 seconds and read again

if __name__ == "__main__":
	getTempAndHumid()