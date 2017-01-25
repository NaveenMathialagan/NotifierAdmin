package com.example.naveenkumar.notifieradmin.offers;

/**
 * Created by Naveen kumar on 03-03-2016.
 */
public class ofrinst {
    String ofrname;
    String ofrrate;

    public ofrinst(){

    }
    public ofrinst(String ofrname,String ofrrate){
       this.ofrname=ofrname;
        this.ofrrate=ofrrate;

    }

    public String getOfrname(){

        return this.ofrname;
    }

    public void setOfrname(String ofrname){

        this.ofrname = ofrname;
    }

    // getting name
    public String getOfrrate(){

        return this.ofrrate;
    }

    // setting name
    public void setOfrrate(String ofrrate){

        this.ofrrate = ofrrate;
    }

}
