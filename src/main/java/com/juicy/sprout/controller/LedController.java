package com.juicy.sprout.controller;

import com.pi4j.io.gpio.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LedController {

    private static GpioPinDigitalOutput pin;

    @RequestMapping("/led")
    public String toggleLed(){
        if (pin == null) {
            GpioController gpio = GpioFactory.getInstance();
            pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26,"MyLED", PinState.LOW);
        }

        pin.toggle();

        if(pin.getState().isHigh()){
            return "ON";
        } else {
            return "OFF";
        }
    }
}
