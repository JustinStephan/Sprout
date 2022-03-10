package com.juicy.sprout.repo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@Setter
@Getter
@Table(name="logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long logId;
    private Date date;
    private Double humid;
    private Double temp;
    public Log() {
    }
//FIX ME dont think this is needed with lombok
//    public Log(Date date, Double humid, Double temp) {
//        this.date = date;
//        this.humid = humid;
//        this.temp = temp;
//    }

    @Override
    public String toString() {
        return String.format(
                "Log[id=%d, date=%s, humid=%d%%, temp=%d'C]",
                logId, date, humid, temp);

    }
}
