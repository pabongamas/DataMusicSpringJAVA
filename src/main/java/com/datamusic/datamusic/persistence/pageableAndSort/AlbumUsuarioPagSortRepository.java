package com.datamusic.datamusic.persistence.pageableAndSort;


import java.util.List;

import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.datamusic.datamusic.persistence.entity.AlbumsUsuario;
import com.datamusic.datamusic.persistence.entity.AlbumsUsuarioPK;

public interface AlbumUsuarioPagSortRepository extends ListPagingAndSortingRepository<AlbumsUsuario, AlbumsUsuarioPK> {
}
