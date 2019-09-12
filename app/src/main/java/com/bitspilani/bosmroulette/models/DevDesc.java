package com.bitspilani.bosmroulette.models;

public class DevDesc {
    String name,desc;
    int Id;

    public DevDesc(String name, String desc, int Id){
        this.desc=desc;
        this.name=name;
        this.Id=Id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getId() {
        return Id;
    }
}
