insert into vacuna (nombre, cantdosis, dosisseparaciondias, inmunidadmeses) values ('Coronavac', 2, 30, 8);
insert into vacuna (nombre, cantdosis, dosisseparaciondias, inmunidadmeses) values ('Pfizer', 2, 15, 8);
insert into vacuna (nombre, cantdosis, dosisseparaciondias, inmunidadmeses) values ('Astrazeneca', 1, 0, 8);
insert into enfermedad (nombre, descripcion) values ('Corona Virus', 'La enfermedad mas ******** del mundo.');
insert into vacuna_enfermedad (vacunas_nombre, enfermedades_nombre) values ('Coronavac', 'Corona Virus');
insert into vacuna_enfermedad (vacunas_nombre, enfermedades_nombre) values ('Pfizer', 'Corona Virus');
insert into vacuna_enfermedad (vacunas_nombre, enfermedades_nombre) values ('Astrazeneca', 'Corona Virus');
insert into planvacunacion(nombre, inicio, fin, enfermedadnombre) values ( 'Plan vacunacion covid 19', date '2020-01-01', date '2023-12-31', 'Corona Virus');
insert into etapa(id, descripcion, inicio, fin, mayorigual, menorigual, plannombre, vacuna_nombre) values (nextval('sequenciaetapaid'), 'Etapa vacunacion Personal de la Salud', date '2020-01-01', date '2023-12-31', null, null, 'Plan vacunacion covid 19', 'Pfizer');
insert into etapa(id, descripcion, inicio, fin, mayorigual, menorigual, plannombre, vacuna_nombre) values (nextval('sequenciaetapaid'), 'Etapa vacunacion Personal de la Salud y Enseñanza', date '2020-01-01', date '2023-12-31', null, null, 'Plan vacunacion covid 19', 'Astrazeneca');
insert into etapa(id, descripcion, inicio, fin, mayorigual, menorigual, plannombre, vacuna_nombre) values (nextval('sequenciaetapaid'), 'Etapa vacunacion General', date '2020-01-01', date '2023-12-31', 40, 70, 'Plan vacunacion covid 19', 'Coronavac');
insert into etapa_filtroempleoen (etapa_id, filtroempleoen) values (1, 'Salud');
insert into etapa_filtroempleoen (etapa_id, filtroempleoen) values (2, 'Salud');
insert into etapa_filtroempleoen (etapa_id, filtroempleoen) values (2, 'Educacion');
insert into vacunatorio (nombre, ciudad, departamento, direccion) values ('COSEM Punta Carretas', 'Montevideo', 'Montevideo', 'José Ellauri 461');
insert into vacunatorio (nombre, ciudad, departamento, direccion) values ('Hospital De Clínicas', 'Montevideo', 'Montevideo', 'Av. Italia 2982');
insert into vacunatorio (nombre, ciudad, departamento, direccion) values ('Hospital de Artigas', 'Artigas', 'Artigas', 'Eduardo M. Castro');

insert into turno (id, inicio, fin, nombre, vacunatorio_nombre) values (nextval('sequenciaTurnoId'), time '08:00:00', time '12:00:00', 'Matutino', 'COSEM Punta Carretas');
insert into turno (id, inicio, fin, nombre, vacunatorio_nombre) values (nextval('sequenciaTurnoId'), time '13:00:00', time '17:00:00', 'Vespertino', 'COSEM Punta Carretas');
insert into turno (id, inicio, fin, nombre, vacunatorio_nombre) values (nextval('sequenciaTurnoId'), time '18:00:00', time '22:00:00', 'Nocturno', 'COSEM Punta Carretas');
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id, cantidadCuposDisponbiles) values (nextval('sequenciaAgendaId'), date '2020-01-01', date '2023-12-31', 'Matutino', 3, 1, 100);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (1, 5, time '08:00:00', time '12:00:00', 30, 1);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (1, 5, time '08:00:00', time '12:00:00', 30, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (1, 5, time '08:00:00', time '12:00:00', 30, 5);
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id, cantidadCuposDisponbiles) values (nextval('sequenciaAgendaId'), date '2020-01-01', date '2023-12-31', 'Vespertino', 3, 2, 100);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (2, 5, time '13:00:00', time '17:00:00', 30, 1);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (2, 5, time '13:00:00', time '17:00:00', 30, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (2, 5, time '13:00:00', time '17:00:00', 30, 5);
insert into agenda (id, inicio, fin, nombre, etapaid, turno_id, cantidadCuposDisponbiles) values (nextval('sequenciaAgendaId'), date '2020-01-01', date '2023-12-31', 'Nocturno', 3, 3, 100);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (3, 5, time '18:00:00', time '22:00:00', 30, 1);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (3, 5, time '18:00:00', time '22:00:00', 30, 3);
insert into horariopordia (agenda_id, capacidadporturno, inicio, fin, minutosturno, dia) values (3, 5, time '18:00:00', time '22:00:00', 30, 5);

insert into sociologistico (nombre, habilitado) values ('UPS', boolean 'false')