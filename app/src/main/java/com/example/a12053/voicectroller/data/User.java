package com.example.a12053.voicectroller.data;

public class User {
    private String id;
    private String psd;

    public User(String id,String psd){
        this.id = id;
        this.psd = psd;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setPsd(String psd){
        this.psd = psd;
    }

    public String getPsd (){
        return psd;
    }
}
