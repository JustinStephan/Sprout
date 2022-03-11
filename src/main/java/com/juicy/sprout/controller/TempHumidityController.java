package com.juicy.sprout.controller;

import com.juicy.sprout.repo.Log;
import com.juicy.sprout.repo.LogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

@RestController
@EnableScheduling
@RequestMapping("/sensor")
public class TempHumidityController {

    @Autowired
    private LogRepo logRepo;

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

    @RequestMapping("/temp")
    public String getTemp() throws Exception{
        return "TEMP: " + getReading("temp") + " 'C";
    }

    @RequestMapping("/humid")
    public String getHumid() throws Exception{
        return "HUMIDITY: " + getReading("humid") + " %RH";
    }

    @RequestMapping("/test")
    public String getTest() throws Exception{
        String reading =  getReading("test");

        String[] arr = reading.split(":");
        return arr[1];
    }

    @RequestMapping("/th")
    public String getDigitalHumidTemp() throws Exception {
        String reading = getReading("dht");
        String[] arr = reading.split(":");

        for (String s : arr){System.out.print(s + " | ");}

        return "HUMIDITY: " + arr[0] + "%RH | TEMP: " + arr[1] + " 'C" ;
    }

    @Scheduled(cron = "0 * * * * *")
    @RequestMapping("/log")
    public void sendDigitalHumidTempToDb() throws Exception {
        String reading = getReading("dht");
        String[] arr = reading.split(":");

        System.out.println("HUMIDITY: " + arr[0]);
        System.out.println("TEMPERATURE: " + arr[1]);

        //TODO FINISH THIS METHOD
        dbUpdate(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
    }
    @RequestMapping("/logTest")
    @ResponseBody
    public String dbUpdate(@RequestParam Double humid, @RequestParam Double temp) {
        Log log = new Log();

        log.setDate(new Date(System.currentTimeMillis()));
        log.setHumid(humid);
        log.setTemp(temp);

        logRepo.save(log);

        return "DB UPDATED";
    }
}
