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

    @RequestMapping("/temp")
    public void getTemp() throws Exception{
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec("python PyScripts/dht2.py");
        BufferedReader br = new BufferedReader(new
                InputStreamReader(p.getInputStream()));
        if ((line = br.readLine()) != null) {
            if (!(line.contains("ERR_CRC") || line.contains("ERR_RNG"))) {
                System.out.println(line);
//                data = line.split("ABC");
//                System.out.println(data[0]);
//                temperature = Integer.parseInt(data[0]);
//                humidity = Integer.parseInt(data[1]);
            } else
                System.out.println("Data Error");
        }

        br.close();
        p.waitFor();
//        System.out.println("Temperature is : " + temperature + " 'C Humidity is :" + humidity + " %RH");
//        System.out.println("Done.");
    }

    @RequestMapping("/temp2")
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
