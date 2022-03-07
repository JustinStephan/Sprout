package com.juicy.sprout.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class TempHumidityController {
    private static String line;
    private static String[] data;
    static int humidity = 0;
    static int temperature = 0;

    private String getReading(String readingType) throws Exception {
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec("python PyScripts/" + readingType + ".py");
        BufferedReader br = new BufferedReader(new
                InputStreamReader(p.getInputStream()));
        if ((line = br.readLine()) != null) {
            if (!(line.contains("ERR_CRC") || line.contains("ERR_RNG"))) {
                return line;
            }
        }
        br.close();
        p.waitFor();

        return "Data Error";
    }

    @RequestMapping("/sensor/temp")
    public String getTemp() throws Exception{
        return "TEMP: " + getReading("temp") + " 'C";
    }

    @RequestMapping("/sensor/humid")
    public String getHumid() throws Exception{
        return "HUMIDITY: " + getReading("humid") + " %RH";
    }

    @RequestMapping("/sensor/test")
    public String getTest() throws Exception{
        String reading =  getReading("test");

        String[] arr = reading.split(":");
        return arr[1];
    }

    @RequestMapping("/sensor/th")
    public String getTempHumid() throws Exception {
        String reading = getReading("dht");
        String[] arr = reading.split(":");

        for (String s : arr){System.out.println(s);}

        return "HUMIDITY: " + arr[0] + "'C | TEMP: " + arr[3] + " %RH" ;
    }
}
