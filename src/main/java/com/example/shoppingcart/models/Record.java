package com.example.shoppingcart.models;

import java.util.ArrayList;

public class Record {
    public Record(ArrayList<String> records) {
        this.records = records;
    }

    public Record() {
    }

    public ArrayList<String> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<String> records) {
        this.records = records;
    }

    public void addRecord(String s){
        this.records.add(s);
    }
    ArrayList<String> records=new ArrayList<>();

}
