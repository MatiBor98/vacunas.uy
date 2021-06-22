ALTER SEQUENCE asigid RESTART WITH 1;
ALTER SEQUENCE dosisid RESTART WITH 1;
ALTER SEQUENCE intervaloid RESTART WITH 1;
ALTER SEQUENCE reservaid RESTART WITH 1;
ALTER SEQUENCE sequenciaagendaid RESTART WITH 1;
ALTER SEQUENCE sequenciaetapaid RESTART WITH 1;
ALTER SEQUENCE sequenciaturnoid RESTART WITH 1;

insert into vacuna (nombre, cantdosis, dosisseparaciondias, inmunidadmeses) values ('Coronavac', 2, 14, 8); -- Coronavac 2 dosis, 14 dias -> Coronavirus
insert into vacuna (nombre, cantdosis, dosisseparaciondias, inmunidadmeses) values ('Pfizer', 2, 17, 8); -- Pfizer 2 dosis, 17 dias -> Coronavirus
insert into vacuna (nombre, cantdosis, dosisseparaciondias, inmunidadmeses) values ('Astrazeneca', 1, 0, 8); -- Astrazeneca 1 dosis -> Coronavirus
insert into vacuna (nombre, cantdosis, dosisseparaciondias, inmunidadmeses) values ('Gripevac', 1, 0, 8); -- Gripevac 1 dosis -> Gripe 2021
insert into enfermedad (nombre, descripcion) values ('Coronavirus', 'COVID-19'); -- Coronavirus -> 3 vacunas
insert into enfermedad (nombre, descripcion) values ('Gripe 2021', 'Gripe estacional'); -- Gripe 2021 ->  1 vacuna
insert into vacuna_enfermedad (vacunas_nombre, enfermedades_nombre) values ('Coronavac', 'Coronavirus');
insert into vacuna_enfermedad (vacunas_nombre, enfermedades_nombre) values ('Pfizer', 'Coronavirus');
insert into vacuna_enfermedad (vacunas_nombre, enfermedades_nombre) values ('Astrazeneca', 'Coronavirus');
insert into vacuna_enfermedad (vacunas_nombre, enfermedades_nombre) values ('Gripevac', 'Gripe 2021');

--Plan vacunacion covid 19, 2020/1/1-2023/12/31, Coronavirus
insert into planvacunacion(nombre, inicio, fin, enfermedadnombre) values ( 'Plan vacunacion covid 19', date '2020-01-01', date '2023-12-31', 'Coronavirus');
--Anti gripe,2021/1/1-2021/12/31, Gripe 2021
insert into planvacunacion(nombre, inicio, fin, enfermedadnombre) values ( 'Anti gripe', date '2021-01-01', date '2021-12-31', 'Gripe 2021');
-- etapa 1, Plan vacunacion covid 19, Pfizer, 2020/1/1-2023/12/31 (SALUD)
insert into etapa(id, descripcion, inicio, fin, mayorigual, menorigual, plannombre, vacuna_nombre) values (nextval('sequenciaetapaid'), 'Etapa vacunacion Personal de la Salud', date '2020-01-01', date '2023-12-31', null, null, 'Plan vacunacion covid 19', 'Pfizer');
-- etapa 2, Plan vacunacion covid 19, Astrazenecas, 2020/1/1-2023/12/31 (SALUD Y EDUCACION)
insert into etapa(id, descripcion, inicio, fin, mayorigual, menorigual, plannombre, vacuna_nombre) values (nextval('sequenciaetapaid'), 'Etapa vacunacion Personal de la Salud y Enseñanza', date '2020-01-01', date '2023-12-31', null, null, 'Plan vacunacion covid 19', 'Astrazeneca');
-- etapa 3, Plan vacunacion covid 19, Coronavac, 2020/1/1-2023/12/31
insert into etapa(id, descripcion, inicio, fin, mayorigual, menorigual, plannombre, vacuna_nombre) values (nextval('sequenciaetapaid'), 'Etapa vacunacion General Coronavirus', date '2020-01-01', date '2023-12-31', 20, 70, 'Plan vacunacion covid 19', 'Coronavac');
-- etapa 4, Anti gripe, Gripevac, 2021/1/1-2021/12/31
insert into etapa(id, descripcion, inicio, fin, mayorigual, menorigual, plannombre, vacuna_nombre) values (nextval('sequenciaetapaid'), 'Etapa vacunacion General Gripe', date '2021-01-01', date '2021-12-31', 20, 70, 'Anti gripe', 'Gripevac');

