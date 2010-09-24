insert into `perfil`(`DESCRIPCION`,`ID`) values ('Administrador',1);
insert into `perfil`(`DESCRIPCION`,`ID`) values ('Concejero de Administracion',2);
insert into `perfil`(`DESCRIPCION`,`ID`) values ('Empleado',3);
insert into `perfil`(`DESCRIPCION`,`ID`) values ('Presidente de consorcio',4);
insert into `perfil`(`DESCRIPCION`,`ID`) values ('Encargado de Edificio',5);
insert into `usuario`(`NOMBRE`,`APELLIDO`,`PASSWORD`,`DNI`,`USUARIO`,`ID_EDIFICIO`,`ID`) values ('Nicolino','Roche','adminpass',30110082,'adminuser',null,1);
insert into `usuario_perfil`(`ID_USUARIO`,`ID_PERFIL`,`ID`) values (1,1,1);