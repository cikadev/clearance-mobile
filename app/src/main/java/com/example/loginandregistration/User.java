package com.example.loginandregistration;

import java.util.Date;


public class User {
    String username;

    String s_name;
    String s_batch;
    String s_major;
    Date sessionExpiryDate;

    public void setUsername(String username) {
        this.username = username;
    }


    public void setSName(String s_name){
        this.s_name = s_name;
    }

    public void setSBatch(String s_batch){
        this.s_batch = s_batch;
    }

    public void setSMajor(String s_major){
        this.s_major = s_major;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }

    public String getUsername() {
        return username;
    }

    public String getSName(){
        return s_name;
    }

    public String getSBatch(){
        return s_batch;
    }

    public String getSMajor(){
        return s_major;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }
}