insert into etapa_filtroempleoen (etapa_id, filtroempleoen) values (1, 'SALUD');
insert into etapa_filtroempleoen (etapa_id, filtroempleoen) values (2, 'SALUD');
insert into etapa_filtroempleoen (etapa_id, filtroempleoen) values (2, 'EDUCACION');

/*
insert into vacunatorio (nombre, ciudad, departamento, direccion, ubicacion) values ('COSEM Punta Carretas', 'Montevideo', 'Montevideo', 'José Ellauri 461'
, ST_GeomFromText('POINT(-34.9218912 -56.1583385)', 4326)
);
insert into vacunatorio (nombre, ciudad, departamento, direccion, ubicacion) values ('Hospital De Clínicas', 'Montevideo', 'Montevideo', 'Av. Italia 2982'
, ST_GeomFromText('POINT(-34.8913639 -56.1539307)', 4326)
);
insert into vacunatorio (nombre, ciudad, departamento, direccion, ubicacion) values ('Hospital de Artigas', 'Artigas', 'Artigas', 'Eduardo M. Castro'
, ST_GeomFromText('POINT(-30.4028157 -56.4608542)', 4326)
);
*/

-- vacunatorio COSEM Punta Carretas
insert into vacunatorio (nombre, ciudad, departamento, direccion) values ('COSEM Punta Carretas', 'Montevideo', 'Montevideo', 'José Ellauri 461');
-- vacunatorio Hospital de Clinicas
insert into vacunatorio (nombre, ciudad, departamento, direccion) values ('Hospital De Clínicas', 'Montevideo', 'Montevideo', 'Av. Italia 2982');
-- vacunatorio Hospital de Artigas
insert into vacunatorio (nombre, ciudad, departamento, direccion) values ('Hospital de Artigas', 'Artigas', 'Artigas', 'Eduardo M. Castro');


--Turno 1, Matutino, 8:00-12:00, COSEM Punta Carretas
insert into turno (id, inicio, fin, nombre, vacunatorio_nombre) values (nextval('sequenciaTurnoId'), time '08:00:00', time '12:00:00', 'Matutino', 'COSEM Punta Carretas');
--Turno 2, Vespertino, 13:00-17:00, COSEM Punta Carretas
insert into turno (id, inicio, fin, nombre, vacunatorio_nombre) values (nextval('sequenciaTurnoId'), time '13:00:00', time '17:00:00', 'Vespertino', 'COSEM Punta Carretas');
--Turno 3, Nocturno, 18:00-22:00, COSEM Punta Carretas
insert into turno (id, inicio, fin, nombre, vacunatorio_nombre) values (nextval('sequenciaTurnoId'), time '18:00:00', time '22:00:00', 'Nocturno', 'COSEM Punta Carretas');
--Turno 4, Matutino, 18:00-22:00, Hospital de Artigas
insert into turno (id, inicio, fin, nombre, vacunatorio_nombre) values (nextval('sequenciaTurnoId'), time '18:00:00', time '22:00:00', 'Matutino', 'Hospital de Artigas');
--Turno 5, Vespertino, 18:00-22:00, Hospital de Artigas
insert into turno (id, inicio, fin, nombre, vacunatorio_nombre) values (nextval('sequenciaTurnoId'), time '18:00:00', time '22:00:00', 'Vespertino', 'Hospital de Artigas');
--Turno 6, Nocturno, 18:00-22:00, Hospital de Artigas
insert into turno (id, inicio, fin, nombre, vacunatorio_nombre) values (nextval('sequenciaTurnoId'), time '18:00:00', time '22:00:00', 'Nocturno', 'Hospital de Artigas');


