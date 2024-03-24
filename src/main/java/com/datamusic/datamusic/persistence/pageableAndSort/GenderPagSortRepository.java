package com.datamusic.datamusic.persistence.pageableAndSort;

import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.datamusic.datamusic.persistence.entity.Genero;

public interface GenderPagSortRepository extends ListPagingAndSortingRepository<Genero,Long> {
    
    
}
