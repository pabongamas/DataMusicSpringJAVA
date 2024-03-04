package com.datamusic.datamusic.persistence.pageableAndSort;

import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.datamusic.datamusic.persistence.entity.AlbumEntity;

public interface AlbumPagSortRepository extends ListPagingAndSortingRepository<AlbumEntity,Long>{
    
    
}
