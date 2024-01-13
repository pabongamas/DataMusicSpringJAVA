package com.datamusic.datamusic.web.controller.IO;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

public class ApiResponse {
    private String message;
     @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> data;
    public ApiResponse() {
    }
    
    public ApiResponse(String message) {
        this.message = message;
        this.data = new HashMap<>();
    }
    public void addData(String key, Object value) {
        this.data.put(key, value);
    }

    public ApiResponse(String message, Map<String, Object> data) {
        this.message = message;
        this.data = data;
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
    
    

    
}
