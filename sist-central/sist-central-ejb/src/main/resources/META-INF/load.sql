insert into vacuna (nombre, cantdosis, dosisseparaciondias, inmunidadmeses) values ('Coronavac', 2, 14, 8);
insert into vacuna (nombre, cantdosis, dosisseparaciondias, inmunidadmeses) values ('Pfizer', 2, 17, 8);
insert into vacuna (nombre, cantdosis, dosisseparaciondias, inmunidadmeses) values ('Astrazeneca', 1, 0, 8);
insert into vacuna (nombre, cantdosis, dosisseparaciondias, inmunidadmeses) values ('Gripevac', 1, 0, 8);
insert into enfermedad (nombre, descripcion) values ('Coronavirus', 'COVID-19');
insert into enfermedad (nombre, descripcion) values ('Gripe 2021', 'Gripe estacional');
insert into vacuna_enfermedad (vacunas_nombre, enfermedades_nombre) values ('Coronavac', 'Coronavirus');
insert into vacuna_enfermedad (vacunas_nombre, enfermedades_nombre) values ('Pfizer', 'Coronavirus');
insert into vacuna_enfermedad (vacunas_nombre, enfermedades_nombre) values ('Astrazeneca', 'Coronavirus');
insert into vacuna_enfermedad (vacunas_nombre, enfermedades_nombre) values ('Gripevac', 'Gripe 2021');
insert into planvacunacion(nombre, inicio, fin, enfermedadnombre) values ( 'Plan vacunacion covid 19', date '2020-01-01', date '2023-12-31', 'Coronavirus');
insert into planvacunacion(nombre, inicio, fin, enfermedadnombre) values ( 'Anti gripe', date '2021-01-01', date '2021-12-31', 'Gripe 2021');
insert into etapa(id, descripcion, inicio, fin, mayorigual, menorigual, plannombre, vacuna_nombre) values (nextval('sequenciaetapaid'), 'Etapa vacunacion Personal de la Salud', date '2020-01-01', date '2023-12-31', null, null, 'Plan vacunacion covid 19', 'Pfizer');
insert into etapa(id, descripcion, inicio, fin, mayorigual, menorigual, plannombre, vacuna_nombre) values (nextval('sequenciaetapaid'), 'Etapa vacunacion Personal de la Salud y Enseñanza', date '2020-01-01', date '2023-12-31', null, null, 'Plan vacunacion covid 19', 'Astrazeneca');
insert into etapa(id, descripcion, inicio, fin, mayorigual, menorigual, plannombre, vacuna_nombre) values (nextval('sequenciaetapaid'), 'Etapa vacunacion General', date '2020-01-01', date '2023-12-31', 40, 70, 'Plan vacunacion covid 19', 'Coronavac');
insert into etapa(id, descripcion, inicio, fin, mayorigual, menorigual, plannombre, vacuna_nombre) values (nextval('sequenciaetapaid'), 'Etapa vacunacion General', date '2021-01-01', date '2021-12-31', 40, 70, 'Anti gripe', 'Gripevac');

