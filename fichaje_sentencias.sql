create table empresa(
	id_empresa int(8),
	nombre_empresa varchar(30),
	nif_empresa varchar(12),
	ciudad_empresa varchar(20),
	primary key (id_empresa)
);

create table dia(
	id_dia int(1),
	nombre_dia varchar(9),
	primary key (id_dia)
);

create table horario(
	id_horario int(8),
	hora_inicio_horario float(4,2),
	hora_final_horario float(4,2),
	tiempo_descanso_horario float(4,2),
	primary key (id_horario)
);

create table trabajador(
	id_trabajador int(8),
	nombre_trabajador varchar(20),
	apellido_trabajador varchar(40),
	dni_trabajador varchar(12),
	fecha_nacimiento_trabajador date,
	id_empresa int(8),
	primary key (id_trabajador),
	foreign key (id_empresa) references empresa (id_empresa)
);

create table horario_trabajador(
	id_horario_trabajador int(8),
	id_trabajador int(8),
	id_horario int(8),
	id_dia int(1),
	primary key (id_horario_trabajador),
	foreign key (id_trabajador) references trabajador (id_trabajador),
	foreign key (id_horario) references horario (id_horario),
	foreign key (id_dia) references dia (id_dia)
);

create table fichaje(
	id_fichaje int(16),
	id_horario_trabajador int(8),
	horas_trabajadas_fichaje float(4,2),
	primary key (id_fichaje),
	foreign key (id_horario_trabajador) references horario_trabajador (id_horario_trabajador)
);







---------- CREAR ROLES ----------

-- Rol admin --
CREATE ROLE admin;
GRANT ALL PRIVILEGES ON *.* TO admin;

-- Rol Trabajador --
CREATE ROLE trabajador;
GRANT SELECT ON horario TO trabajador;
GRANT SELECT ON vista_horarios_trabajadores TO trabajador;
GRANT SELECT ON horario_trabajador TO trabajador;
GRANT INSERT ON fichaje TO trabajador;
GRANT SELECT ON dia TO trabajador;

-- Rol empresa --
CREATE ROLE empresa;
GRANT SELECT ON dia TO empresa;
GRANT SELECT ON trabajadores_por_empresa TO empresa;
GRANT INSERT, UPDATE, DELETE ON horario TO empresa;
GRANT SELECT, INSERT, UPDATE, DELETE ON horario_trabajador TO empresa;
GRANT SELECT, INSERT, UPDATE, DELETE ON trabajador TO empresa;






---------- CREAR USUAIROS ----------

-- Admin --
CREATE USER administrador IDENTIFIED By 'administrador';
GRANT admin to administrador;
SET DEFAULT ROLE admin FOR administrador;
 
-- Empresas --
CREATE USER AcmeInc IDENTIFIED BY 'AcmeInc';
GRANT empresa TO AcmeInc;
SET DEFAULT ROLE empresa FOR AcmeInc;

CREATE USER GlobexCorporation IDENTIFIED BY 'GlobexCorporation';
GRANT empresa TO GlobexCorporation;
SET DEFAULT ROLE empresa FOR GlobexCorporation;

CREATE USER UmbrellaCorporation IDENTIFIED BY 'UmbrellaCorporation';
GRANT empresa TO UmbrellaCorporation;
SET DEFAULT ROLE empresa FOR UmbrellaCorporation;

-- Trabajadores --
CREATE USER JuanPerezGarcia IDENTIFIED BY 'JuanPerezGarcia';
GRANT trabajador TO JuanPerezGarcia;
SET DEFAULT ROLE trabajador FOR JuanPerezGarcia;

CREATE USER MariaGonzalezLopez IDENTIFIED BY 'MariaGonzalezLopez';
GRANT trabajador TO MariaGonzalezLopez;
SET DEFAULT ROLE trabajador FOR MariaGonzalezLopez;

CREATE USER PedroSanchezMartinez IDENTIFIED BY 'PedroSanchezMartinez';
GRANT trabajador TO PedroSanchezMartinez;
SET DEFAULT ROLE trabajador FOR PedroSanchezMartinez;

CREATE USER SaraHernandez IDENTIFIED BY 'SaraHernandez';
GRANT trabajador TO SaraHernandez;
SET DEFAULT ROLE trabajador FOR SaraHernandez;

CREATE USER JuanRamirez IDENTIFIED BY 'JuanRamirez';
GRANT trabajador TO JuanRamirez;
SET DEFAULT ROLE trabajador FOR JuanRamirez;

CREATE USER MariaGarcia IDENTIFIED BY 'MariaGarcia';
GRANT trabajador TO MariaGarcia;
SET DEFAULT ROLE trabajador FOR MariaGarcia;



---------- CREAR VISTAS ----------

-- VER HORARIO TRABAJADORES --
CREATE VIEW vista_horarios_trabajadores AS
SELECT t.nombre_trabajador, t.apellido_trabajador, d.nombre_dia, h.hora_inicio_horario, h.hora_final_horario, h.tiempo_descanso_horario
FROM trabajador t
JOIN horario_trabajador ht ON t.id_trabajador = ht.id_trabajador
JOIN horario h ON ht.id_horario = h.id_horario
JOIN dia d ON ht.id_dia = d.id_dia;

-- VER TRABAJADORES POR EMPRESA-- 
CREATE VIEW trabajadores_por_empresa AS
SELECT empresa.nombre_empresa, CONCAT(trabajador.nombre_trabajador, ' ', trabajador.apellido_trabajador) AS trabajador
FROM empresa
INNER JOIN trabajador ON empresa.id_empresa = trabajador.id_empresa
ORDER BY empresa.nombre_empresa;




-- Procedimientos --
ALTER TABLE empresa 
ADD COLUMN numero_trabajadores int NOT NULL

DELIMITER $$
	CREATE PROCEDURE actualizar_numero_trabajadores(IN id INT)
		BEGIN
			UPDATE empresa SET numero_trabajadores = (
				SELECT COUNT(*) FROM trabajador WHERE id_empresa = id
			)
			WHERE id_empresa = id;
		END $$
DELIMITER ;

--

ALTER TABLE trabajador
ADD COLUMN horas_fichadas_trabajador DOUBLE(10,0) NOT NULL;

DELIMITER $$
CREATE PROCEDURE sumar_horas_fichadas(IN id INT)
	BEGIN
		DECLARE horas_totales DOUBLE;
		SELECT SUM(horas_fichadas) INTO horas_totales FROM fichaje WHERE id_trabajador = id;
		UPDATE trabajador SET horas_fichadas_trabajador = horas_totales WHERE id_trabajador = id;
	END $$
DELIMITER ;

-- TRIGGERS --
DELIMITER $$
	CREATE TRIGGER actualizar_numero_trabajadores_trigger
		AFTER INSERT ON trabajador
		FOR EACH ROW
		BEGIN
			CALL actualizar_numero_trabajadores(NEW.id_empresa);
		END $$
DELIMITER ;

DELIMITER $$
	CREATE TRIGGER actualizar_horas_fichadas_trabajador 
		AFTER INSERT ON fichaje
		FOR EACH ROW
		BEGIN
			CALL sumar_horas_fichadas(NEW.id_horario_trabajador);
		END$$
DELIMITER ;