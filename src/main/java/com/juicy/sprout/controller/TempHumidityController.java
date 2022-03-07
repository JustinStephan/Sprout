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
        return getReading("temp") + " 'C";
    }

    @RequestMapping("/sensor/humid")
    public String getHumid() throws Exception{
        return getReading("humid") + " %RH";
    }

    @RequestMapping("/sensor/test")
    public String getTest() throws Exception{
        return getReading("test");
    }

    @RequestMapping("/sensor/th")
    public String getTempHumid() {
        String s = null;

        System.out.println(":::::::::::::::::::::::::");
        System.out.println(":::::::::::::::::::::::::");
        try {

            Process p = Runtime.getRuntime().exec("python3 PyScripts/dht2.py");

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            System.out.println(":::::::::::::::::::::::::");
            System.out.println(":::::::::::::::::::::::::");
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
        }

        return s;
    }
}
