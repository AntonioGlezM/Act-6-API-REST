-- =============================================
-- Datos iniciales de ejemplo para GameAPI (MySQL)
-- Idempotente: solo inserta si la fila no existe ya,
-- para no duplicar datos en cada arranque (ddl-auto=update)
-- =============================================

-- Estudios de desarrollo (se fija el id para que las FK sean estables)
INSERT INTO estudios (id, nombre, pais, anio_fundacion)
SELECT * FROM (SELECT 1, 'Nintendo', 'Japón', 1889) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM estudios WHERE id = 1);
INSERT INTO estudios (id, nombre, pais, anio_fundacion)
SELECT * FROM (SELECT 2, 'Rockstar Games', 'Estados Unidos', 1998) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM estudios WHERE id = 2);
INSERT INTO estudios (id, nombre, pais, anio_fundacion)
SELECT * FROM (SELECT 3, 'CD Projekt Red', 'Polonia', 1994) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM estudios WHERE id = 3);
INSERT INTO estudios (id, nombre, pais, anio_fundacion)
SELECT * FROM (SELECT 4, 'FromSoftware', 'Japón', 1986) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM estudios WHERE id = 4);
INSERT INTO estudios (id, nombre, pais, anio_fundacion)
SELECT * FROM (SELECT 5, 'Naughty Dog', 'Estados Unidos', 1984) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM estudios WHERE id = 5);

-- Géneros
INSERT INTO generos (id, nombre, descripcion)
SELECT * FROM (SELECT 1, 'Acción', 'Juegos centrados en la acción y el combate') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM generos WHERE id = 1);
INSERT INTO generos (id, nombre, descripcion)
SELECT * FROM (SELECT 2, 'RPG', 'Juegos de rol con progresión de personaje') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM generos WHERE id = 2);
INSERT INTO generos (id, nombre, descripcion)
SELECT * FROM (SELECT 3, 'Aventura', 'Juegos de exploración y narrativa') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM generos WHERE id = 3);
INSERT INTO generos (id, nombre, descripcion)
SELECT * FROM (SELECT 4, 'Mundo abierto', 'Juegos con un mundo extenso para explorar libremente') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM generos WHERE id = 4);
INSERT INTO generos (id, nombre, descripcion)
SELECT * FROM (SELECT 5, 'Plataformas', 'Juegos de saltos y plataformas') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM generos WHERE id = 5);

-- Juegos (estudio_id referencia a los estudios de arriba)
INSERT INTO juegos (id, titulo, descripcion, precio, fecha_lanzamiento, estudio_id)
SELECT * FROM (SELECT 1, 'The Legend of Zelda: Tears of the Kingdom', 'Secuela de Breath of the Wild', 69.99, '2023-05-12', 1) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM juegos WHERE id = 1);
INSERT INTO juegos (id, titulo, descripcion, precio, fecha_lanzamiento, estudio_id)
SELECT * FROM (SELECT 2, 'Grand Theft Auto V', 'Juego de mundo abierto ambientado en Los Santos', 29.99, '2013-09-17', 2) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM juegos WHERE id = 2);
INSERT INTO juegos (id, titulo, descripcion, precio, fecha_lanzamiento, estudio_id)
SELECT * FROM (SELECT 3, 'The Witcher 3: Wild Hunt', 'RPG de mundo abierto basado en las novelas de Sapkowski', 39.99, '2015-05-19', 3) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM juegos WHERE id = 3);
INSERT INTO juegos (id, titulo, descripcion, precio, fecha_lanzamiento, estudio_id)
SELECT * FROM (SELECT 4, 'Elden Ring', 'RPG de acción en mundo abierto', 59.99, '2022-02-25', 4) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM juegos WHERE id = 4);
INSERT INTO juegos (id, titulo, descripcion, precio, fecha_lanzamiento, estudio_id)
SELECT * FROM (SELECT 5, 'The Last of Us Part II', 'Aventura de acción post-apocalíptica', 39.99, '2020-06-19', 5) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM juegos WHERE id = 5);
INSERT INTO juegos (id, titulo, descripcion, precio, fecha_lanzamiento, estudio_id)
SELECT * FROM (SELECT 6, 'Super Mario Odyssey', 'Juego de plataformas en 3D', 49.99, '2017-10-27', 1) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM juegos WHERE id = 6);

-- Relación Juego-Género (tabla intermedia juego_genero)
INSERT INTO juego_genero (juego_id, genero_id)
SELECT * FROM (SELECT 1, 3) AS tmp WHERE NOT EXISTS (SELECT 1 FROM juego_genero WHERE juego_id = 1 AND genero_id = 3);
INSERT INTO juego_genero (juego_id, genero_id)
SELECT * FROM (SELECT 1, 4) AS tmp WHERE NOT EXISTS (SELECT 1 FROM juego_genero WHERE juego_id = 1 AND genero_id = 4);
INSERT INTO juego_genero (juego_id, genero_id)
SELECT * FROM (SELECT 2, 1) AS tmp WHERE NOT EXISTS (SELECT 1 FROM juego_genero WHERE juego_id = 2 AND genero_id = 1);
INSERT INTO juego_genero (juego_id, genero_id)
SELECT * FROM (SELECT 2, 4) AS tmp WHERE NOT EXISTS (SELECT 1 FROM juego_genero WHERE juego_id = 2 AND genero_id = 4);
INSERT INTO juego_genero (juego_id, genero_id)
SELECT * FROM (SELECT 3, 2) AS tmp WHERE NOT EXISTS (SELECT 1 FROM juego_genero WHERE juego_id = 3 AND genero_id = 2);
INSERT INTO juego_genero (juego_id, genero_id)
SELECT * FROM (SELECT 3, 4) AS tmp WHERE NOT EXISTS (SELECT 1 FROM juego_genero WHERE juego_id = 3 AND genero_id = 4);
INSERT INTO juego_genero (juego_id, genero_id)
SELECT * FROM (SELECT 4, 1) AS tmp WHERE NOT EXISTS (SELECT 1 FROM juego_genero WHERE juego_id = 4 AND genero_id = 1);
INSERT INTO juego_genero (juego_id, genero_id)
SELECT * FROM (SELECT 4, 2) AS tmp WHERE NOT EXISTS (SELECT 1 FROM juego_genero WHERE juego_id = 4 AND genero_id = 2);
INSERT INTO juego_genero (juego_id, genero_id)
SELECT * FROM (SELECT 4, 4) AS tmp WHERE NOT EXISTS (SELECT 1 FROM juego_genero WHERE juego_id = 4 AND genero_id = 4);
INSERT INTO juego_genero (juego_id, genero_id)
SELECT * FROM (SELECT 5, 1) AS tmp WHERE NOT EXISTS (SELECT 1 FROM juego_genero WHERE juego_id = 5 AND genero_id = 1);
INSERT INTO juego_genero (juego_id, genero_id)
SELECT * FROM (SELECT 5, 3) AS tmp WHERE NOT EXISTS (SELECT 1 FROM juego_genero WHERE juego_id = 5 AND genero_id = 3);
INSERT INTO juego_genero (juego_id, genero_id)
SELECT * FROM (SELECT 6, 5) AS tmp WHERE NOT EXISTS (SELECT 1 FROM juego_genero WHERE juego_id = 6 AND genero_id = 5);
