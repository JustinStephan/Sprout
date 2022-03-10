package com.juicy.sprout.model;

import lombok.Data;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface Reading {
    public Set<String> getRecords();

    void setRecord(String name, long value);

    public long getRecord(String name);

    public Date getDate();
}
