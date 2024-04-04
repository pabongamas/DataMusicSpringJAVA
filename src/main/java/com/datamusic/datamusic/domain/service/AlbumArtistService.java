package com.datamusic.datamusic.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.AlbumArtist;
import com.datamusic.datamusic.domain.repository.AlbumArtistRepository;

@Service
public class AlbumArtistService {

    @Autowired
    private AlbumArtistRepository albumArtistRepository;

    public List<AlbumArtist> getAll() {
        return albumArtistRepository.getAll();
    }
    public Page<AlbumArtist> getAllByPage(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return albumArtistRepository.getAllByPage(pageRequest);
    }

    public List<AlbumArtist> getAlbumArtistByAlbumId(Long idAlbum) {
        return albumArtistRepository.getAlbumArtistByAlbumId(idAlbum);
    }

    public List<AlbumArtist> getAlbumArtistByArtistId(Long idArtist) {
        return albumArtistRepository.getAlbumArtistByArtistId(idArtist);
    }

    public AlbumArtist saveAlbumArtist(AlbumArtist albumArtist) {
        return albumArtistRepository.save(albumArtist);
    }

    public boolean delete(Long idAlbum, Long idArtist) {
        albumArtistRepository.delete(idAlbum, idArtist);
        return true;
    }

    public List<AlbumArtist> getAlbumArtistByAlbumIdAndIdArtist(Long idAlbum, Long idArtist) {
        return albumArtistRepository.getAlbumArtistByAlbumIdAndArtistId(idAlbum, idArtist);
    }

}
