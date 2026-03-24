INSERT INTO Agencia (id_agencia, nombre) VALUES (1, 'Agencia Central de Noticias');
INSERT INTO Agencia (id_agencia, nombre) VALUES (2, 'Prensa Global');
INSERT INTO Agencia (id_agencia, nombre) VALUES (3, 'Agencia EFE Norte');
INSERT INTO Agencia (id_agencia, nombre) VALUES (4, 'Asturias Media');

INSERT INTO Empresa_Comunicacion (id_empresa, nombre, email) VALUES (1, 'Diario El Mañana', 'redaccion@elmanana.com');
INSERT INTO Empresa_Comunicacion (id_empresa, nombre, email) VALUES (2, 'Canal 24 Horas', 'noticias@canal24.tv');
INSERT INTO Empresa_Comunicacion (id_empresa, nombre, email) VALUES (3, 'Radio Gijón', 'contacto@radiogijon.es');
INSERT INTO Empresa_Comunicacion (id_empresa, nombre, email) VALUES (4, 'La Nueva España', 'lne@redaccion.es');
INSERT INTO Empresa_Comunicacion (id_empresa, nombre, email) VALUES (5, 'TeleAsturias', 'informativos@teleasturias.tv');
INSERT INTO Empresa_Comunicacion (id_empresa, nombre, email) VALUES (6, 'El Comercio', 'noticias@elcomercio.es');

INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero) VALUES (1, 'Ana Blanco', '600111222', 1, 'BASE');
INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero) VALUES (2, 'Matias Prats', '600333444', 1, 'BASE');
INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero) VALUES (3, 'Lois Lane', '600555666', 2, 'BASE');
INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero) VALUES (4, 'Clark Kent', '600777888', 2, 'BASE');
INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero) VALUES (5, 'Peter Parker', '600999000', 3, 'GRÁFICO');
INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero) VALUES (6, 'Eddie Brock', '600123456', 3, 'CAMARÓGRAFO');
INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero) VALUES (7, 'Sara Carbonero', '600654321', 4, 'BASE');
INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero) VALUES (8, 'Iñaki Gabilondo', '600987654', 4, 'BASE');
INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero) VALUES (9, 'Julia Otero', '600112233', 1, 'BASE');
INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero) VALUES (10, 'Jordi Evole', '600445566', 2, 'BASE');
INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero) VALUES (11, 'Vicente Valles', '600111333', 1, 'GRÁFICO');
INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero) VALUES (12, 'Monica Carrillo', '600222444', 2, 'CAMARÓGRAFO');


INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (1, 'Rueda de prensa del Alcalde', '2026-03-10', 1);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (2, 'Manifestacion por el clima', '2026-03-15', 2);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (3, 'Inauguracion del nuevo hospital', '2026-04-02', 1);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (4, 'Derbi asturiano en El Molinon', '2026-04-15', 4);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (5, 'Concierto en Laboral Ciudad de la Cultura', '2026-05-20', 4);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (6, 'Feria Internacional de Muestras (FIDMA)', '2026-08-01', 3);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (7, 'Festival Internacional de Cine de Gijon', '2026-11-15', 3);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (8, 'Huelga del sector del metal', '2026-03-22', 2);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (9, 'Rueda de prensa de la oposicion', '2026-09-10', 1);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (10, 'Apertura curso universitario', '2026-09-15', 2);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (11, 'Cumbre tecnologica (Minyana)', '2026-10-20', 1);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (12, 'Cena de gala tecnologica (Noche)', '2026-10-20', 1);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (20, 'Gala de los Premios Princesa', '2026-10-25', 1);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (21, 'Inauguracion AVE Asturias', '2026-11-29', 4);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (97, 'Rueda de prensa de mañana', '2026-12-31', 1);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (98, 'Gala benéfica de noche', '2026-12-31', 1);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (99, 'Evento Libre de Prueba', '2026-12-01', 1);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (100, 'Otra Rueda de Prensa sin asignar', '2026-12-05', 1);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (101, 'Rueda de prensa sobre presupuestos', '2026-06-10', 1);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (102, 'Inauguracion exposicion de arte', '2026-06-12', 1);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (103, 'Debate electoral autonomico', '2026-05-28', 2);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (104, 'Festival de la Sidra en Nava', '2026-07-10', 3);
INSERT INTO Evento (id_evento, descripcion, fecha, id_agencia) VALUES (105, 'Presentacion de hub tecnologico', '2026-09-05', 4);