--Agenda 1, Turno 1(COSEM Punta Carretas), Etapa 3(Plan vacunacion covid 19, Coronavac)
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id) values (nextval('sequenciaAgendaId'), date '2020-01-01', null, 'Matutino', 3, 1);
--Turno matutino dia por medio
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (1, 5, time '08:00:00', time '12:00:00', 30, 1);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (1, 5, time '08:00:00', time '12:00:00', 30, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (1, 5, time '08:00:00', time '12:00:00', 30, 5);
-- Intervalo 1, 2022/10/19, Agenda 1(COSEM PC, etapa3, Coronavac)
insert into intervalo (id, fechayHora, agendaid) VALUES  (nextval('intervaloId'),TIMESTAMP '2022-10-19 10:30:00',1);
-- Intervalo 2, 2022/11/19, Agenda 1(COSEM PC, etapa3, Coronavac)
insert into intervalo (id, fechayHora, agendaid) VALUES  (nextval('intervaloId'),TIMESTAMP '2022-11-19 10:30:00',1);
--Intervalo 3, 2021/6/4, Agenda 1(COSEM PC, etapa3, Coronavac)
insert into intervalo (id, fechayHora, agendaid) VALUES  (nextval('intervaloId'), TIMESTAMP '2021-6-4 10:30:00',1);


--Agenda 2, Turno 2(COSEM Punta Carretas), Etapa 3(Plan vacunacion covid 19, Coronavac)
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id) values (nextval('sequenciaAgendaId'), date '2020-01-01', null, 'Vespertino', 3, 2);
--Turno vespertino dia por medio
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (2, 5, time '13:00:00', time '17:00:00', 30, 1);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (2, 5, time '13:00:00', time '17:00:00', 30, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (2, 5, time '13:00:00', time '17:00:00', 30, 5);


--Agenda 3, Turno 3(COSEM Punta Carretas), Etapa 3(Plan vacunacion covid 19, Coronavac)
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id) values (nextval('sequenciaAgendaId'), date '2020-01-01', null, 'Nocturno', 3, 3);
--Turno nocturno dia por media
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (3, 5, time '18:00:00', time '22:00:00', 30, 1);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (3, 5, time '18:00:00', time '22:00:00', 30, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (3, 5, time '18:00:00', time '22:00:00', 30, 5);


--Agenda 4, Turno 3(COSEM Punta Carretas), Etapa 1(Plan vacunacion covid 19, Pfizer)
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id) values (nextval('sequenciaAgendaId'), date '2020-01-01', null, 'Matutino', 1, 3);
--Turno matutino dia por medio
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (4, 5, time '18:00:00', time '22:00:00', 30, 0);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (4, 5, time '18:00:00', time '22:00:00', 30, 2);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (4, 5, time '18:00:00', time '22:00:00', 30, 4);
--Intervalo 4,  2021/6/7, Agenda 4(COSEM PC, etapa1,Pfizer)
insert into intervalo(id, fechayhora, agendaid) values (nextval('intervaloid'), timestamp '2021-06-07 18:00:00.000000', 4);
--Intervalo 5,  2021/6/28, Agenda 4(COSEM PC, etapa1,Pfizer)
insert into intervalo(id, fechayhora, agendaid) values (nextval('intervaloid'), timestamp ',2021-06-28 18:00:00.000000', 4);


--Agenda 5, Turno 1(COSEM Punta Carretas), Etapa 4(Anti gripe, Gripevac)
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id) values (nextval('sequenciaAgendaId'), date '2021-02-01', null, 'Matutino', 4, 1);
--Intervalo 6,  2021/6/7, Agenda 5(COSEM PC, etapa4,GripeVac)
insert into intervalo(id, fechayhora, agendaid) values (nextval('intervaloid'), timestamp '2021-06-07 18:00:00.000000', 5);


