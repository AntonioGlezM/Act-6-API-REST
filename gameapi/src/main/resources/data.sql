-- =============================================
-- Datos iniciales de ejemplo para GameAPI (MySQL)
-- Se usa INSERT IGNORE: si la fila (por su id/PK) ya existe,
-- MySQL la ignora en vez de dar error. Así no se duplican
-- datos en cada arranque al usar ddl-auto=update.
-- =============================================

-- Estudios de desarrollo (se fija el id para que las FK sean estables)
INSERT IGNORE INTO estudios (id, nombre, pais, anio_fundacion) VALUES (1, 'Nintendo', 'Japón', 1889);
INSERT IGNORE INTO estudios (id, nombre, pais, anio_fundacion) VALUES (2, 'Rockstar Games', 'Estados Unidos', 1998);
INSERT IGNORE INTO estudios (id, nombre, pais, anio_fundacion) VALUES (3, 'CD Projekt Red', 'Polonia', 1994);
INSERT IGNORE INTO estudios (id, nombre, pais, anio_fundacion) VALUES (4, 'FromSoftware', 'Japón', 1986);
INSERT IGNORE INTO estudios (id, nombre, pais, anio_fundacion) VALUES (5, 'Naughty Dog', 'Estados Unidos', 1984);

-- Géneros
INSERT IGNORE INTO generos (id, nombre, descripcion) VALUES (1, 'Acción', 'Juegos centrados en la acción y el combate');
INSERT IGNORE INTO generos (id, nombre, descripcion) VALUES (2, 'RPG', 'Juegos de rol con progresión de personaje');
INSERT IGNORE INTO generos (id, nombre, descripcion) VALUES (3, 'Aventura', 'Juegos de exploración y narrativa');
INSERT IGNORE INTO generos (id, nombre, descripcion) VALUES (4, 'Mundo abierto', 'Juegos con un mundo extenso para explorar libremente');
INSERT IGNORE INTO generos (id, nombre, descripcion) VALUES (5, 'Plataformas', 'Juegos de saltos y plataformas');

-- Juegos (estudio_id referencia a los estudios de arriba)
INSERT IGNORE INTO juegos (id, titulo, descripcion, precio, fecha_lanzamiento, estudio_id) VALUES (1, 'The Legend of Zelda: Tears of the Kingdom', 'Secuela de Breath of the Wild', 69.99, '2023-05-12', 1);
INSERT IGNORE INTO juegos (id, titulo, descripcion, precio, fecha_lanzamiento, estudio_id) VALUES (2, 'Grand Theft Auto V', 'Juego de mundo abierto ambientado en Los Santos', 29.99, '2013-09-17', 2);
INSERT IGNORE INTO juegos (id, titulo, descripcion, precio, fecha_lanzamiento, estudio_id) VALUES (3, 'The Witcher 3: Wild Hunt', 'RPG de mundo abierto basado en las novelas de Sapkowski', 39.99, '2015-05-19', 3);
INSERT IGNORE INTO juegos (id, titulo, descripcion, precio, fecha_lanzamiento, estudio_id) VALUES (4, 'Elden Ring', 'RPG de acción en mundo abierto', 59.99, '2022-02-25', 4);
INSERT IGNORE INTO juegos (id, titulo, descripcion, precio, fecha_lanzamiento, estudio_id) VALUES (5, 'The Last of Us Part II', 'Aventura de acción post-apocalíptica', 39.99, '2020-06-19', 5);
INSERT IGNORE INTO juegos (id, titulo, descripcion, precio, fecha_lanzamiento, estudio_id) VALUES (6, 'Super Mario Odyssey', 'Juego de plataformas en 3D', 49.99, '2017-10-27', 1);

-- Relación Juego-Género (tabla intermedia juego_genero)
INSERT IGNORE INTO juego_genero (juego_id, genero_id) VALUES (1, 3);  -- Zelda -> Aventura
INSERT IGNORE INTO juego_genero (juego_id, genero_id) VALUES (1, 4);  -- Zelda -> Mundo abierto
INSERT IGNORE INTO juego_genero (juego_id, genero_id) VALUES (2, 1);  -- GTA V -> Acción
INSERT IGNORE INTO juego_genero (juego_id, genero_id) VALUES (2, 4);  -- GTA V -> Mundo abierto
INSERT IGNORE INTO juego_genero (juego_id, genero_id) VALUES (3, 2);  -- Witcher 3 -> RPG
INSERT IGNORE INTO juego_genero (juego_id, genero_id) VALUES (3, 4);  -- Witcher 3 -> Mundo abierto
INSERT IGNORE INTO juego_genero (juego_id, genero_id) VALUES (4, 1);  -- Elden Ring -> Acción
INSERT IGNORE INTO juego_genero (juego_id, genero_id) VALUES (4, 2);  -- Elden Ring -> RPG
INSERT IGNORE INTO juego_genero (juego_id, genero_id) VALUES (4, 4);  -- Elden Ring -> Mundo abierto
INSERT IGNORE INTO juego_genero (juego_id, genero_id) VALUES (5, 1);  -- TLOU2 -> Acción
INSERT IGNORE INTO juego_genero (juego_id, genero_id) VALUES (5, 3);  -- TLOU2 -> Aventura
INSERT IGNORE INTO juego_genero (juego_id, genero_id) VALUES (6, 5);  -- Mario -> Plataformas