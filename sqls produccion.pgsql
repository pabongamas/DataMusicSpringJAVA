-- CREATE TABLE ARTISTAS (
--   "id_artista" SERIAL NOT NULL,
--   "nombre" VARCHAR(100) NOT NULL,
--   PRIMARY KEY ("id_artista"));

--   CREATE TABLE GENEROS (
--   "id_genero" SERIAL NOT NULL,
--   "nombre" VARCHAR(100) NOT NULL,
--   PRIMARY KEY ("id_genero"));
  
--     CREATE TABLE  ALBUMS (
--   "id_album" SERIAL NOT NULL,
--   "nombre" VARCHAR(100) NULL,
--   "anio" INT NOT NULL,
--   "id_genero" INT NOT NULL,
--   PRIMARY KEY ("id_album"),
--   CONSTRAINT "fk_ALBUMS_GENEROS"
--     FOREIGN KEY ("id_genero")
--     REFERENCES GENEROS ("id_genero")
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION);

--     CREATE TABLE  ALBUMS_ARTISTA (
--   "id_album" INT NOT NULL,
--   "id_artista" INT NOT NULL,
--   PRIMARY KEY ("id_album", "id_artista"),
--   CONSTRAINT "fk_ALBUMS_ARTISTA_ARTISTA1"
--     FOREIGN KEY ("id_artista")
--     REFERENCES ARTISTAS ("id_artista")
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION,
--   CONSTRAINT "fk_ALBUMS_ARTISTA_ALBUMS1"
--     FOREIGN KEY ("id_album")
--     REFERENCES ALBUMS ("id_album")
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION);


    -- CREATE TABLE CANCIONES (
    -- "id_cancion" SERIAL NOT NULL,
    -- "nombre" VARCHAR(255) NOT NULL,
    -- "duracion" INTEGER,
    -- "id_album" INT NOT NULL,
    -- PRIMARY KEY ("id_cancion"),
    -- CONSTRAINT "fk_CANCIONES_ALBUMS"
    -- FOREIGN KEY ("id_album")
    -- REFERENCES ALBUMS ("id_album")
    -- ON DELETE CASCADE
    -- ON UPDATE NO ACTION);

    --   CREATE TABLE USUARIOS (
    -- "id_usuario" SERIAL NOT NULL,
    -- "nombre" VARCHAR(255) NOT NULL,
    -- "apellidos" VARCHAR(255) NOT NULL,
    -- "correo_electronico" VARCHAR(255) NOT NULL,
    -- "contrasena" VARCHAR(255) NOT NULL,
    -- PRIMARY KEY ("id_usuario"));

    
    -- insert into usuarios (nombre,apellidos,correo_electronico,contrasena) values('Jhon jairo','Pabon gamas','pabongamas@gmail.com','$2a$10$kXaqm1ZVrer39FSfhHznvOc1L9bhm1fa12V6ftnrrxfR.gXsUlwgK'); 

    -- insert into usuarios (nombre,apellidos,correo_electronico,contrasena) values('user pruebita','Pabon gamas','emailsito@gmail.com','$2a$10$c4J6xr3O3GGtgbPDLTbAP.yOkgvTUtEcW6VHTb2VRkQm95rWVROJa'); 

    -- CREATE TABLE PLAYLISTS (
    -- "id_playlist" SERIAL NOT NULL,
    -- "nombre" VARCHAR(255) NOT NULL,
    -- "fecha_creacion" TIMESTAMP NOT NULL,
    -- "id_usuario" INT NOT NULL,
    -- PRIMARY KEY ("id_playlist"),
    --    CONSTRAINT "fk_PLAYLISTS_USUARIOS"
    -- FOREIGN KEY ("id_usuario")
    -- REFERENCES USUARIOS ("id_usuario")
    -- ON DELETE NO ACTION
    -- ON UPDATE NO ACTION);

    
--     CREATE TABLE  CANCIONES_PLAYLISTS (
--   "id_cancion" INT NOT NULL,
--   "id_playlist" INT NOT NULL,
--   PRIMARY KEY ("id_cancion", "id_playlist"),
--   CONSTRAINT "fk_CANCIONES_PLAYLISTS_CANCIONES1"
--     FOREIGN KEY ("id_cancion")
--     REFERENCES CANCIONES ("id_cancion")
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION,
--   CONSTRAINT "fk_CANCIONES_PLAYLISTS_PLAYLISTS1"
--     FOREIGN KEY ("id_playlist")
--     REFERENCES PLAYLISTS ("id_playlist")
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION);


--    insert into generos values(1,'heavy metal');
--    insert into generos values(2,'Rock');
--    insert into generos values(23,'Pop Español');


--    insert into artistas values(1,'Ozzy Osbourne');
--    insert into artistas values(2,'Creedence Clearwater Revival');
   
--    insert into albums values(1,'Cosmos factory',1970,2);
--    insert into albums values(2,'Pendulum',1970,2);
--    insert into albums values(3,'No More Tears',1991,1);

   
   
--    insert into ALBUMS_ARTISTA values(1,2);
--    insert into ALBUMS_ARTISTA values(2,2);
--    insert into ALBUMS_ARTISTA values(3,1);

--  insert into canciones (nombre,duracion,id_album) values('Ramble Tamble',330,1);
--        insert into canciones (nombre,duracion,id_album) values('Road to Nowhere',330,1);

  -- SE REINICIAN LAS SECUENCIAS SEGÚN LOS DATOS INICIALES
-- SELECT setval('public.generos_id_genero_seq', 2, true);
-- SELECT setval('public.artistas_id_artista_seq', 2, true);
-- SELECT setval('public.albums_id_album_seq', 3, true);


-- ALTER TABLE CANCIONES
-- ADD COLUMN numero_cancion INTEGER,
-- ADD COLUMN explicita BOOLEAN,
-- ADD COLUMN nombre_archivo VARCHAR(255);

-- ALTER TABLE ALBUMS
-- ADD COLUMN nombre_archivo VARCHAR(255);


-- DELETE  FROM usuarios WHERE id_usuario=3;
-- select * from roles;

-- insert into roles (nombre) values ('ADMIN');
-- insert into roles (nombre) values ('MEMBER_NORMAL');
-- insert into roles (nombre) values ('MEMBER_PREMIUM');
-- insert into roles (nombre) values ('ARTIST');


-- SELECT * FROM usuarios_roles;


-- select * from roles;

-- insert into usuarios_roles (id_rol,id_usuario) values(4,4);

-- DELETE FROM usuarios_roles WHERE id_rol=1 and id_usuario=4;

-- select * from roles;

-- select * from usuarios;

-- select * from generos;

    CREATE TABLE  ALBUMS_USUARIOS (
  "id_usuario" INT NOT NULL,
  "id_album" INT NOT NULL,
  "fecha_agregada" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY ("id_album", "id_usuario"),
  CONSTRAINT "fk_ALBUMS_USUARIO_USUARIO1"
    FOREIGN KEY ("id_usuario")
    REFERENCES USUARIOS ("id_usuario")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT "fk_USUARIO_ALBUMS_ALBUMS1"
    FOREIGN KEY ("id_album")
    REFERENCES ALBUMS ("id_album")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);