--Agenda 6, Turno 4(Hospital de Artigas), Etapa 4(Anti gripe, Gripevac)
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id) values (nextval('sequenciaAgendaId'), date '2021-01-01', null, 'Matutino', 4, 4);
--turno matutino dia por medio
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (4, 5, time '8:00:00', time '12:00:00', 30, 1);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (4, 5, time '8:00:00', time '12:00:00', 30, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (4, 5, time '8:00:00', time '12:00:00', 30, 5);


--Agenda 7, Turno 5(Hospital de Artigas), Etapa 4(Anti gripe, Gripevac)
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id) values (nextval('sequenciaAgendaId'), date '2021-01-01', null, 'Vespertino', 4, 5);
--turno vespertino dia por medio
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (5, 5, time '13:00:00', time '17:00:00', 30, 1);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (5, 5, time '13:00:00', time '17:00:00', 30, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (5, 5, time '13:00:00', time '17:00:00', 30, 5);
--Intervalo 7,  2021/8/19, Agenda 7(Artigas, etapa4,GripeVac)
insert into intervalo (id, fechayHora, agendaid) VALUES  (nextval('intervaloId'),TIMESTAMP '2021-8-19 17:30:00',7);
--Intervalo 8,  2021/6/19, Agenda 7(Artigas, etapa4,GripeVac)
insert into intervalo (id, fechayHora, agendaid) VALUES  (nextval('intervaloId'),TIMESTAMP '2021-6-19 19:00:00',7);

--Agenda 8, Turno 6(Hospital de Artigas), Etapa 4(Anti gripe, Gripevac)
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id) values (nextval('sequenciaAgendaId'), date '2021-01-01', null, 'Nocturno', 4, 6);
--turno nocturno dia por medio
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (6, 5, time '18:00:00', time '22:00:00', 30, 1);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (6, 5, time '18:00:00', time '22:00:00', 30, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (6, 5, time '18:00:00', time '22:00:00', 30, 5);






--Socio logistico UPS
insert into sociologistico (nombre, habilitado) values ('UPS', boolean 'false');

--Lote 2143, 500 dosis, vence 2020/09/09, UPS, Pfizer
insert into lote (numerolote, dosisdisponibles, fechavencimiento, sociologisticoid, vacuna_nombre) values (2143, 500, date '2020-09-09', 'UPS', 'Pfizer');
--Lote 1982, 1000 dosis, vence 2020/9/9, UPS, Pfizer
insert into lote (numerolote, dosisdisponibles, fechavencimiento, sociologisticoid, vacuna_nombre) values (1982, 1000, date '2020-09-09', 'UPS', 'Pfizer');
--Lote 2000, 3000 dosis, vence 2021/9/9, UPS, Pfizer, entregada
insert into lote (numerolote, dosisdisponibles, fechadespacho, fechaentrega, fechavencimiento, sociologisticoid, vacuna_nombre) values (2000, 3000, date '2021-05-19', date '2021-05-20', date '2021-09-09', 'UPS', 'Pfizer');
--Lote 2001, 500 dosis, vence 2021/9/9, UPS, Pfizer, entregada
insert into lote (numerolote, dosisdisponibles, fechadespacho, fechaentrega, fechavencimiento, sociologisticoid, vacuna_nombre) values (2001, 500, date '2021-06-02', date '2021-06-05', date '2021-09-09', 'UPS', 'Pfizer');
--Lote 4000, 1000 dosis, vence 2022/9/9, UPS, Gripevac, entregada
insert into lote (numerolote, dosisdisponibles, fechadespacho, fechaentrega, fechavencimiento, sociologisticoid, vacuna_nombre) values (4000, 1000, date '2021-04-19', date '2021-04-20', date '2022-09-09', 'UPS', 'Gripevac');
--Lote 4001, 600 dosis, vence 2022/9/9, UPS, Gripevac, entregada
insert into lote (numerolote, dosisdisponibles, fechadespacho, fechaentrega, fechavencimiento, sociologisticoid, vacuna_nombre) values (4001, 600, date '2021-05-02', date '2021-05-05', date '2022-09-09', 'UPS', 'Gripevac');
--Asignado lote 1982 a COSEM PC, Pfizer
insert into vacunatorio_lote (vacunatorio_nombre, lotes_numerolote) values ('COSEM Punta Carretas',1982);
--Asignado lote 2143 a COSEM PC, Pfizer
insert into vacunatorio_lote (vacunatorio_nombre, lotes_numerolote) values ('COSEM Punta Carretas',2143);
--Asignado lote 2000 a COSEM PC, Pfizer
insert into vacunatorio_lote (vacunatorio_nombre, lotes_numerolote) VALUES ('COSEM Punta Carretas', 2000);
--Asignado lote 2001 a COSEM PC, Pfizer
insert into vacunatorio_lote (vacunatorio_nombre, lotes_numerolote) VALUES ('COSEM Punta Carretas', 2001);
--Asignado lote 4000 a Artigas, Gripevac
insert into vacunatorio_lote (vacunatorio_nombre, lotes_numerolote) values ('Hospital de Artigas',4000);
--Asignado lote 4001 a Artigas, Gripevac
insert into vacunatorio_lote (vacunatorio_nombre, lotes_numerolote) values ('Hospital de Artigas',4001);

-- Ciudadano 52050756, Bruno, Masculino, Servicios
insert into ciudadano (rol, ci, email, fechanacimiento, nombre, sexo, trabajo) VALUES ('Ciudadano', 52050756,'bpardinas@bruno.com', date '1997-01-01', 'Bruno Pardiñas', 0, 11);
-- Vacunador 50550419, Nicolas, Masculino, Desempleado
insert into ciudadano (rol, ci, email, fechanacimiento, nombre, sexo, trabajo) VALUES ('Vacunador', 50550419,'nicolas@mail.com', date '1949-01-01', 'Nicolás San Martín', 0, 0);
-- Ciudadano 12341234, Ana, Femenino, Alimentacion
insert into ciudadano (rol, ci, email, fechanacimiento, nombre, sexo, trabajo) VALUES ('Ciudadano', 12341234,'ana@mail.com', date '1949-02-01', 'Ana Gutierrez', 1, 2);
-- Ciudadano 52667157, Ignacio, indefinido, indefinido
insert into ciudadano (rol, ci, email, nombre) VALUES ('Ciudadano', 52667157,'nacho.ote@mail.com', 'Ignacio Otero');

--Reserva 1, Pendiente, 50550419(Nico), intervalo 1(COSEM PC, Coronavac)
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid)  VALUES (nextval('reservaId'), 0, 1, 50550419, 1);
--Reserva 2, Pendiente, 50550419(Nico), intervalo 2(COSEM PC, Coronavac)
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaId'), 0, 2, 50550419, 2);
--Reserva 3, Vacunado, 52050756(Bruno), intervalo 4(COSEM PC, Pfizer)
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaid'), 2, 1, 52050756, 4);
--Reserva 4, Vacunado, 52050756(Bruno), intervalo 5(COSEM PC, Pfizer)
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaid'), 2, 2, 52050756, 5);
--Reserva 5, Vacunado, 12341234(Ana), intervalo 4(COSEM PC, Pfizer)
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaid'), 2, 1, 12341234, 4);
--Reserva 6, Pendiente, 50550419(Nico), intervalo 3(COSEM PC, Coronavac)
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaId'), 0, 1, 50550419, 3);
--Reserva 7, Pendiente, 50550419(Nico), intervalo 6(COSEM PC, Gripevac)
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaid'), 0, 1, 50550419, 6);
--Reserva 8, Vacunado, 52050756(Bruno), intervalo 6(COSEM PC, Gripevac)
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaid'), 2, 1, 52050756, 6);
--Reserva 9, Pendiente, 52667157(Nacho), intervalo 7(Artigas, Gripevac)
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid)  VALUES (nextval('reservaId'), 0, 1, 52667157, 7);
--Reserva 10, Pendiente, 50550419(Nico), intervalo 8(Artigas, Gripevac)
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid)  VALUES (nextval('reservaId'), 0, 1, 50550419, 8);

