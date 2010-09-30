insert into `perfil`(`DESCRIPCION`,`ID`) values ('Administrador',1);
insert into `perfil`(`DESCRIPCION`,`ID`) values ('Responsable de Gastos',2);
insert into `perfil`(`DESCRIPCION`,`ID`) values ('Responsable de Cobros',3);
insert into `perfil`(`DESCRIPCION`,`ID`) values ('Responsable de Edificio',4);
insert into `usuario`(`NOMBRE`,`APELLIDO`,`PASSWORD`,`DNI`,`USUARIO`,`ID`) values ('Nicolino','Roche','adminpass',30110082,'adminuser',1);
insert into `usuario_perfil_edificio`(`ID_USUARIO`,`ID_PERFIL`,`ID`,`ID_EDIFICIO`) values (1,1,1,null);