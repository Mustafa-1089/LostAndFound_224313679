package com.example.lostfound.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String type;
    public String name;
    public String phone;
    public String description;
    public String date;
    public String timeStamp;
    public String location;
    public String imagePath;
    public String category;

    public Item(String type, String name, String phone, String description, String date, String timeStamp, String location, String imagePath, String category) {
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.timeStamp = timeStamp;
        this.location = location;
        this.imagePath = imagePath;
        this.category = category;
    }
}
