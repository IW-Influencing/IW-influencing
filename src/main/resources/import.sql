-- 
-- El contenido de este fichero se cargará al arrancar la aplicación, suponiendo que uses
-- 		application-default ó application-externaldb en modo 'create'
--

-- Usuario de ejemplo con nombre = a y contraseña = aa  
INSERT INTO usuario(id,activo,nombre,password,roles,apellidos,edad,tags) VALUES (
	1, 1, 'a', 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
	'ADMIN',
	'Abundio Ejémplez',
	23,
	'ropa'
	
);

-- Otro usuario de ejemplo con username = b y contraseña = aa  
INSERT INTO usuario(id,activo,nombre,password,roles,apellidos,edad,tags) VALUES (
	2, 1, 'b', 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
	'USER',
	'Berta Muéstrez',
	22,
	'fiesta'
);


INSERT INTO propuesta(id,descripcion,nombre,sueldo,tags,empresa_id) VALUES (
	1,
	'Realiza publicidad en metro',
	'Metropubli',
	100,
	'Ciudad',
	2
);

INSERT INTO candidatura(id,aceptada,estado,candidato_id,propuesta_id) VALUES (
	1,
	true,
	'EN_CURSO',
	1,
	1
);
