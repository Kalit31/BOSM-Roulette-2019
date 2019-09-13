package com.bitspilani.bosmroulette.models;

public class DevDesc {
    String name,desc,Id;

    public DevDesc(String name, String desc, String Id){
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

    public String getId() {
        return Id;
    }
}
