package com.datamusic.datamusic.web.controller;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.domain.AlbumArtist;
import com.datamusic.datamusic.domain.Artist;
import com.datamusic.datamusic.domain.Gender;
import com.datamusic.datamusic.domain.Song;
import com.datamusic.datamusic.domain.service.SaveFileService;
import com.datamusic.datamusic.domain.service.SaveFileServiceS3AWS;
import com.datamusic.datamusic.domain.service.SongService;
import com.datamusic.datamusic.persistence.DTO.FileUploadResponse;
import com.datamusic.datamusic.persistence.DTO.FormAdminSaveSong;
import com.datamusic.datamusic.web.controller.IO.ApiResponse;

import jakarta.validation.Valid;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/songs")
public class CancionController {

    @Autowired
    private SaveFileService saveFileService;

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Value("${upload.directory.songs}")
    private String uploadDirectorySongs;

    private static final String ERROR_MESSAGE = "Han ocurrido errores";
    private static final String SUCCESSFUL_MESSAGE = "Operación exitosa";
    private static final String NOT_FOUND_MESSAGE = "Canción No Encontrada";

    @Autowired
    private SongService songService;

    @Autowired
    private SaveFileServiceS3AWS SaveFileServiceS3AWS;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            List<Song> songs = songService.getAll();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("songs", songs);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de las canciones", null),
                    HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/allPageable")
    public ResponseEntity<ApiResponse> getAllPageable(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        try {
            Page<Song> songs = songService.getAllPageable(page, elements, sortBy, sortDirection);
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("songs", songs.getContent());
            response.addData("pageable", songs.getPageable());
            response.addData("totalElements", songs.getTotalElements());
            response.addData("elementsByPage", songs.getSize());
            response.addData("totalPages", songs.getTotalPages());
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha recuperado la informacion de las canciones ", null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBysongId(@PathVariable("id") Long songId) {
        Optional<Song> songById = songService.getSong(songId);

        if (songById.isPresent()) {
            Song song = songById.get();
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("song", song);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        }
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error", NOT_FOUND_MESSAGE);
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                HttpStatus.NOT_FOUND);
    }

