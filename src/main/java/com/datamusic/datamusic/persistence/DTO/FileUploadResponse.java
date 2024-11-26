package com.datamusic.datamusic.persistence.DTO;

import java.time.LocalDateTime;

public class FileUploadResponse {
    private String filePath;
    private String nameFile;
    private LocalDateTime dateTime;
    
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public String getNameFile() {
        return nameFile;
    }
    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }
    


    
}
