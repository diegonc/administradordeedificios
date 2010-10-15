insert into `perfil`(`DESCRIPCION`,`ID`) values ('Administrador',1);
insert into `perfil`(`DESCRIPCION`,`ID`) values ('Responsable de Gastos',2);
insert into `perfil`(`DESCRIPCION`,`ID`) values ('Responsable de Cobros',3);
insert into `perfil`(`DESCRIPCION`,`ID`) values ('Responsable de Edificio',4);
insert into `usuario`(`NOMBRE`,`APELLIDO`,`PASSWORD`,`DNI`,`USUARIO`) values ('Nicolino','Roche','adminpass',1,'adminuser');
insert into `usuario_perfil`(`ID_USUARIO`,`ID_PERFIL`) values (1,1);