    @GetMapping("/album/{id}")
    public ResponseEntity<ApiResponse> getSongsByAlbumId(@PathVariable("id") Long albumId) {
        try {
            List<Song> songsByAlbumId = songService.getSongsByAlbumId(albumId);
            Iterator<Song> songsIterator = songsByAlbumId.iterator();
            Album album = new Album();
            while (songsIterator.hasNext()) {
                Song song = songsIterator.next();
                album = song.getAlbum();
                song.setAlbum(null);
                song.setAlbumId(null);
            }
            // Collections.sort(songsByAlbumId,new Comparator<Song>() {

            // @Override
            // public int compare(Song arg0, Song arg1) {
            // return arg0.getNumberSong().compareTo(arg1.getNumberSong());
            // }

            // });
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("songsByAlbum", songsByAlbumId);
            response.addData("album", album);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de las canciones por este album",
                            null),
                    HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/gender/{id}")
    public ResponseEntity<ApiResponse> getSongsByGenderId(@PathVariable("id") Long genderId) {
        try {
            List<Song> songsByGenderId = songService.getSongsByGeneroId(genderId);
            Iterator<Song> songsIterator = songsByGenderId.iterator();
            Gender gender = new Gender();
            while (songsIterator.hasNext()) {
                Song song = songsIterator.next();
                gender = song.getAlbum().getGender();
                song.setAlbum(null);
                song.setAlbumId(null);
            }
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("songsByGender", songsByGenderId);
            response.addData("gender", gender);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de las canciones por este Genero",
                            null),
                    HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/playlist/{id}")
    public ResponseEntity<ApiResponse> getSongsByPlaylist(@PathVariable("id") Long playlistId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        try {
            List<Song> getSongsByPlaylist = songService.getSongsByPlaylist(playlistId);
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("songsByPlaylist", getSongsByPlaylist);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de las canciones por este Artista",
                            null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/playlist/{id}/byPage")
    public ResponseEntity<ApiResponse> getSongsByPlaylistPage(@PathVariable("id") Long playlistId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        try {
            Page<Song> getSongsByPlaylist = songService.getSongsByPlaylistPage(playlistId, page, elements, sortBy,
                    sortDirection);
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("songs", getSongsByPlaylist.getContent());
            response.addData("pageable", getSongsByPlaylist.getPageable());
            response.addData("totalElements", getSongsByPlaylist.getTotalElements());
            response.addData("elementsByPage", getSongsByPlaylist.getSize());
            response.addData("totalPages", getSongsByPlaylist.getTotalPages());
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de las canciones por este Artista",
                            null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<ApiResponse> getSongsByArtist(@PathVariable("id") Long artistId) {
        try {
            List<Song> songsByArtistId = songService.getSongsByArtistId(artistId);
            Iterator<Song> iteratorSong = songsByArtistId.iterator();
            List<Artist> artists = new ArrayList<Artist>();
            while (iteratorSong.hasNext()) {
                Song song = iteratorSong.next();
                song.getAlbum().getArtists().stream().map(AlbumArtist::getArtist)
                        .forEach(artist -> {
                            if (!artists.contains(artist)) {
                                artists.add(artist);
                            }
                        });
                song.setAlbum(null);
                // song.getAlbum().setArtists(null);
            }
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            response.addData("songsByArtist", songsByArtistId);
            response.addData("artists", artists);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "No se ha Recuperado la informac&oacute; de las canciones por este Artista",
                            null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@Valid @RequestPart("song") FormAdminSaveSong songToSaveDTO,
            @RequestPart(value = "file", required = false) MultipartFile fileSong) throws IOException {
        Song song = new Song();
        song.setName(songToSaveDTO.getName());
        song.setDuration(songToSaveDTO.getDuration());
        song.setExplicit(songToSaveDTO.getExplicit());
        song.setNumberSong(songToSaveDTO.getNumberSong());
        song.setAlbumId(songToSaveDTO.getAlbumId());
        song.setSongId(songToSaveDTO.getSongId());
        System.out.println(songToSaveDTO.getSongId());
        song.setNameFile(songToSaveDTO.getNameFile());
        try {

            if (song.getSongId() == null || !songToSaveDTO.getLoaded()) {
                Optional<Song> validCancion = songService.getSongByNameAndAlbumId(song.getName(), song.getAlbumId());
                if (validCancion.isPresent()) {
                    Map<String, String> errors = new HashMap<String, String>();
                    errors.put("error", "La cancion " + song.getName() + " ya esta incluida en este album ");
                    return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                            HttpStatus.BAD_REQUEST);
                }
            }
            ApiResponse response = new ApiResponse(true, SUCCESSFUL_MESSAGE);
            if (!songToSaveDTO.getLoaded() || (songToSaveDTO.getLoaded() && songToSaveDTO.getEdited())) {
                System.out.println(fileSong);
                if (fileSong != null) {
                    String uploadDirectory = this.uploadDirectorySongs + "/" + song.getAlbumId();
                    FileUploadResponse fileUploaded = SaveFileServiceS3AWS.uploadFile(fileSong, uploadDirectory);
                    // String nameFileSaved = saveFileService.saveFileToStorage(uploadDirectory,
                    // fileSong);
                    String nameFileSaved = fileUploaded.getNameFile();

                    song.setNameFile(nameFileSaved);
                }
                Song songSaved = songService.save(song);
                response.addData("song", songSaved);
            }
            return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
        } catch (SQLGrammarException ex) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "Error de gramática SQL:" + ex.getSQLException()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<ApiResponse>(
                    new ApiResponse(false, "El album con id " + song.getAlbumId() + " no se encuentra registrado",
                            null),
                    HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSong(@PathVariable("id") Long songId) {
        try {
            boolean songDeleted = songService.delete(songId);
            if (songDeleted) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(songDeleted, SUCCESSFUL_MESSAGE, null),
                        HttpStatus.OK);
            }
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", NOT_FOUND_MESSAGE);
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<String, String>();
            errors.put("error", e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, ERROR_MESSAGE, null, errors),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadSong")
    public ResponseEntity<ApiResponse> uploadSong(@Valid @RequestPart("song") MultipartFile image) throws IOException {

        // String uploadDirectory ="src/main/resources/static/songs";
        String uploadDirectory = this.uploadDirectory + "" + this.uploadDirectorySongs;

        String nameImgSaved = saveFileService.saveFileToStorage(uploadDirectory, image);
        // TODO: process POST request

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, SUCCESSFUL_MESSAGE), HttpStatus.OK);
    }

    @GetMapping("play/{idSong}")
    public ResponseEntity<InputStreamResource> streamAudio(@PathVariable Long idSong,
            @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {

        Optional<Song> songObj = this.songService.getSong(idSong);
        Song song = songObj.get();
        String fileName = song.getNameFile();
        Long albumId = song.getAlbumId();

        String uploadDirectorySongs = this.uploadDirectorySongs;

        // Obtener el archivo desde S3
        String s3Key = uploadDirectorySongs + "/" + albumId + "/" + fileName; // Ruta del archivo en S3
        S3Object s3Object = SaveFileServiceS3AWS.getFileFromS3(s3Key); // Método para obtener el archivo desde S3
        // FileUploadResponse fileResponse =
        // SaveFileServiceS3AWS.getFile(uploadDirectorySongs,+albumId+"/"+fileName);
        Path filePath = Paths.get(uploadDirectory + "/" + albumId).resolve(fileName).normalize();
        InputStream inputStream = s3Object.getObjectContent();
        long fileSize = s3Object.getObjectMetadata().getContentLength();

        if (rangeHeader == null) {
            // Si no se especifica un rango, devolver el archivo completo
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize))
                    .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                    .body(new InputStreamResource(inputStream));
        }
        // Parsear el rango solicitado
        HttpRange range = HttpRange.parseRanges(rangeHeader).get(0);
        long start = range.getRangeStart(fileSize);
        long end = range.getRangeEnd(fileSize);
        long contentLength = end - start + 1;

        // Leer el rango específico desde S3
        byte[] partialContent = new byte[(int) contentLength];
        inputStream.skip(start); // Saltar al inicio del rango
        inputStream.read(partialContent); // Leer el rango específico

        // Cerrar el InputStream original
        inputStream.close();

        // Crear un nuevo InputStream con el contenido parcial
        InputStream partialInputStream = new ByteArrayInputStream(partialContent);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
                .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize)
                .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                .body(new InputStreamResource(partialInputStream));
    }

}
