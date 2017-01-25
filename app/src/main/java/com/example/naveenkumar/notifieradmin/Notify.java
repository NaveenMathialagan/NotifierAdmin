package com.example.naveenkumar.notifieradmin;

/**
 * Created by VSRK on 12/21/2015.
 */
public class Notify {

    //private variables
    String app;
    String packages;
    String title;
    String text;
    String hour;
    String minit;
    String  second;
    String date;
    String month;
    String year;
    String notiType;

    // Empty constructor
public Notify(){

}
    // constructor
    public Notify(String PACKAGE_NAME, String TITLE, String TEXT, String APP, String hour, String minit, String second, String date, String month, String year, String type){
        this.text = TEXT;
        this.packages = PACKAGE_NAME;
        this.title = TITLE;
        this.app=APP;

        this.hour=hour;
        this.minit=minit;
        this.second=second;
        this.date=date;
        this.month=month;
        this.year=year;
        this.notiType=type;
    }

    // constructor
  /*  public Notify(String PACKAGE_NAME, String TITLE){

        this.packages = PACKAGE_NAME;
        this.title = TITLE;
    }
    */
    // getting ID
    public String getApp(){

        return this.app;
    }

    // setting id
    public void setApp(String app){
        this.app = app;
    }

    // getting name
    public String getPackage(){
        return this.packages;
    }

    // setting name
    public void setPackage(String name){
        this.packages = name;
    }

    // getting phone number
    public String getTitle(){
        return this.title;
    }

    // setting phone number
    public void setTitle(String title){

        this.title = title;
    }
    public String getText()
    {
        return this.text;
    }

    // setting phone number
    public void setText(String text){

        this.text = text;
    }

    public String getHour(){

        return this.hour;
    }

    public void setHour(String hour){

        this.hour=hour;
    }
    public String  getMinit(){

        return this.minit;
    }

    public void setMinit(String minit){

        this.minit=minit;
    }

    public String getSecond(){

        return this.second;
    }

    public void setSecond(String second){

        this.second=second;
    }
    public String getDate(){

        return this.date;
    }

    public void setDate(String date){

        this.date=date;
    }
    public String getMonth(){

        return this.month;
    }

    public void setMonth(String month){

        this.month=month;
    }

    public String getYear(){

        return this.year;
    }

    public void setYear(String year){

        this.year=year;
    }

    public String getNotiType() {
        return notiType;
    }

    public void setNotiType(String notiType) {
        this.notiType = notiType;
    }
}

