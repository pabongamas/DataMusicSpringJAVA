package com.datamusic.datamusic.persistence.pageableAndSort;

import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.datamusic.datamusic.persistence.entity.AlbumsArtista;
import com.datamusic.datamusic.persistence.entity.AlbumsArtistaPK;

public interface AlbumArtistaPagSortRepository extends ListPagingAndSortingRepository<AlbumsArtista,AlbumsArtistaPK> {
    
}
