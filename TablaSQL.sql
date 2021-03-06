 DROP TABLE IF EXISTS Usuario;
CREATE TABLE Usuario(
	id_usuario INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
	username VARCHAR(35),
	password VARCHAR(35),
	rango INTEGER,
);

CREATE TABLE Indicador(
	id_indicador INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
	nombre VARCHAR(50),
	expresion VARCHAR(500),
	id_usuario INTEGER,
);

CREATE TABLE Cotizacion(
	id_cotizacion INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
	empresa VARCHAR(70),
	cuenta VARCHAR(70),
	anio INTEGER,
	valor FLOAT,
);

CREATE TABLE Condicion(
	id_condicion INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
	id_indicador INTEGER,
	tipo INTEGER,
	constante FLOAT,
	id_metodologia INTEGER,
);

INSERT INTO Usuario VALUES (1,'admin','admin',0);
INSERT INTO Usuario VALUES (2,'asd','asd',1);