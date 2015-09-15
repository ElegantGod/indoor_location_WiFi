package com.love.yan;

public class DataWifi {

    private String ssid;
    private int level;
    private String bssid;
    private String number;

    public DataWifi() {
    }
    public DataWifi(String ssid, int level, String bssid, String number){
    	this.ssid = ssid;
    	this.level = level;
    	this.bssid = bssid;
    	this.number = number;
    }

    public String getssid() {
        return ssid;
    }

    public int getlevel() {
        return level;
    }
    public String getbssid(){
    	return bssid;
    }
    public String getnumber(){
    	return number;
    }

    public void setssid(String ssid) {
        this.ssid = ssid;
    }

    public void setlevel(int level) {
        this.level = level;
    }

    public void setbssid(String bssid) {
        this.bssid = bssid;
    }

    public void setnumber(String number) {
        this.number = number;
    }
    public String toString(){
    	return "账号:"+ssid+";强度:"+level+";地址:"+bssid+";位置:"+number;
    }
}