insert into etapa_filtroempleoen (etapa_id, filtroempleoen) values (1, 'SALUD');
insert into etapa_filtroempleoen (etapa_id, filtroempleoen) values (2, 'SALUD');
insert into etapa_filtroempleoen (etapa_id, filtroempleoen) values (2, 'EDUCACION');
insert into vacunatorio (nombre, ciudad, departamento, direccion) values ('COSEM Punta Carretas', 'Montevideo', 'Montevideo', 'José Ellauri 461');
insert into vacunatorio (nombre, ciudad, departamento, direccion) values ('Hospital De Clínicas', 'Montevideo', 'Montevideo', 'Av. Italia 2982');
insert into vacunatorio (nombre, ciudad, departamento, direccion) values ('Hospital de Artigas', 'Artigas', 'Artigas', 'Eduardo M. Castro');
insert into turno (id, inicio, fin, nombre, vacunatorio_nombre) values (nextval('sequenciaTurnoId'), time '08:00:00', time '12:00:00', 'Matutino', 'COSEM Punta Carretas');
insert into turno (id, inicio, fin, nombre, vacunatorio_nombre) values (nextval('sequenciaTurnoId'), time '13:00:00', time '17:00:00', 'Vespertino', 'COSEM Punta Carretas');
insert into turno (id, inicio, fin, nombre, vacunatorio_nombre) values (nextval('sequenciaTurnoId'), time '18:00:00', time '22:00:00', 'Nocturno', 'COSEM Punta Carretas');
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id) values (nextval('sequenciaAgendaId'), date '2020-01-01', null, 'Matutino', 3, 1);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (1, 5, time '08:00:00', time '12:00:00', 30, 1);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (1, 5, time '08:00:00', time '12:00:00', 30, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (1, 5, time '08:00:00', time '12:00:00', 30, 5);
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id) values (nextval('sequenciaAgendaId'), date '2020-01-01', null, 'Vespertino', 3, 2);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (2, 5, time '13:00:00', time '17:00:00', 30, 1);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (2, 5, time '13:00:00', time '17:00:00', 30, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (2, 5, time '13:00:00', time '17:00:00', 30, 5);
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id) values (nextval('sequenciaAgendaId'), date '2020-01-01', null, 'Nocturno', 3, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (3, 5, time '18:00:00', time '22:00:00', 30, 1);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (3, 5, time '18:00:00', time '22:00:00', 30, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (3, 5, time '18:00:00', time '22:00:00', 30, 5);
insert into ciudadano (rol, ci, email, fechanacimiento, nombre, sexo, trabajo) VALUES ('Ciudadano', 52050756,'bpardinas@bruno.com', date '1997-01-01', 'Bruno Pardiñas', 0, 11);
insert into ciudadano (rol, ci, email, fechanacimiento, nombre, sexo, trabajo) VALUES ('Vacunador', 50550419,'nicolas@mail.com', date '1949-01-01', 'Nicolás San Martín', 0, 0);
insert into ciudadano (rol, ci, email, fechanacimiento, nombre, sexo, trabajo) VALUES ('Ciudadano', 12341234,'ana@mail.com', date '2000-01-01', 'Ana Rodriguez', 1, 1);

insert into intervalo (id, fechayHora, agendaid) VALUES  (nextval('intervaloId'),TIMESTAMP '2020-10-19 10:30:00',1);
insert into intervalo (id, fechayHora, agendaid) VALUES  (nextval('intervaloId'),TIMESTAMP '2020-11-19 10:30:00',1);
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid)  VALUES (nextval('reservaId'), 2, 1, 50550419, 1);
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaId'), 2, 2, 50550419, 2);


insert into intervalo (id, fechayHora, agendaid) VALUES  (nextval('intervaloId'), TIMESTAMP '2021-6-4 10:30:00',1);
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaId'), 0, 1, 50550419, 3);
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id) values (nextval('sequenciaAgendaId'), date '2020-01-01', null, 'Matutino', 1, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (4, 5, time '18:00:00', time '22:00:00', 30, 0);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (4, 5, time '18:00:00', time '22:00:00', 30, 2);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (4, 5, time '18:00:00', time '22:00:00', 30, 4);

insert into intervalo(id, fechayhora, agendaid) values (nextval('intervaloid'), timestamp '2021-06-07 18:00:00.000000', 4);
insert into intervalo(id, fechayhora, agendaid) values (nextval('intervaloid'), timestamp ',2021-06-28 18:00:00.000000', 4);
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaid'), 2, 1, 52050756, 4);
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaid'), 2, 2, 52050756, 5);

insert into sociologistico (nombre, habilitado) values ('UPS', boolean 'false');
insert into lote (numerolote, dosisdisponibles, fechavencimiento, sociologistico_nombre, vacuna_nombre) values (2143, 500, date '2020-09-09', 'UPS', 'Pfizer');
insert into lote (numerolote, dosisdisponibles, fechavencimiento, sociologistico_nombre, vacuna_nombre) values (1982, 1000, date '2020-09-09', 'UPS', 'Pfizer');
insert into vacunatorio_lote (vacunatorio_nombre, lotes_numerolote) values ('COSEM Punta Carretas',1982);
insert into vacunatorio_lote (vacunatorio_nombre, lotes_numerolote) values ('COSEM Punta Carretas',2143);

insert into agenda (id, inicio, fin, nombre, etapaid, turno_id) values (nextval('sequenciaAgendaId'), date '2021-02-01', null, 'Matutino', 4, 1);
insert into intervalo(id, fechayhora, agendaid) values (nextval('intervaloid'), timestamp '2021-06-07 18:00:00.000000', 5);
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaid'), 0, 1, 50550419, 6);
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaid'), 2, 1, 52050756, 6);

insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaid'), 2, 1, 12341234, 1);
insert into reserva (codigo, estado, paradosis, ciciudadano, intervaloid) VALUES (nextval('reservaid'), 0, 1, 12341234, 1);



insert into usuariobo(tipodeusuario, email, password) VALUES ('autoridad', '1@1', '$2a$10$ubTOrDZ6ufLdhoHQT7Hmtu9CQvxsR7YI6VNOH9ye40jdOXh7ubq1i');

insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-17','COSEM Punta Carretas','Pfizer', 1300)
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-16','COSEM Punta Carretas','Pfizer', 1200)
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-15','COSEM Punta Carretas','Pfizer', 1100)
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-14','COSEM Punta Carretas','Pfizer', 800)
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-13','COSEM Punta Carretas','Pfizer', 900)
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-12','COSEM Punta Carretas','Pfizer', 500)
insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-11','COSEM Punta Carretas','Pfizer', 200)

insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-17','COSEM Punta Carretas','Coronavac', 300)

insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-17','COSEM Punta Carretas','Gripevac', 200)

insert into datosdosis(fecha,nombreVacunatorio,nombreVacuna,cantidad) VALUES (date '2021-06-17','Hospital De Clínicas','Coronavac', 350)

