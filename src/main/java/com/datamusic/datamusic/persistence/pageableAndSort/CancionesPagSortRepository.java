package com.datamusic.datamusic.persistence.pageableAndSort;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.datamusic.datamusic.persistence.entity.Cancion;

public interface CancionesPagSortRepository extends ListPagingAndSortingRepository<Cancion,Long>{
    

    Page<Cancion> findByPlaylistsPlaylistEntityIdPlaylist(Long idPlaylist,Pageable pageable);
}
