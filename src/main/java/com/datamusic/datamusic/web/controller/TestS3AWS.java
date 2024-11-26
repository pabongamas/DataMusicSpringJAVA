package com.datamusic.datamusic.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.datamusic.datamusic.domain.service.SaveFileService;
import com.datamusic.datamusic.domain.service.SaveFileServiceS3AWS;
import com.datamusic.datamusic.persistence.DTO.FileAwsResponse;
import com.datamusic.datamusic.persistence.DTO.FileUploadResponse;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/testaws")
public class TestS3AWS {
    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";

     @Autowired
     private SaveFileServiceS3AWS SaveFileServiceS3AWS;

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(MultipartFile file) {
        return new ResponseEntity<>(SaveFileServiceS3AWS.uploadFile(file,"/images/album"), HttpStatus.OK);
    }

    @GetMapping("/getFile")
    public ResponseEntity<ApiResponse> getDataFile(@RequestParam String param) {
        FileUploadResponse fileResponse= SaveFileServiceS3AWS.getFile("/images/album",param);
        ApiResponse response=new ApiResponse(true,SUCCESSFUL_MESSAGE);
        response.addData("info", fileResponse);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }
    
    
}
