-- =============================================
-- Datos iniciales de ejemplo para GameAPI
-- Se cargan automáticamente al arrancar con H2
-- =============================================

-- Estudios de desarrollo
INSERT INTO estudios (nombre, pais, anio_fundacion) VALUES ('Nintendo', 'Japón', 1889);
INSERT INTO estudios (nombre, pais, anio_fundacion) VALUES ('Rockstar Games', 'Estados Unidos', 1998);
INSERT INTO estudios (nombre, pais, anio_fundacion) VALUES ('CD Projekt Red', 'Polonia', 1994);
INSERT INTO estudios (nombre, pais, anio_fundacion) VALUES ('FromSoftware', 'Japón', 1986);
INSERT INTO estudios (nombre, pais, anio_fundacion) VALUES ('Naughty Dog', 'Estados Unidos', 1984);

-- Géneros
INSERT INTO generos (nombre, descripcion) VALUES ('Acción', 'Juegos centrados en la acción y el combate');
INSERT INTO generos (nombre, descripcion) VALUES ('RPG', 'Juegos de rol con progresión de personaje');
INSERT INTO generos (nombre, descripcion) VALUES ('Aventura', 'Juegos de exploración y narrativa');
INSERT INTO generos (nombre, descripcion) VALUES ('Mundo abierto', 'Juegos con un mundo extenso para explorar libremente');
INSERT INTO generos (nombre, descripcion) VALUES ('Plataformas', 'Juegos de saltos y plataformas');

-- Juegos (estudio_id referencia a los estudios insertados arriba)
INSERT INTO juegos (titulo, descripcion, precio, fecha_lanzamiento, estudio_id) VALUES ('The Legend of Zelda: Tears of the Kingdom', 'Secuela de Breath of the Wild', 69.99, '2023-05-12', 1);
INSERT INTO juegos (titulo, descripcion, precio, fecha_lanzamiento, estudio_id) VALUES ('Grand Theft Auto V', 'Juego de mundo abierto ambientado en Los Santos', 29.99, '2013-09-17', 2);
INSERT INTO juegos (titulo, descripcion, precio, fecha_lanzamiento, estudio_id) VALUES ('The Witcher 3: Wild Hunt', 'RPG de mundo abierto basado en las novelas de Sapkowski', 39.99, '2015-05-19', 3);
INSERT INTO juegos (titulo, descripcion, precio, fecha_lanzamiento, estudio_id) VALUES ('Elden Ring', 'RPG de acción en mundo abierto', 59.99, '2022-02-25', 4);
INSERT INTO juegos (titulo, descripcion, precio, fecha_lanzamiento, estudio_id) VALUES ('The Last of Us Part II', 'Aventura de acción post-apocalíptica', 39.99, '2020-06-19', 5);
INSERT INTO juegos (titulo, descripcion, precio, fecha_lanzamiento, estudio_id) VALUES ('Super Mario Odyssey', 'Juego de plataformas en 3D', 49.99, '2017-10-27', 1);

-- Relación Juego-Género (tabla intermedia juego_genero)
INSERT INTO juego_genero (juego_id, genero_id) VALUES (1, 3);  -- Zelda -> Aventura
INSERT INTO juego_genero (juego_id, genero_id) VALUES (1, 4);  -- Zelda -> Mundo abierto
INSERT INTO juego_genero (juego_id, genero_id) VALUES (2, 1);  -- GTA V -> Acción
INSERT INTO juego_genero (juego_id, genero_id) VALUES (2, 4);  -- GTA V -> Mundo abierto
INSERT INTO juego_genero (juego_id, genero_id) VALUES (3, 2);  -- Witcher 3 -> RPG
INSERT INTO juego_genero (juego_id, genero_id) VALUES (3, 4);  -- Witcher 3 -> Mundo abierto
INSERT INTO juego_genero (juego_id, genero_id) VALUES (4, 1);  -- Elden Ring -> Acción
INSERT INTO juego_genero (juego_id, genero_id) VALUES (4, 2);  -- Elden Ring -> RPG
INSERT INTO juego_genero (juego_id, genero_id) VALUES (4, 4);  -- Elden Ring -> Mundo abierto
INSERT INTO juego_genero (juego_id, genero_id) VALUES (5, 1);  -- TLOU2 -> Acción
INSERT INTO juego_genero (juego_id, genero_id) VALUES (5, 3);  -- TLOU2 -> Aventura
INSERT INTO juego_genero (juego_id, genero_id) VALUES (6, 5);  -- Mario -> Plataformas
