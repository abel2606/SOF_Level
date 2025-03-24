Use sof;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE cita;
TRUNCATE TABLE contrato;
TRUNCATE TABLE paquete;
TRUNCATE TABLE cliente;
TRUNCATE TABLE usuario;
TRUNCATE TABLE material;
TRUNCATE TABLE cita_material;
TRUNCATE TABLE tipofoto;
TRUNCATE TABLE paquetetipofoto;
TRUNCATE TABLE compra;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO usuario (id, contrasena, nombre_usuario, tipo_usuario, correo, nombre_persona, telefono) 
VALUES (1, 'segura123', 'carlosL', 'FOTOGRAFO', 'carlos@example.com', 'Carlos López', '555-6789'),
(2, 'segura123', 'AntionB', 'FOTOGRAFO', 'carlos@example.com', 'Antonio Banderas', '666-1234'),
(3, 'segura123', 'Admin', 'ADMINISTRADOR', 'carlos@example.com', 'Raul ortega', '777-0909');

INSERT INTO cliente (id, nombre, telefono, correo) 
VALUES (1, 'Juan Pérez', '555-1234', 'juan@example.com'),
(2, 'Ricardo alcaraz', '999-1737', 'richi@example.com');

INSERT INTO paquete (id, precio, nombre)
VALUES (1, 2500.0, 'Sesión de fotos premium'),
(2, 1500.0, 'Sesión de fotos normal');

INSERT INTO contrato (id, tematica, estado, folio, cliente_id, paquete_id)
VALUES (1, 'Boda', 'Activo', 'FOLIO123', 1,1),
(2, 'XV', 'Activo', 'FOLIO456', 1,2);

INSERT INTO cita (id, codigo, extras, fecha_hora_fin, fecha_hora_inicio, lugar, contrato_id, fotografo_id)
VALUES (1, '7498364386', 'Venir de azul', "2024-03-18 12:00:00","2024-03-18 10:00:00", "Jardin Principal",1,1),
(2, '4789436833', 'Llegar muy temprano', "2024-03-18 12:00:00","2024-03-18 10:00:00", "Jardin secundario",1,2);

INSERT INTO material(id, cantidad,nombre)
Values (1,1,'globo verde'),
(2,1,'serpetina'),
(3,1,'globo rojo'),
(4,1,'pancarta'),
(5,1,'listones'),
(6,1,'moños');