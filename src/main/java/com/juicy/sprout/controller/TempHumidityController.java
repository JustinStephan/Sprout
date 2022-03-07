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
        Process p = rt.exec("python PythonScripts/dht2.py");
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

    @RequestMapping("/temp2")
    public void getTempHumid() {
        String s = null;

        try {

            Process p = Runtime.getRuntime().exec("python3 PyScripts/test.py");

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

            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