--aca terminan 4 vacunas suminstradas en COSEM (3 pfizer y 1 gripevac)

--datos

--2021/6/17 COSEM PC 1300 dosis de pfizer
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-17','COSEM Punta Carretas','Pfizer', 1300);
--2021/6/16 COSEM PC 1200 dosis de pfizer
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-16','COSEM Punta Carretas','Pfizer', 1200);
--2021/6/15 COSEM PC 1100 dosis de pfizer
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-15','COSEM Punta Carretas','Pfizer', 1100);
--2021/6/14 COSEM PC 800 dosis de pfizer
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-14','COSEM Punta Carretas','Pfizer', 800);
--2021/6/13 COSEM PC 900 dosis de pfizer
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-13','COSEM Punta Carretas','Pfizer', 900);
--2021/6/12 COSEM PC 500 dosis de pfizer
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-12','COSEM Punta Carretas','Pfizer', 500);
--2021/6/15 COSEM PC 200 dosis pfizer
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-11','COSEM Punta Carretas','Pfizer', 200);
--2021/6/17 COSEM PC 300 dosis coronavac
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-17','COSEM Punta Carretas','Coronavac', 300);
--2021/6/17 COSEM PC 200 dosis gripevac
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-17','COSEM Punta Carretas','Gripevac', 200);
--2021/6/15 Clinicas 350 dosis coronavac
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-17','Hospital De Clínicas','Coronavac', 350);

--autoridad 1@1
insert into usuariobo(tipodeusuario, email, password) VALUES ('autoridad', '1@1', '$2a$10$ubTOrDZ6ufLdhoHQT7Hmtu9CQvxsR7YI6VNOH9ye40jdOXh7ubq1i');

