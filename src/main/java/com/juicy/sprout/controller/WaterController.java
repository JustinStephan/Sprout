package com.juicy.sprout.controller;

import com.juicy.sprout.repo.LogRepo;
import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WaterController {
    @Autowired
    private LogRepo logRepo;

    private static GpioPinDigitalInput pin;

    @RequestMapping("/water")
    public String getWaterLevels() {

        if (pin == null) {
            GpioController gpio = GpioFactory.getInstance();
            pin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_26,
                    "waterLevel1",
                    PinPullResistance.PULL_DOWN);
        }
        System.out.println(pin.getState().toString());

        if (pin.isHigh()) {
            return "PIN HIGH";
        } else {
            return "PIN LOW";
        }
    }

    @Scheduled(cron = "0 * * * * *")
    @RequestMapping("h20")
    public String waterIsWet() {
        return getWaterLevels();
    }
}
