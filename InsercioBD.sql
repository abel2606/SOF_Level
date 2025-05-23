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
VALUES (1, 'segura123', 'carlosL', 'FOTOGRAFO', 'carlos_carl@gmail.com', 'Carlos López', '6622790333'),
(2, 'segura123', 'AntionB', 'FOTOGRAFO', 'antonioGX@gmail.com', 'Antonio Banderas', '6644082351'),
(3, 'segura123', 'Admin', 'FOTOGRAFO', 'raul323@gmail.com', 'Raul ortega', '6641183331'),
(4, 'segura123', 'DanGG', 'FOTOGRAFO', 'antonioGX@gmail.com', 'Daneil Guzman', '6644542351'),
(5, 'segura123', 'MarG', 'FOTOGRAFO', 'mar28masa@gmail.com', 'Maria Gally', '6643453331');

#INSERT INTO cliente (id, nombre, telefono, correo, estado) 
#VALUES (1, 'Juan Pérez', '5551234567', 'juan@example.com'),
#(2, 'Ricardo alcaraz', '9991737123', 'richi@example.com');

INSERT INTO paquete (id, precio, nombre)
VALUES 
(1, 10000.0, 'Boda'),
(2, 8000.0, 'XV Años'),
(3, 3000.0, 'Smash Cake'),
(4, 2500.0, 'Fashion'),
(5, 2000.0, 'infantil'),
(6, 1500.0, 'Maternidad');

#INSERT INTO contrato (id, tematica, estado, folio, cliente_id, paquete_id, fecha_inicio, fecha_termino)
#VALUES (1, 'Boda', 'Activo', 'FOLIO123', 1,1,'2025-04-10 09:00:00','2025-04-10 07:00:00'),
#(2, 'XV', 'Activo', 'FOLIO456', 1,2,'2025-04-10 07:00:00','2025-04-10 07:00:00');

#INSERT INTO cita (id, codigo, extras, fecha_hora_fin, fecha_hora_inicio, lugar, contrato_id, fotografo_id)
#VALUES 
#-- Citas para el 28/05/2025
#(9, '1234567890', 'Traer accesorios', '2025-04-10 09:00:00', '2025-04-10 07:00:00', 'Parque Central', 1, 2),
#(10, '0987654321', 'Llevar identificación', '2025-04-10 12:00:00', '2025-04-10 10:00:00', 'Plaza Mayor', 1, 2),
#(11, '5678901234', 'Usar ropa formal', '2025-04-10 15:00:00', '2025-04-10 13:00:00', 'Estudio Fotográfico', 1, 2),

#-- Citas para el 29/05/2025
#(12, '2345678901', 'Confirmar asistencia', '2025-04-05 10:00:00', '2025-04-05 08:00:00', 'Hotel Central', 1, 2),
#(13, '3456789012', 'No usar colores brillantes', '2025-04-05 13:00:00', '2025-04-05 11:00:00', 'Museo Histórico', 1, 2),

-- Cita para el 10/06/2025
#(14, '4567890123', 'Traer equipo propio', '2025-04-02 16:00:00', '2025-04-02 14:00:00', 'Centro de Convenciones', 1, 2);

INSERT INTO material(id, cantidad,nombre)
Values (1,30,'globos de colores'),
(2,75,'Spray para globos'),
(3,20,'Letras de fomi'),
(4,78,'coronas de plástico'),
(5,33,'Metros de tela');