-- ============================================================
-- Script de datos iniciales — Proyecto Streaming (tema #13)
-- José Miguel Rojas Bacco
--
-- Uso:
--   1. Levanta el proyecto al menos una vez para que Hibernate
--      cree las tablas (ddl-auto=update)
--   2. Ejecuta este script:
--      docker exec -i streaming_db mysql -ujose -pjose1234 streaming_db < datos.sql
-- ============================================================

-- Limpiar datos previos (respeta el orden por FK)
DELETE FROM episodios;
DELETE FROM series;

-- Reiniciar autoincrement
ALTER TABLE episodios AUTO_INCREMENT = 1;
ALTER TABLE series AUTO_INCREMENT = 1;

-- ──────────────────────────────────────────────
-- SERIES
-- ──────────────────────────────────────────────
INSERT INTO series (titulo, genero, plataforma, ano_estreno) VALUES
('Breaking Bad',       'Drama',           'Netflix',  2008),
('The Last of Us',     'Ciencia Ficción', 'HBO Max',  2023),
('Stranger Things',    'Terror',          'Netflix',  2016),
('The Crown',          'Historia',        'Netflix',  2016),
('House of the Dragon','Fantasía',        'HBO Max',  2022),
('Dark',               'Ciencia Ficción', 'Netflix',  2017),
('Severance',          'Drama',           'Apple TV', 2022);

-- ──────────────────────────────────────────────
-- EPISODIOS — Breaking Bad (id=1)
-- ──────────────────────────────────────────────
INSERT INTO episodios (numero, temporada, titulo, duracion_min, fecha_emision, serie_id) VALUES
(1, 1, 'Pilot',                        58, '2008-01-20', 1),
(2, 1, 'Cat''s in the Bag',            48, '2008-01-27', 1),
(3, 1, 'And the Bag''s in the River',  48, '2008-02-10', 1),
(4, 1, 'Cancer Man',                   48, '2008-02-17', 1),
(1, 2, 'Seven Thirty-Seven',           47, '2009-03-08', 1),
(2, 2, 'Down',                         47, '2009-03-15', 1),
(3, 2, 'Bit by a Dead Bee',            47, '2009-03-22', 1);

-- ──────────────────────────────────────────────
-- EPISODIOS — The Last of Us (id=2)
-- ──────────────────────────────────────────────
INSERT INTO episodios (numero, temporada, titulo, duracion_min, fecha_emision, serie_id) VALUES
(1, 1, 'When You''re Lost in the Darkness', 81, '2023-01-15', 2),
(2, 1, 'Infected',                          55, '2023-01-22', 2),
(3, 1, 'Long Long Time',                    76, '2023-01-29', 2),
(4, 1, 'Please Hold to My Hand',            52, '2023-02-05', 2),
(5, 1, 'Endure and Survive',                58, '2023-02-12', 2);

-- ──────────────────────────────────────────────
-- EPISODIOS — Stranger Things (id=3)
-- ──────────────────────────────────────────────
INSERT INTO episodios (numero, temporada, titulo, duracion_min, fecha_emision, serie_id) VALUES
(1, 1, 'The Vanishing of Will Byers',    47, '2016-07-15', 3),
(2, 1, 'The Weirdo on Maple Street',     55, '2016-07-15', 3),
(3, 1, 'Holly, Jolly',                   51, '2016-07-15', 3),
(1, 2, 'Madmax',                         48, '2017-10-27', 3),
(2, 2, 'Trick or Treat, Freak',          56, '2017-10-27', 3);

-- ──────────────────────────────────────────────
-- EPISODIOS — The Crown (id=4)
-- ──────────────────────────────────────────────
INSERT INTO episodios (numero, temporada, titulo, duracion_min, fecha_emision, serie_id) VALUES
(1, 1, 'Wolfenden',      58, '2016-11-04', 4),
(2, 1, 'Hyde Park Gate', 57, '2016-11-04', 4),
(3, 1, 'Windsor',        62, '2016-11-04', 4);

-- ──────────────────────────────────────────────
-- EPISODIOS — House of the Dragon (id=5)
-- ──────────────────────────────────────────────
INSERT INTO episodios (numero, temporada, titulo, duracion_min, fecha_emision, serie_id) VALUES
(1, 1, 'The Heirs of the Dragon', 66, '2022-08-21', 5),
(2, 1, 'The Rogue Prince',        54, '2022-08-28', 5),
(3, 1, 'Second of His Name',      63, '2022-09-04', 5),
(4, 1, 'King of the Narrow Sea',  60, '2022-09-11', 5);

-- ──────────────────────────────────────────────
-- EPISODIOS — Dark (id=6)
-- ──────────────────────────────────────────────
INSERT INTO episodios (numero, temporada, titulo, duracion_min, fecha_emision, serie_id) VALUES
(1, 1, 'Secrets',      51, '2017-12-01', 6),
(2, 1, 'Lies',         52, '2017-12-01', 6),
(3, 1, 'Past and Present', 50, '2017-12-01', 6);

-- ──────────────────────────────────────────────
-- EPISODIOS — Severance (id=7)
-- ──────────────────────────────────────────────
INSERT INTO episodios (numero, temporada, titulo, duracion_min, fecha_emision, serie_id) VALUES
(1, 1, 'Good News About Hell',    38, '2022-02-18', 7),
(2, 1, 'Half Loop',               39, '2022-02-25', 7),
(3, 1, 'In Perpetuity',           40, '2022-03-04', 7),
(4, 1, 'The You You Are',         41, '2022-03-11', 7);