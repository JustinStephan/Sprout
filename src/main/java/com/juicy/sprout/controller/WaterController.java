package com.juicy.sprout.controller;

import com.juicy.sprout.repo.LogRepo;
import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WaterController {
    @Autowired
    private LogRepo logRepo;

    private static GpioPinDigitalOutput pin;

    @RequestMapping("/water")
    public String getWaterLevels() {

        if (pin == null) {
            GpioController gpio = GpioFactory.getInstance();
            // pin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_12,"waterLevel1", PinState.LOW);
            Gpio.pinMode(12, Gpio.INPUT);

            int inD = Gpio.digitalRead(12);
            int inA = Gpio.analogRead(12);

            return "Pin 12 digital input: " + inD + " | analog input: " + inA;
        }
        return "No value";
    }
}
