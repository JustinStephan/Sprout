package com.juicy.sprout.model;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class DHTReading implements Reading {
    private Date date;
    private Map<String,Long> store;


    public DHTReading(Date date, long humid, long temp) {
        this.date = date;
        this.store = new HashMap<>();

        this.store.put("humid", humid);
        this.store.put("temp", temp);
    }

    @Override
    public Set<String> getRecords() {
        return this.store.keySet();
    }

    @Override
    public void setRecord(String name, long value) {
        this.store.put(name, value);
    }

    @Override
    public long getRecord(String name) {
        return this.store.get(name);
    }
}
