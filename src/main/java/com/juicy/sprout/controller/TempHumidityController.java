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
    private static int[] pins = {19};
    static int humidity = 0;
    static int temperature = 0;


    private String getReading(String readingType, int pin) throws Exception {
        Runtime rt = Runtime.getRuntime();
        String command = "python PyScripts/" + readingType + ".py " + pin;
        System.out.println("\n\nSCRIPT EXECUTED: " + command + "\n\n");
        Process p = rt.exec(command);
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
        StringBuilder response = new StringBuilder();
        for (int pin : pins){
        response.append("TEMP: ").append(getReading("temp", pin)).append(" 'C\n");
        }
        return response.toString();
    }

    @RequestMapping("/humid")
    public String getHumid() throws Exception{
        StringBuilder response = new StringBuilder();
        for (int pin : pins){
            response.append("HUMIDITY: ").append(getReading("humid", pin)).append(" %RH");
        }
        return response.toString();
    }

    @RequestMapping("/all")
    public String getAllDigitalHumidTemp() throws Exception {
        StringBuilder response = new StringBuilder();
        int i = 1;
        for (int pin : pins){
//        String reading = getReading("dht", 19);
            response.append("HUMIDITY ").append(i).append(": ").append(getReading("humid", pin)).append(" %RH,");
            response.append("TEMP ").append(i).append(": ").append(getReading("temp", pin)).append(" 'C,");
        }
        return response.toString();
    }

    @RequestMapping("/th")
    public String getDigitalHumidTemp() throws Exception {
        String reading = getReading("dht", 19);
        String[] arr = reading.split(":");
        System.out.println("\n\n" + reading + "\n\n");
       // for (String s : arr){System.out.print(s + " | ");}

        return "HUMIDITY: " + arr[0] + "%RH\nTEMP: " + arr[1] + " 'C" ;
    }

    @Scheduled(cron = "0 * * * * *")
    @RequestMapping("/log")
    public void sendDigitalHumidTempToDb() throws Exception {
        String reading = getReading("dht", 19);
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
