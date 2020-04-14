-- 
-- El contenido de este fichero se cargará al arrancar la aplicación, suponiendo que uses
-- 		application-default ó application-externaldb en modo 'create'
--

-- Usuario de ejemplo con nombre = a y contraseña = aa  

INSERT INTO usuario(id,activo,nombre_cuenta,nombre,password,roles,apellidos,edad,tags,estado,score) VALUES (
	1, 1, 'a','a',
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
	'ADMIN',
	'Abundio Ejemplez',
	23,
	'ropa',
	'Estado del usuario',
	5
);

-- Otro usuario de ejemplo con username = b y contraseña = aa  


INSERT INTO usuario(id,activo,nombre_cuenta,nombre,password,roles,apellidos,edad,tags,estado,score) VALUES (
	2, 1, 'b','b',
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
	'INFLUENCER',
	'Berta Muestrez',
	22,
	'fiesta',
	'Estado del usuario',
	4
);

INSERT INTO usuario(id,activo,nombre,nombre_cuenta,password,roles,apellidos,edad,tags,estado,score) VALUES (
	3, 1, 'c', 'c', 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
	'INFLUENCER',
	'Sanchez Garcia',
	22,
	'videojuegos',
	'Estado del usuario',
	4
);

INSERT INTO usuario(id,activo,nombre,nombre_cuenta,password,roles,apellidos,edad,tags,estado,score) VALUES (
	4, 1, 'd', 'd',
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
	'EMPRESA',
	'Martin Gimeno',
	22,
	'comida',
	'Estado del usuario',
	3
);

INSERT INTO propuesta(id,descripcion,nombre,sueldo,tags,empresa_id) VALUES (
	1,
	'Realizar publicidad en metro',
	'Metropubli',
	100,
	'Ciudad',
	2
);

INSERT INTO propuesta(id,descripcion,nombre,sueldo,tags,empresa_id) VALUES (
	2,
	'Realizar publicidad en metro 2',
	'Metropubli',
	200,
	'Ciudad',
	2
);
INSERT INTO propuesta(id,descripcion,nombre,sueldo,tags,empresa_id) VALUES (
	3,
	'Publicitar festival',
	'Festipubli',
	500,
	'Musica',
	4
);


INSERT INTO candidatura(id,aceptada,estado,candidato_id,propuesta_id) VALUES (
	1,
	true,
	'EN_CURSO',
	1,
	1
);

INSERT INTO candidatura(id,aceptada,estado,candidato_id,propuesta_id) VALUES (
	2,
	false,
	'EN_CURSO',
	2,
	3
);

INSERT INTO candidatura(id,aceptada,estado,candidato_id,propuesta_id) VALUES (
	3,
	true,
	'FINALIZADA',
	1,
	3
);

INSERT INTO candidatura(id,aceptada,estado,candidato_id,propuesta_id) VALUES (
	4,
	false,
	'EN_CURSO',
	2,
	2
);

INSERT INTO evento(id, descripcion, tipo, fecha, leido, emisor_id, receptor_id) VALUES(
	1, 'se ha registrado un usuario', 'ADMINISTRACION','2017-07-23', false,
	1,
	2
);

INSERT INTO evento(id, descripcion, tipo, fecha, leido, emisor_id, receptor_id) VALUES(
	2, 'se ha registrado otro usuario', 'ADMINISTRACION','2017-07-23', false,
	1,
	2
);