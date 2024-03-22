package com.datamusic.datamusic.persistence.pageableAndSort;

import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.datamusic.datamusic.persistence.entity.Usuario;

public interface UserPagSortRepository extends ListPagingAndSortingRepository<Usuario,Long> {
    
    
}
