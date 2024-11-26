package com.datamusic.datamusic.web.controller;

import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.domain.AlbumArtist;
import com.datamusic.datamusic.domain.Artist;
import com.datamusic.datamusic.domain.Song;
import com.datamusic.datamusic.domain.service.AlbumService;
import com.datamusic.datamusic.domain.service.ImageProcess;
import com.datamusic.datamusic.domain.service.SaveFileService;
import com.datamusic.datamusic.domain.service.SaveFileServiceS3AWS;
import com.datamusic.datamusic.domain.service.SongService;
import com.datamusic.datamusic.persistence.DTO.FileUploadResponse;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
    private static final String NOT_FOUND_MESSAGE = "Album No Encontrado";
    private static final String NOT_FOUND_IMAGE_MESSAGE = "Imagen del album No Encontrada";
    @Autowired
    private AlbumService albumService;

    @Autowired
    private SaveFileService saveFileService;

    @Autowired
    private SongService songService;

    @Autowired
    private ImageProcess imageProcess;
    @Autowired
    private SaveFileServiceS3AWS SaveFileServiceS3AWS;

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Value("${upload.directory.albums}")
    private String uploadDirectoryAlbums;

    @Value("${upload.directory.albums.thumbs}")
    private String uploadDirectoryAlbumsThumbs;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            List<Album> albums = albumService.getAll();
            for (Album album : albums) {
                if (album.getNameFile() != null) {
                    String nameImgAlbum = album.getNameFile();
                    String uploadDirectory = this.uploadDirectory + "" + this.uploadDirectoryAlbums;
                    byte[] imgBytesAlbum = saveFileService.getImage(uploadDirectory, nameImgAlbum);
                    album.setImgAlbum(imgBytesAlbum);
                }
            }
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("albums", albums);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de los Albums", null),
                    HttpStatus.NOT_FOUND);
        }
    }
    // ESTE METODO ES EL MISMO DE ARRIBA SOLO QUE PAGINADO CON PAGEABLE
    // EN ESTE METODO RETORNO UN OBJETO DE PAGE CON EL MAPEO DE ALBUM ENTITY A ALBUM
    // se puede ver el codigo en el metodo getAllByPage del archivo
    // AlbumEntityRepository

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAlbumsSummaryPageable(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements, @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        try {
            Page<Album> albums = albumService.getAllByPage(page, elements, sortBy, sortDirection);
            for (Album album : albums.getContent()) {
                if (album.getNameFile() != null) {
                    String nameImgAlbum = album.getNameFile();
                    // String uploadDirectory = this.uploadDirectory + "" +
                    // this.uploadDirectoryAlbums;
                    String uploadDirectory = this.uploadDirectoryAlbums;

                    FileUploadResponse fileResponse = SaveFileServiceS3AWS.getFile(uploadDirectory, nameImgAlbum);
                    // byte[] imgBytesAlbum = saveFileService.getImage(uploadDirectory,
                    // nameImgAlbum);
                    // album.setImgAlbum(imgBytesAlbum);
                    album.setPathImageAlbum(null);
                    if (!fileResponse.getFilePath().isEmpty()) {
                        album.setPathImageAlbum(fileResponse.getFilePath());

                    }
                }
            }
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("albums", albums.getContent());
            response.addData("pageable", albums.getPageable());
            response.addData("totalElements", albums.getTotalElements());
            response.addData("elementsByPage", albums.getSize());
            response.addData("totalPages", albums.getTotalPages());
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", e.getMessage());
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de los Albums ", null, errors),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> getImageAlbum(@PathVariable("id") Long idAlbum) throws IOException {
        Optional<Album> albumById = albumService.getAlbumById(idAlbum);
        if (albumById.isPresent()) {
            Album album = albumById.get();
            if (album.getNameFile() != null) {
                String nameImgAlbum = album.getNameFile();
                String uploadDirectory = this.uploadDirectory + "" + this.uploadDirectoryAlbums;
                byte[] imgBytesAlbum = saveFileService.getImage(uploadDirectory, nameImgAlbum);

                ByteArrayResource resource = new ByteArrayResource(imgBytesAlbum);
                ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);

                response.addData("album", album);
                // response.addData("imgs", imagesByteList);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(resource.contentLength())
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                ContentDisposition.attachment()
                                        .filename(nameImgAlbum)
                                        .build().toString())
                        .body(resource);
            }

            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("imgAlbum", NOT_FOUND_IMAGE_MESSAGE);
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                    HttpStatus.NOT_FOUND);
        }
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error", NOT_FOUND_MESSAGE);
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                HttpStatus.NOT_FOUND);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAlbumById(@PathVariable("id") Long albumId, HttpServletRequest request)
            throws IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        Optional<Album> albumById = albumService.getAlbumById(albumId);
        if (albumById.isPresent()) {
            Album album = albumById.get();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("album", album);
            if (album.getNameFile() != null) {
                String nameImgAlbum = album.getNameFile();
                // String uploadDirectory = this.uploadDirectory + "" + this.uploadDirectoryAlbums;
                // String uploadDirectoryThumb = this.uploadDirectory + "" + this.uploadDirectoryAlbumsThumbs;

                // // List<Map<String, byte[]>> imagesByteList = new ArrayList<>();
                // byte[] imgBytesAlbum = saveFileService.getImage(uploadDirectory, nameImgAlbum);
                // // Map<String, byte[]>MapImgAlbum = new HashMap<>();
                // // MapImgAlbum.put("album_"+album.getAlbumId(),imgBytesAlbum);
                // // imagesByteList.add(MapImgAlbum);
                // album.setImgAlbum(imgBytesAlbum);


                String uploadDirectory = this.uploadDirectoryAlbums;

                FileUploadResponse fileResponse = SaveFileServiceS3AWS.getFile(uploadDirectory, nameImgAlbum);
                // byte[] imgBytesAlbum = saveFileService.getImage(uploadDirectory,
                // nameImgAlbum);
                // album.setImgAlbum(imgBytesAlbum);
                album.setPathImageAlbum("");
                if (!fileResponse.getFilePath().isEmpty()) {
                    album.setPathImageAlbum(fileResponse.getFilePath());
                    System.out.println(album.getPathImageAlbum());

                }

                // obtener colores principales de la imagen del album
                List<Map<String, Object>> colorsImg = imageProcess.colorsOfImage(uploadDirectory + "" + nameImgAlbum,
                        1);

                response.addData("route", uploadDirectory + "" + nameImgAlbum);
                // response.addData("routeThumb", uploadDirectoryThumb + "" + nameImgAlbum);
                response.addData("colors", colorsImg);
                // response.addData("imgs", imagesByteList);

            }
            // con este puedo crear una sequencia de datos y procesarla ahi mismo y retornar
            // una nueva data
            // como lo asigne
            List<AlbumArtist> listArtists = album.getArtists();
            String[] namesArtists = listArtists.stream().map(AlbumArtist::getArtist).map(Artist::getName)
                    .toArray(String[]::new);
            response.addData("artists", namesArtists);

            // voy a listar las canciones que tiene el album
            List<Song> songs;
            try {
                songs = songService.getSongsByAlbumId(albumId);
            } catch (Exception e) {
                return new ResponseEntity<ApiResponse>(
                        new ApiResponse(false,
                                "No se ha Recuperado la informac&oacute; de las canciones por este album",
                                null),
                        HttpStatus.NOT_FOUND);
            }
            if (songs.size() > 0) {
                Iterator<Song> songsIterator = songs.iterator();
                while (songsIterator.hasNext()) {
                    Song songIteration = songsIterator.next();
                    songIteration.setAlbum(null);
                    songIteration.setAlbumId(null);
                    boolean isLiked = songService.songIsLiked(songIteration.getSongId(), albumId, token);
                    songIteration.setIsLikedByCurrentUser(isLiked);
                }
                Collections.sort(songs, new Comparator<Song>() {

                    @Override
                    public int compare(Song arg0, Song arg1) {
                        return arg0.getNumberSong() != null ? arg0.getNumberSong().compareTo(arg1.getNumberSong()) : 0;
                    }

                });
            }
            response.addData("songs", songs);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        }
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error", NOT_FOUND_MESSAGE);
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/imageThumb")
    public ResponseEntity<?> getImageAlbumThumb(@PathVariable("id") Long idAlbum) throws IOException {
        Optional<Album> albumById = albumService.getAlbumById(idAlbum);
        if (albumById.isPresent()) {
            Album album = albumById.get();
            if (album.getNameFile() != null) {
                String nameImgAlbum = album.getNameFile();
                String uploadDirectoryThumb = this.uploadDirectory + "" + this.uploadDirectoryAlbumsThumbs;
                byte[] imgBytesAlbumThumb = saveFileService.getImage(uploadDirectoryThumb, nameImgAlbum);

                ByteArrayResource resource = new ByteArrayResource(imgBytesAlbumThumb);
                ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);

                response.addData("album", album);
                // response.addData("imgs", imagesByteList);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(resource.contentLength())
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                ContentDisposition.attachment()
                                        .filename(nameImgAlbum)
                                        .build().toString())
                        .body(resource);
            }

            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("imgAlbum", NOT_FOUND_IMAGE_MESSAGE);
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                    HttpStatus.NOT_FOUND);
        }
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error", NOT_FOUND_MESSAGE);
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                HttpStatus.NOT_FOUND);

    }

    @GetMapping("/gender/{id}")
    public ResponseEntity<ApiResponse> getAlbumByGenderId(@PathVariable("id") Long genderId) {

        try {
            List<Album> albumsByGender = albumService.getAlbumByGenderId(genderId);
            for (Album album : albumsByGender) {
                if (album.getNameFile() != null) {
                    String nameImgAlbum = album.getNameFile();
                    String uploadDirectory = this.uploadDirectory + "" + this.uploadDirectoryAlbums;
                    byte[] imgBytesAlbum = saveFileService.getImage(uploadDirectory, nameImgAlbum);
                    album.setImgAlbum(imgBytesAlbum);
                }
            }
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("albums", albumsByGender);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de los Albums Por el genero", null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/genderByPage/{id}")
    public ResponseEntity<ApiResponse> getAlbumByGenderIdByPage(@PathVariable("id") Long genderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements, @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        try {
            Page<Album> albumsByGender = albumService.getAlbumsByGenderByPage(genderId, page, elements, sortBy,
                    sortDirection);
            for (Album album : albumsByGender) {
                if (album.getNameFile() != null) {
                    String nameImgAlbum = album.getNameFile();
                    String uploadDirectory = this.uploadDirectory + "" + this.uploadDirectoryAlbums;
                    byte[] imgBytesAlbum = saveFileService.getImage(uploadDirectory, nameImgAlbum);
                    album.setImgAlbum(imgBytesAlbum);
                }
            }
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("albums", albumsByGender.getContent());
            response.addData("pageable", albumsByGender.getPageable());
            response.addData("totalElements", albumsByGender.getTotalElements());
            response.addData("elementsByPage", albumsByGender.getSize());
            response.addData("totalPages", albumsByGender.getTotalPages());
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errores = new HashMap<String, String>();
            errores.put("error", e.getMessage());
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de los Albums Por el genero", null,
                            errores),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody Album album) {
        try {
            Album albumsaved = albumService.save(album);

            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("album", albumsaved);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
        } catch (SQLGrammarException ex) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "El genero con id " + album.getGenderId() + " no se encuentra registrado",
                            null),
                    HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/saveWithImage")
    public ResponseEntity<ApiResponse> saveWithImage(@Valid @RequestPart("album") Album album,
            @RequestPart("image") MultipartFile image) throws IOException {
        try {
            Album albumsaved = albumService.save(album);
            // Album albumsaved = album;

            // String uploadDirectory ="src/main/resources/static/images/ads";
            // String uploadDirectory = this.uploadDirectory + "" +
            // this.uploadDirectoryAlbums;
            // String uploadDirectory = "/images/album";
            String uploadDirectory=this.uploadDirectoryAlbums;

            FileUploadResponse fileUploaded = SaveFileServiceS3AWS.uploadFile(image, uploadDirectory);
            // String nameImgSaved = saveFileService.saveFileToStorage(uploadDirectory,
            // image);
            String nameImgSaved = fileUploaded.getNameFile();

            // PATH OF THUMBNAILS
            String pathThumbTemp = this.uploadDirectory + "" + this.uploadDirectoryAlbumsThumbs;
            // imageProcess.generateThumbnailOfImage(uploadDirectory + "" + nameImgSaved, uploadDirectoryThumbsAlbum);
            ByteArrayOutputStream fileByteArrayOutPut=imageProcess.generateThumbnailMultiPartFile(image,pathThumbTemp);

            //save the thumb of the file in s3 with the directory /thumbs/ 
            String pathThumbTempS3 = this.uploadDirectoryAlbumsThumbs+"/"+nameImgSaved;
            FileUploadResponse fileUploadedThumb = SaveFileServiceS3AWS.uploadThumbNailS3(fileByteArrayOutPut, pathThumbTempS3);
    
            // actualizo el ablum creado con la ruta de la imagen guardada
            albumsaved.setNameFile(nameImgSaved);
            Album albumsavedWithImg = albumService.save(albumsaved);
            albumsavedWithImg.setNameFile(null);
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("album", albumsavedWithImg);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
        } catch (SQLGrammarException ex) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "El genero con id " + album.getGenderId() + " no se encuentra registrado",
                            null),
                    HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long albumId) {

        try {
            // obtengo la informacion del album
            Optional<Album> albumByID = albumService.getAlbumById(albumId);
            String nameAlbum = "";
            String nameFile = "";
            if (albumByID.isPresent()) {
                // obtengo y guardo el nombre del album
                nameAlbum = albumByID.get().getName();
                if (albumByID.get().getNameFile() != null) {
                    nameFile = albumByID.get().getNameFile();
                }
            }
            // obtengo lista de artistas que puede tener un album
            List<AlbumArtist> albumsByArtist = albumService.getartistsByAlbum(albumId);
            if (!albumsByArtist.isEmpty()) {
                // creo lista donde voy a guardar los nombres de los artistas
                List<String> artists = new ArrayList<>();
                // recorro lista de artistas e inserto el nombre en la lista de nombres de los
                // artistas
                albumsByArtist.forEach(artistAlbum -> {
                    artists.add(artistAlbum.getArtist().getName());
                });
                // los uno con el delimitador ,
                String NamesArtist = String.join(",", artists);

                // si la lista de albums por artista esta diferente de vacia es porque el album
                // tiene artistas relacionados los cuales no se pueden eliminar
                throw new Exception("El album " + nameAlbum
                        + "  esta siendo ocupado por " + NamesArtist + " , por lo cual no puede ser eliminado.");
            }
            // si no presenta el album , artistas relacionados ,permite eliminar
            boolean albumdeleted = albumService.delete(albumId);
            if (albumdeleted) {
                if (nameFile != "") {
                    // String uploadDirectory ="src/main/resources/static/images/ads";
                    String uploadDirectory = this.uploadDirectory + "" + this.uploadDirectoryAlbums;
                    String uploadDirectoryThumb = this.uploadDirectory + "" + this.uploadDirectoryAlbumsThumbs;
                    boolean nameImgSaved = saveFileService.deleteImage(uploadDirectory, nameFile);
                    boolean thumbSaved = saveFileService.deleteImage(uploadDirectoryThumb, nameFile);

                }
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, SUCCESSFUL_MESSAGE, null, null),
                        HttpStatus.OK);
            }
            // retornamos errores por si hubo algun error en la eliminacion del album
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", NOT_FOUND_MESSAGE);
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // retornamos errores por si hubo algun error en la eliminacion del album
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", e.getMessage());
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
