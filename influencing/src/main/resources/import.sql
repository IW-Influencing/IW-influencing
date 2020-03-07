-- 
-- El contenido de este fichero se cargará al arrancar la aplicación, suponiendo que uses
-- 		application-default ó application-externaldb en modo 'create'
--

-- Usuario de ejemplo con nombre = b y contraseña = aa  
INSERT INTO usuario(id,activo,nombre,contraseña,rol,apellidos,edad,tags) VALUES (
	1, 1, 'a', 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
	'ADMIN',
	'Abundio Ejémplez',
	23,
	'ropa'
	
);

-- Otro usuario de ejemplo con username = b y contraseña = aa  
INSERT INTO usuario(id,activo,nombre,contraseña,rol,apellidos,edad,tags) VALUES (
	2, 1, 'b', 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
	'USER',
	'Berta Muéstrez',
	22,
	'fiesta'
);