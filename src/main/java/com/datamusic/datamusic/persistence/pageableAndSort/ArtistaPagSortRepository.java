package com.datamusic.datamusic.persistence.pageableAndSort;

import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.datamusic.datamusic.persistence.entity.Artista;

public interface ArtistaPagSortRepository extends ListPagingAndSortingRepository<Artista,Long> {
    
}