INSERT INTO Asignacion (id_evento, id_reportero) VALUES (1, 1);
INSERT INTO Asignacion (id_evento, id_reportero) VALUES (1, 9);
INSERT INTO Asignacion (id_evento, id_reportero) VALUES (2, 3);
INSERT INTO Asignacion (id_evento, id_reportero) VALUES (2, 10);
INSERT INTO Asignacion (id_evento, id_reportero) VALUES (3, 2);
INSERT INTO Asignacion (id_evento, id_reportero) VALUES (4, 7);
INSERT INTO Asignacion (id_evento, id_reportero) VALUES (4, 8);
INSERT INTO Asignacion (id_evento, id_reportero) VALUES (5, 7);
INSERT INTO Asignacion (id_evento, id_reportero) VALUES (6, 5);
INSERT INTO Asignacion (id_evento, id_reportero) VALUES (7, 6);
INSERT INTO Asignacion (id_evento, id_reportero) VALUES (8, 4);
INSERT INTO Asignacion (id_evento, id_reportero) VALUES (11, 11);
INSERT INTO Asignacion (id_evento, id_reportero) VALUES (20, 1);
INSERT INTO Asignacion (id_evento, id_reportero) VALUES (21, 7);

INSERT INTO Reportaje (id_reportaje, id_evento, id_reportero, titulo, subtitulo, cuerpo, fecha_entrega) VALUES (1, 1, 1, 'El Alcalde promete nuevas ayudas', 'Se destinarán 5 millones', 'Durante la mañana de hoy, el alcalde ha prometido en rueda de prensa...', '2026-03-11 10:30:00');
INSERT INTO Reportaje (id_reportaje, id_evento, id_reportero, titulo, subtitulo, cuerpo, fecha_entrega) VALUES (2, 2, 3, 'Multitudinaria marcha ecologista', 'Miles de personas toman las calles', 'La manifestación comenzó a las 12:00 de forma pacífica...', '2026-03-16 09:15:00');
INSERT INTO Reportaje (id_reportaje, id_evento, id_reportero, titulo, subtitulo, cuerpo, fecha_entrega) VALUES (3, 4, 8, 'Empate sin goles en el derbi', 'Mucha tension y poco futbol', 'El partido disputado en El Molinón terminó con reparto de puntos...', '2026-04-16 08:00:00');
INSERT INTO Reportaje (id_reportaje, id_evento, id_reportero, titulo, subtitulo, cuerpo, fecha_entrega) VALUES (4, 6, 5, 'Record de asistencia en la Feria', 'Mas de 100.000 visitantes', 'El recinto ferial Luis Adaro cerró sus puertas con cifras históricas...', '2026-08-02 12:00:00');
INSERT INTO Reportaje (id_reportaje, id_evento, id_reportero, titulo, subtitulo, cuerpo, fecha_entrega) VALUES (5, 8, 4, 'Seguimiento masivo en la huelga', 'Los sindicatos celebran el exito', 'Las fábricas amanecieron paradas en la primera jornada de paros...', '2026-03-23 11:45:00');
INSERT INTO Reportaje (id_reportaje, id_evento, id_reportero, titulo, subtitulo, cuerpo, fecha_entrega) VALUES (20, 20, 1, 'Premios Princesa: Todo un Exito', 'Glamour en Oviedo', 'La ceremonia se desarrollo sin incidentes y con gran expectacion...', '2026-10-26 10:00:00');
INSERT INTO Reportaje (id_reportaje, id_evento, id_reportero, titulo, subtitulo, cuerpo, fecha_entrega) VALUES (21, 21, 7, 'El AVE llega por fin a la region', 'Conexion directa con Madrid', 'El primer tren llego a la estacion puntual entre aplausos...', '2026-11-29 14:00:00');


INSERT INTO Version_Reportaje (id_version, id_reportaje, fecha_cambio, hora_cambio, subtitulo_guardado, cuerpo_guardado) VALUES (1, 1, '2026-03-10', '18:00:00', 'Borrador preliminar', 'Texto en redaccion por parte de Ana...');
INSERT INTO Version_Reportaje (id_version, id_reportaje, fecha_cambio, hora_cambio, subtitulo_guardado, cuerpo_guardado) VALUES (2, 1, '2026-03-11', '09:00:00', 'Correccion de cifras', 'Durante la mañana de hoy, el alcalde prometió...');
INSERT INTO Version_Reportaje (id_version, id_reportaje, fecha_cambio, hora_cambio, subtitulo_guardado, cuerpo_guardado) VALUES (3, 3, '2026-04-15', '20:30:00', 'Cronica primera parte', 'Primer tiempo aburrido en el estadio...');
INSERT INTO Version_Reportaje (id_version, id_reportaje, fecha_cambio, hora_cambio, subtitulo_guardado, cuerpo_guardado) VALUES (4, 4, '2026-08-01', '19:00:00', 'Datos iniciales', 'Primeros recuentos de visitantes de la FIDMA...');

INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (1, 1, 'ACEPTADO', true);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (1, 2, 'PENDIENTE', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (2, 3, 'RECHAZADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (4, 4, 'ACEPTADO', true);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (4, 5, 'ACEPTADO', true);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (4, 6, 'PENDIENTE', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (6, 6, 'ACEPTADO', true);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (6, 1, 'RECHAZADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (7, 5, 'PENDIENTE', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (8, 2, 'ACEPTADO', true);

-- Para probar la "Agencia Central de Noticias" (Evento 1 ya tiene reportaje)
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (1, 3, 'ACEPTADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (1, 4, 'ACEPTADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (1, 5, 'ACEPTADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (20, 1, 'ACEPTADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (20, 2, 'ACEPTADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (20, 6, 'ACEPTADO', false);


-- Para probar "Prensa Global" (Los eventos 2 y 8 ya tienen reportaje)
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (2, 1, 'ACEPTADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (2, 2, 'ACEPTADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (8, 4, 'ACEPTADO', false);

-- Para probar "Agencia EFE Norte" (El evento 6 ya tiene reportaje)
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (6, 2, 'ACEPTADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (6, 3, 'ACEPTADO', false);

-- Para probar "Asturias Media" (El evento 4 ya tiene reportaje)
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (4, 1, 'ACEPTADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (4, 2, 'ACEPTADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (21, 3, 'ACEPTADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (21, 4, 'ACEPTADO', false);
INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) VALUES (21, 5, 'ACEPTADO', false);

-- ===== EJEMPLOS DE LAS NUEVAS TABLAS/CAMPOS =====

-- Temáticas nuevas
INSERT INTO Tematica (id_tematica, nombre) VALUES (1, 'Politica');
INSERT INTO Tematica (id_tematica, nombre) VALUES (2, 'Economia');
INSERT INTO Tematica (id_tematica, nombre) VALUES (3, 'Deportes');
INSERT INTO Tematica (id_tematica, nombre) VALUES (4, 'Cultura');
INSERT INTO Tematica (id_tematica, nombre) VALUES (5, 'Tecnologia');

-- Relación Reportero <-> Temática
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (1, 1);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (2, 2);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (3, 1);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (4, 2);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (5, 3);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (6, 4);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (10, 5);

-- Relación Evento <-> Temática
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (1, 1);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (2, 1);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (4, 3);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (5, 4);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (11, 5);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (20, 4);

-- Relación Empresa <-> Temática
INSERT INTO Empresa_Tematica (id_empresa, id_tematica) VALUES (1, 1);
INSERT INTO Empresa_Tematica (id_empresa, id_tematica) VALUES (1, 2);
INSERT INTO Empresa_Tematica (id_empresa, id_tematica) VALUES (2, 3);
INSERT INTO Empresa_Tematica (id_empresa, id_tematica) VALUES (3, 1);
INSERT INTO Empresa_Tematica (id_empresa, id_tematica) VALUES (4, 4);
INSERT INTO Empresa_Tematica (id_empresa, id_tematica) VALUES (5, 3);
INSERT INTO Empresa_Tematica (id_empresa, id_tematica) VALUES (6, 5);

-- Ejemplos de precio en Evento
UPDATE Evento SET precio = 120.50 WHERE id_evento = 1;
UPDATE Evento SET precio = 250.00 WHERE id_evento = 4;
UPDATE Evento SET precio = 80.00 WHERE id_evento = 11;

-- Ejemplos de estado_revision en Reportaje
UPDATE Reportaje SET estado_revision = 'EN_REVISION' WHERE id_reportaje = 1;
UPDATE Reportaje SET estado_revision = 'REVISADO' WHERE id_reportaje = 2;
UPDATE Reportaje SET estado_revision = 'ENTREGADO' WHERE id_reportaje = 3;

-- Comentarios de revisión
INSERT INTO Comentario_Revision (id_comentario, id_reportaje, id_reportero, comentario, fecha_hora)
VALUES (1, 1, 2, 'Falta confirmar la cifra total de ayudas.', '2026-03-11 11:00:00');
INSERT INTO Comentario_Revision (id_comentario, id_reportaje, id_reportero, comentario, fecha_hora)
VALUES (2, 1, 9, 'Añadir contexto historico en el segundo párrafo.', '2026-03-11 12:10:00');
INSERT INTO Comentario_Revision (id_comentario, id_reportaje, id_reportero, comentario, fecha_hora)
VALUES (3, 2, 10, 'Buen enfoque, revisar ortografía en el cierre.', '2026-03-16 10:00:00');

-- Multimedia asociada a reportajes
INSERT INTO Multimedia (id_multimedia, id_reportaje, id_reportero, tipo, ruta, estado)
VALUES (1, 1, 1, 'IMAGEN', '/media/reportaje1/portada.jpg', 'DEFINITIVO');
INSERT INTO Multimedia (id_multimedia, id_reportaje, id_reportero, tipo, ruta, estado)
VALUES (2, 1, 5, 'VIDEO', '/media/reportaje1/corte-entrevista.mp4', 'BORRADOR');
INSERT INTO Multimedia (id_multimedia, id_reportaje, id_reportero, tipo, ruta, estado)
VALUES (3, 4, 5, 'IMAGEN', '/media/reportaje4/fidma-publico.jpg', 'DEFINITIVO');

-- Ejemplos de descargado en Ofrecimiento
UPDATE Ofrecimiento SET descargado = true WHERE id_evento = 1 AND id_empresa = 1;
UPDATE Ofrecimiento SET descargado = true WHERE id_evento = 4 AND id_empresa = 4;
UPDATE Ofrecimiento SET descargado = false WHERE id_evento = 20 AND id_empresa = 2;

-- ===== AJUSTE HU: cobertura completa de temáticas =====
-- Nuevas temáticas para enriquecer clasificación
INSERT INTO Tematica (id_tematica, nombre) VALUES (6, 'Salud');
INSERT INTO Tematica (id_tematica, nombre) VALUES (7, 'Sociedad');
INSERT INTO Tematica (id_tematica, nombre) VALUES (8, 'Educacion');
INSERT INTO Tematica (id_tematica, nombre) VALUES (9, 'Infraestructuras');
INSERT INTO Tematica (id_tematica, nombre) VALUES (10, 'Medioambiente');

-- Todos los eventos tienen al menos una temática (y varios con múltiples)
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (3, 6);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (3, 7);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (6, 2);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (6, 7);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (7, 4);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (7, 7);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (8, 2);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (8, 7);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (9, 1);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (10, 8);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (12, 5);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (12, 4);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (21, 9);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (97, 1);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (98, 7);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (99, 5);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (100, 1);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (101, 2);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (101, 1);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (102, 4);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (103, 1);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (104, 4);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (105, 5);
INSERT INTO Evento_Tematica (id_evento, id_tematica) VALUES (105, 2);

-- Todos los reporteros tienen al menos una temática (y varios con múltiples)
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (1, 2);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (2, 1);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (3, 10);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (4, 7);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (5, 5);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (6, 7);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (7, 3);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (8, 4);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (9, 1);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (10, 1);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (11, 5);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (12, 4);

-- ===== HU 34086: freelance =====
INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero, es_freelance) VALUES (13, 'Lucia Campos', '600555111', 1, 'BASE', true);
INSERT INTO Reportero (id_reportero, nombre, telefono, id_agencia, tipo_reportero, es_freelance) VALUES (14, 'Mario Vega', '600555222', 2, 'BASE', true);

INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (13, 1);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (13, 2);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (14, 5);
INSERT INTO Reportero_Tematica (id_reportero, id_tematica) VALUES (14, 4);

UPDATE Evento SET disponible_freelance = true WHERE id_evento = 1;
UPDATE Evento SET disponible_freelance = true WHERE id_evento = 6;
UPDATE Evento SET disponible_freelance = true WHERE id_evento = 11;
UPDATE Evento SET disponible_freelance = true WHERE id_evento = 12;
UPDATE Evento SET disponible_freelance = true WHERE id_evento = 20;

INSERT INTO Preferencia_Freelance (id_evento, id_reportero, estado_preferencia) VALUES (1, 13, 'INTERESADO');
INSERT INTO Preferencia_Freelance (id_evento, id_reportero, estado_preferencia) VALUES (6, 13, 'DUDANDO');
INSERT INTO Preferencia_Freelance (id_evento, id_reportero, estado_preferencia) VALUES (11, 14, 'INTERESADO');
INSERT INTO Preferencia_Freelance (id_evento, id_reportero, estado_preferencia) VALUES (20, 14, 'NO_INTERESADO');
