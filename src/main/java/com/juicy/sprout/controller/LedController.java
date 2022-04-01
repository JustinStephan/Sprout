package com.juicy.sprout.controller;

import com.pi4j.io.gpio.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@EnableScheduling
@RestController
public class LedController {
//    private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static GpioPinDigitalOutput pin;

    @Scheduled(cron = "* * * * * *")
    @RequestMapping("/led")
    public String toggleLed(){

        if (pin == null) {
            GpioController gpio = GpioFactory.getInstance();
            pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25,"MyLED", PinState.LOW);
        }

        pin.toggle();

        if(pin.getState().isHigh()){
            System.out.println("tic");
            return "ON";
        } else {
            System.out.println("toc");
            return "OFF";
        }
    }
}
