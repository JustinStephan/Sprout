package com.juicy.sprout.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
public class TempHumidityController {
    private static String line;
    private static String[] data;
    static int humidity = 0;
    static int temperature = 0;

    @RequestMapping("/temp")
    public void getTemp() throws Exception{
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec("python /projects/PythonScripts/dht.py");
        BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
        if ((line = bri.readLine()) != null) {
            if (!(line.contains("ERR_CRC") || line.contains("ERR_RNG"))) {

                data = line.split("ABC");
                System.out.println(data[0]);
                temperature = Integer.parseInt(data[0]);
                humidity = Integer.parseInt(data[1]);
            } else
                System.out.println("Data Error");
        }

        bri.close();
        p.waitFor();
        System.out.println("Temperature is : " + temperature + " 'C Humidity is :" + humidity + " %RH");
        System.out.println("Done.");

    }
}
