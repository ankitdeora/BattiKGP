package com.example.ankitdeora2856.battikgp;

/**
 * Created by ankitdeora2856 on 21-03-2016.
 */
public class mDate {
    private int year;
    private int month;
    private int day;

    mDate(String mystr){
        int index = mystr.indexOf(" ");
        mystr = mystr.substring(0,index); // yyyy-mm-dd

        index = mystr.indexOf("-");
        this.year = Integer.parseInt(mystr.substring(0, index));
        mystr = mystr.substring(index + 1); //mm-dd

        index = mystr.indexOf("-");
        this.month = Integer.parseInt(mystr.substring(0, index));
        mystr = mystr.substring(index + 1); //dd

        this.day = Integer.parseInt(mystr);

    }

    int getYear() {
        return this.year;
    }

    int getMonth(){
        return this.month;
    }

    int getDay(){
        return this.day;
    }
}
