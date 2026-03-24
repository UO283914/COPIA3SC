DROP TABLE IF EXISTS Comentario_Revision;
DROP TABLE IF EXISTS Preferencia_Freelance;
DROP TABLE IF EXISTS Multimedia;
DROP TABLE IF EXISTS Evento_Tematica;
DROP TABLE IF EXISTS Reportero_Tematica;
DROP TABLE IF EXISTS Empresa_Tematica;
DROP TABLE IF EXISTS Tematica;
DROP TABLE IF EXISTS Ofrecimiento;
DROP TABLE IF EXISTS Version_Reportaje;
DROP TABLE IF EXISTS Reportaje;
DROP TABLE IF EXISTS Asignacion;
DROP TABLE IF EXISTS Empresa_Comunicacion;
DROP TABLE IF EXISTS Evento;
DROP TABLE IF EXISTS Reportero;
DROP TABLE IF EXISTS Agencia;

CREATE TABLE Agencia (
    id_agencia INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre varchar(255)
);

CREATE TABLE Tematica (
    id_tematica INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre varchar(255) UNIQUE
);

CREATE TABLE Reportero (
    id_reportero INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre varchar(255),
    telefono varchar(255),
    id_agencia INTEGER,
    tipo_reportero varchar(50),
    es_freelance boolean DEFAULT false,
    FOREIGN KEY (id_agencia) REFERENCES Agencia (id_agencia)
);

CREATE TABLE Reportero_Tematica (
    id_reportero INTEGER,
    id_tematica INTEGER,
    PRIMARY KEY (id_reportero, id_tematica),
    FOREIGN KEY (id_reportero) REFERENCES Reportero (id_reportero),
    FOREIGN KEY (id_tematica) REFERENCES Tematica (id_tematica)
);

CREATE TABLE Evento (
    id_evento INTEGER PRIMARY KEY AUTOINCREMENT,
    descripcion varchar(255),
    fecha date,
    id_agencia INTEGER,
    precio REAL DEFAULT 0.0,
    disponible_freelance boolean DEFAULT false,
    FOREIGN KEY (id_agencia) REFERENCES Agencia (id_agencia)
);

CREATE TABLE Evento_Tematica (
    id_evento INTEGER,
    id_tematica INTEGER,
    PRIMARY KEY (id_evento, id_tematica),
    FOREIGN KEY (id_evento) REFERENCES Evento (id_evento),
    FOREIGN KEY (id_tematica) REFERENCES Tematica (id_tematica)
);

CREATE TABLE Empresa_Comunicacion (
    id_empresa INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre varchar(255),
    email varchar(255)
);

CREATE TABLE Empresa_Tematica (
    id_empresa INTEGER,
    id_tematica INTEGER,
    PRIMARY KEY (id_empresa, id_tematica),
    FOREIGN KEY (id_empresa) REFERENCES Empresa_Comunicacion (id_empresa),
    FOREIGN KEY (id_tematica) REFERENCES Tematica (id_tematica)
);

CREATE TABLE Asignacion (
    id_evento INTEGER,
    id_reportero INTEGER,
    PRIMARY KEY (id_evento, id_reportero),
    FOREIGN KEY (id_evento) REFERENCES Evento (id_evento),
    FOREIGN KEY (id_reportero) REFERENCES Reportero (id_reportero)
);

CREATE TABLE Preferencia_Freelance (
    id_evento INTEGER,
    id_reportero INTEGER,
    estado_preferencia varchar(50),
    fecha_actualizacion datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_evento, id_reportero),
    FOREIGN KEY (id_evento) REFERENCES Evento (id_evento),
    FOREIGN KEY (id_reportero) REFERENCES Reportero (id_reportero)
);

CREATE TABLE Reportaje (
    id_reportaje INTEGER PRIMARY KEY AUTOINCREMENT,
    id_evento INTEGER UNIQUE,
    id_reportero INTEGER,
    titulo varchar(255) UNIQUE,
    subtitulo varchar(255),
    cuerpo text,
    fecha_entrega datetime,
    estado_revision varchar(50) DEFAULT 'ENTREGADO',
    FOREIGN KEY (id_evento) REFERENCES Evento (id_evento),
    FOREIGN KEY (id_reportero) REFERENCES Reportero (id_reportero)
);

CREATE TABLE Comentario_Revision (
    id_comentario INTEGER PRIMARY KEY AUTOINCREMENT,
    id_reportaje INTEGER,
    id_reportero INTEGER,
    comentario text,
    fecha_hora datetime DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_reportaje) REFERENCES Reportaje (id_reportaje),
    FOREIGN KEY (id_reportero) REFERENCES Reportero (id_reportero)
);

CREATE TABLE Version_Reportaje (
    id_version INTEGER PRIMARY KEY AUTOINCREMENT,
    id_reportaje INTEGER,
    fecha_cambio date,
    hora_cambio time,
    subtitulo_guardado varchar(255),
    cuerpo_guardado text,
    FOREIGN KEY (id_reportaje) REFERENCES Reportaje (id_reportaje)
);

CREATE TABLE Multimedia (
    id_multimedia INTEGER PRIMARY KEY AUTOINCREMENT,
    id_reportaje INTEGER,
    id_reportero INTEGER,
    tipo varchar(50),
    ruta varchar(500) UNIQUE,
    estado varchar(50) DEFAULT 'BORRADOR',
    FOREIGN KEY (id_reportaje) REFERENCES Reportaje (id_reportaje),
    FOREIGN KEY (id_reportero) REFERENCES Reportero (id_reportero)
);

CREATE TABLE Ofrecimiento (
    id_evento INTEGER,
    id_empresa INTEGER,
    estado varchar(255),
    tiene_acceso boolean DEFAULT false,
    descargado boolean DEFAULT false,
    PRIMARY KEY (id_evento, id_empresa),
    FOREIGN KEY (id_evento) REFERENCES Evento (id_evento),
    FOREIGN KEY (id_empresa) REFERENCES Empresa_Comunicacion (id_empresa)
);
