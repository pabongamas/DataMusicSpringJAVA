package com.datamusic.datamusic.web.controller.IO;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.HashMap;

public class ApiResponse {
    public boolean state;
    private String message;
     @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> data;
    public Date date;
    public ApiResponse() {
    }
    
    public ApiResponse(boolean state,String message) {
        this.state=state;
        this.message = message;
        this.data = new HashMap<>();
        this.date = new Date();
    }
    public void addData(String key, Object value) {
        this.data.put(key, value);
    }

    public ApiResponse(boolean state,String message, Map<String, Object> data) {
        this.state=state;
        this.message = message;
        this.data = data;
        this.date = new Date();
    }


    public String getMessage() {
        return message;
    }



    public void setMessage(String message) {
        this.message = message;
    }



    public Map<String, Object> getData() {
        return data;
    }



    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    

    
}