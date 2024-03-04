package com.datamusic.datamusic.domain.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SaveFileService {

    //guardar imagen en un directorio local
    public String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        //asigno nombre unico , donde me concatena UUID aleatorio con el nombre original de la imagen
        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

        //asigna a path la ruta del directorio
        Path uploadPath = Path.of(uploadDirectory);
        //une el directorio con la imagen , ejemplo  /static/images/album/b1ac3f94-11a0-400e-9017-4cdf5d9cc335_Captura desde 2024-03-01 17-39-59.png
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    public byte[] getImage(String imageDirectory,String imageName) throws IOException{
        Path imagePath=Path.of(imageDirectory,imageName);

        if(Files.exists(imagePath)){
            byte[] imageBytes=Files.readAllBytes(imagePath);
            return imageBytes;
        }else{
            return null;
        }
    }

    public boolean deleteImage(String imageDirectory, String imageName) throws IOException{
        Path imagePath=Path.of(imageDirectory,imageName);

        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
            return true;
        } else {
            return false;
        }
    }
}
