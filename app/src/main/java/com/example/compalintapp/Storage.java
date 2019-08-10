package com.example.compalintapp;

public class Storage {

    static  String TABLE_NAME = "Storage";
    static  String DATABASE_NAME = "Complaint_Database";
    static  String COLUMN_ID = "id";
    static  String COLUMN_NAME = "Name";
    static  String COLUMN_COMP = "Complaint";
    static  String COLUMN_NUMBER = "Number";

    static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Complaint TEXT,Number INTEGER) ";

    int id;
    String name;
    String complaint;
    String num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
