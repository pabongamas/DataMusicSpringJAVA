package com.datamusic.datamusic.persistence.pageableAndSort;

import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.datamusic.datamusic.persistence.entity.Cancion;

public interface CancionesPagSortRepository extends ListPagingAndSortingRepository<Cancion,Long>{
    
}
