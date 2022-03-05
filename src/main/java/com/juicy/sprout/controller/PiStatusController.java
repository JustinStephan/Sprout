package com.juicy.sprout.controller;

import ch.qos.logback.core.util.TimeUtil;
import com.juicy.sprout.utils.TimeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PiStatusController {


    @RequestMapping("/status")
    public String piStatus () {
        return "System time: "
                + TimeUtils.toTimeStamp(System.currentTimeMillis())
                + " Server Uptime: " +
                TimeUtils.printServiceUpTime();
    }
}
