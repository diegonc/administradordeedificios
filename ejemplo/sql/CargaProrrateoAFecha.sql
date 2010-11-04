-- Edificio Platinum
-- Modo Liquidacion Prorrateo, tipo de mora mora a fecha.
insert into `edificio`(`ID`,`NOMBRE`,`FONDO_ORDINARIO`,`FONDO_EXTRAORDINARIO`,`FORMA_LIQ_EXP`,`APTO_PROFESIONAL`,`TASA_ANUAL`,`AMORTIZACION`,`CALLE`,`NUMERO`,`LOCALIDAD`,`ENCARGADO_NOMBRE`,`ENCARGADO_TELEFONO`,`ENCARGADO_DEPTO`,`ENCARGADO_PISO`,`DIA_PRIMER_VTO`,`DIA_SEGUNDO_VTO`,`MORA`) values (1,'Platinum',10000,5000,'PRORRATEO',1,10,'ALEMAN','Chile',1348,'CABA','Leonardo Lopez','123456123','D','7',10,0,'afecha');

-- Tipos de Propiedades
-- Ver que las mismas van asociadas a un edificio, divisor representa los m2 de ese tipo.
insert into `tipo_propiedad`(`ID`,`NOMBRE_TIPO`,`ID_EDIFICIO`,`montoExp`,`divisor`) values (1,'Cocheras Subterraneas',1,150,20);
insert into `tipo_propiedad`(`ID`,`NOMBRE_TIPO`,`ID_EDIFICIO`,`montoExp`,`divisor`) values (2,'Bauleras',1,100,4);
insert into `tipo_propiedad`(`ID`,`NOMBRE_TIPO`,`ID_EDIFICIO`,`montoExp`,`divisor`) values (3,'Locales comerciales',1,200,40);
insert into `tipo_propiedad`(`ID`,`NOMBRE_TIPO`,`ID_EDIFICIO`,`montoExp`,`divisor`) values (4,'Depto Planta Baja',1,300,120);
insert into `tipo_propiedad`(`ID`,`NOMBRE_TIPO`,`ID_EDIFICIO`,`montoExp`,`divisor`) values (5,'Cocheras Descubiertas',1,120,20);
insert into `tipo_propiedad`(`ID`,`NOMBRE_TIPO`,`ID_EDIFICIO`,`montoExp`,`divisor`) values (6,'Departamento',1,400,960);


-- Responsable
-- Necesarios para la creacion de propiedades, dado q son los propietarios de las mismas.
insert into `responsable`(`DNI`,`AUTORIDAD`,`CALLE`,`EMAIL`,`LOCALIDAD`,`TELEFONO`,`UBICACION`,`VERSION`) values (30761872,1,'Chile','dmpstaltari@hotmail.com','CABA','42269167','1348',0);
insert into `responsable`(`DNI`,`AUTORIDAD`,`CALLE`,`EMAIL`,`LOCALIDAD`,`TELEFONO`,`UBICACION`,`VERSION`) values (30761873,0,'San Isidro','adam@gmail.com','Dominico','123456','Sillon y Camita',1);
insert into `responsable`(`DNI`,`AUTORIDAD`,`CALLE`,`EMAIL`,`LOCALIDAD`,`TELEFONO`,`UBICACION`,`VERSION`) values (31026053,1,'Chile','adrichelo84@hotmail.com','CABA','42079003','1350',0);


-- Propiedades
-- 2 bauleras, 2 cocheras subterraneas, 2 locales comerciales, 2 Cocheras Descubiertas, 2 Depto Planta Baja, 8 Depto
-- Dividendo representa el tamanio en m2 de la propiedad.
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (1,0,0,0,0,-1,1,86,null,null,null,30761872,2,2);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (2,0,0,0,0,-1,2,85,null,null,null,31026053,2,2);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (6,0,0,0,0,2,1,85,null,null,null,30761872,6,80);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (8,0,0,0,0,0,1,84,null,null,null,31026053,4,60);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (9,0,0,0,0,0,2,84,null,null,null,30761872,4,60);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (14,0,0,0,0,0,3,84,null,null,null,30761873,3,20);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (15,0,0,0,0,0,4,84,null,null,null,30761873,3,20);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (16,0,0,0,0,-1,3,88,null,null,null,31026053,1,10);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (17,0,0,0,0,-1,4,87,null,null,null,31026053,1,10);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (18,0,0,0,0,1,1,85,null,null,null,30761872,6,80);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (19,0,0,0,0,1,2,85,null,null,null,30761872,6,80);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (22,0,0,0,0,2,2,84,null,null,null,31026053,6,80);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (29,0,0,0,0,0,5,85,null,null,null,30761872,5,10);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (30,0,0,0,0,0,6,85,null,null,null,31026053,5,10);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (31,0,0,0,0,3,1,0,null,null,null,31026053,6,80);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (32,0,0,0,0,3,2,0,null,null,null,31026053,6,80);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (33,0,0,0,0,4,1,0,null,null,null,31026053,6,80);
insert into `propiedad`(`ID`,`CTA_EXT_SALDO_EXP`,`CTA_EXT_SALDO_INT`,`CTA_ORD_SALDO_EXP`,`CTA_ORD_SALDO_INT`,`NIVEL`,`ORDEN`,`VERSION`,`INQUILINO_DNI`,`PODERINQUILINO_DNI`,`PODERPROPIETARIO_DNI`,`PROPIETARIO_DNI`,`TIPOPROPIEDAD_ID`,`DIVIDENDO`) values (34,0,0,0,0,4,2,0,null,null,null,31026053,6,80);


-- Tipos de Gastos
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (1,'ENCARGADO','Encargado','PMF');
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (2,'EMPLELIMP','Empleado Limpieza','PMF');
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (3,'SEGNOCT','Seguridad Nocturna','PMF');
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (4,'HONORADMIN','Honorarios Administracion','PMF');
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (5,'SEGMANTASC','Seguro Mantenimiento Ascensor','PMF');
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (6,'SEGEDIFICIO','Seguro Edificio','PMF');
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (7,'AGUA','Agua','PMF');
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (8,'ELECTRICIDAD','Electricidad','PMV');
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (9,'GAS','Gas','PMV');
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (10,'LIMPIEZA','Limpieza','EVE');
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (11,'REPCERRADURA','Reparacion Cerradura','EVE');
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (13,'DESCANIERIAS','Destape Canierias','EVE');
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (14,'PINTPASILLOS','Pintura Pasillos','EXT');
insert into `tipo_gasto`(`ID`,`CODIGO`,`DESCRIPCION`,`TIPO`) values (15,'PINTGRAL','Pintura General','EXT');

-- Tipos de Gastos Extraordinarios
insert into `tipo_gasto_extraordinario`(`ID`,`TIPO_GASTO_ID`) values (1,14);
insert into `tipo_gasto_extraordinario`(`ID`,`TIPO_GASTO_ID`) values (2,15);

-- Tipo de Gastos Ordinarios
insert into `tipo_gasto_ordinario`(`ID`,`TIPO_GASTO_ID`) values (1,1);
insert into `tipo_gasto_ordinario`(`ID`,`TIPO_GASTO_ID`) values (2,2);
insert into `tipo_gasto_ordinario`(`ID`,`TIPO_GASTO_ID`) values (3,3);
insert into `tipo_gasto_ordinario`(`ID`,`TIPO_GASTO_ID`) values (4,4);
insert into `tipo_gasto_ordinario`(`ID`,`TIPO_GASTO_ID`) values (5,5);
insert into `tipo_gasto_ordinario`(`ID`,`TIPO_GASTO_ID`) values (6,6);
insert into `tipo_gasto_ordinario`(`ID`,`TIPO_GASTO_ID`) values (7,7);
insert into `tipo_gasto_ordinario`(`ID`,`TIPO_GASTO_ID`) values (8,8);
insert into `tipo_gasto_ordinario`(`ID`,`TIPO_GASTO_ID`) values (9,9);
insert into `tipo_gasto_ordinario`(`ID`,`TIPO_GASTO_ID`) values (10,10);
insert into `tipo_gasto_ordinario`(`ID`,`TIPO_GASTO_ID`) values (11,11);
insert into `tipo_gasto_ordinario`(`ID`,`TIPO_GASTO_ID`) values (13,13);

-- Tipos de Gastos Ordinarios Eventuales
insert into `tipo_gasto_eventual`(`ID`,`TIPO_GASTO_ID`) values (1,10);
insert into `tipo_gasto_eventual`(`ID`,`TIPO_GASTO_ID`) values (2,11);
insert into `tipo_gasto_eventual`(`ID`,`TIPO_GASTO_ID`) values (4,13);

-- Tipos de Gastos Ordinarios Periodicos
insert into `tipo_gasto_periodico`(`ID`,`TIPO_GASTO_ID`,`PERIODO`,`ID_EDIFICIO`) values (1,1,'1',1);
insert into `tipo_gasto_periodico`(`ID`,`TIPO_GASTO_ID`,`PERIODO`,`ID_EDIFICIO`) values (2,2,'1',1);
insert into `tipo_gasto_periodico`(`ID`,`TIPO_GASTO_ID`,`PERIODO`,`ID_EDIFICIO`) values (3,3,'1',1);
insert into `tipo_gasto_periodico`(`ID`,`TIPO_GASTO_ID`,`PERIODO`,`ID_EDIFICIO`) values (4,4,'1',1);
insert into `tipo_gasto_periodico`(`ID`,`TIPO_GASTO_ID`,`PERIODO`,`ID_EDIFICIO`) values (5,5,'1',1);
insert into `tipo_gasto_periodico`(`ID`,`TIPO_GASTO_ID`,`PERIODO`,`ID_EDIFICIO`) values (6,6,'3',1);
insert into `tipo_gasto_periodico`(`ID`,`TIPO_GASTO_ID`,`PERIODO`,`ID_EDIFICIO`) values (7,7,'2',1);
insert into `tipo_gasto_periodico`(`ID`,`TIPO_GASTO_ID`,`PERIODO`,`ID_EDIFICIO`) values (8,8,'2',1);
insert into `tipo_gasto_periodico`(`ID`,`TIPO_GASTO_ID`,`PERIODO`,`ID_EDIFICIO`) values (9,9,'2',1);

-- Tipos de Gastos Ordinarios Periodicos Monto Fijo
insert into `tipo_gasto_monto_fijo`(`ID`,`TIPO_GASTO_ID`,`DIA_LIMITE`,`MONTO_ACTUAL`) values (1,1,10,1500);
insert into `tipo_gasto_monto_fijo`(`ID`,`TIPO_GASTO_ID`,`DIA_LIMITE`,`MONTO_ACTUAL`) values (2,2,10,900);
insert into `tipo_gasto_monto_fijo`(`ID`,`TIPO_GASTO_ID`,`DIA_LIMITE`,`MONTO_ACTUAL`) values (3,3,5,1000);
insert into `tipo_gasto_monto_fijo`(`ID`,`TIPO_GASTO_ID`,`DIA_LIMITE`,`MONTO_ACTUAL`) values (4,4,10,150);
insert into `tipo_gasto_monto_fijo`(`ID`,`TIPO_GASTO_ID`,`DIA_LIMITE`,`MONTO_ACTUAL`) values (5,5,5,200);
insert into `tipo_gasto_monto_fijo`(`ID`,`TIPO_GASTO_ID`,`DIA_LIMITE`,`MONTO_ACTUAL`) values (6,6,15,550);
insert into `tipo_gasto_monto_fijo`(`ID`,`TIPO_GASTO_ID`,`DIA_LIMITE`,`MONTO_ACTUAL`) values (7,7,10,850);

-- Tipos de Gastos Ordinarios Periodicos Monto Variable
insert into `tipo_gasto_monto_variable`(`ID`,`TIPO_GASTO_ID`,`MONTO_PREV`,`PROXIMO_VENCIMIENTO`) values (1,8,1300,'2010-11-21 00:00:00');
insert into `tipo_gasto_monto_variable`(`ID`,`TIPO_GASTO_ID`,`MONTO_PREV`,`PROXIMO_VENCIMIENTO`) values (2,9,870,'2010-11-10 00:00:00');

-- Relacion entre tipo propiedad y tipo de gasto
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (2,1,8,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (3,1,4,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (4,1,2,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (5,1,7,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (6,1,5,0.35);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (7,1,6,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (8,1,10,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (9,2,8,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (10,2,4,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (11,2,7,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (12,2,5,0.35);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (13,2,1,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (14,2,6,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (15,3,8,0.6);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (16,3,4,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (17,3,2,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (18,3,7,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (19,3,1,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (20,3,6,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (21,3,10,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (22,4,8,0.6);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (23,4,4,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (24,4,2,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (25,4,7,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (26,4,1,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (27,4,3,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (28,4,6,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (29,4,10,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (30,5,8,0.6);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (31,5,4,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (32,5,2,0.3);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (33,5,7,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (34,5,1,0.3);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (35,5,3,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (36,5,6,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (37,5,10,0.3);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (38,6,8,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (39,6,4,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (40,6,2,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (41,6,7,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (42,6,5,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (43,6,1,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (44,6,3,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (45,6,6,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (46,6,10,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (47,1,1,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (48,1,11,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (49,6,15,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (50,2,15,null);
insert into `tipo_propiedad_tipo_gasto`(`ID`,`ID_TIPO_PROPIEDAD`,`ID_TIPO_GASTO`,`COEFICIENTE_DISTRIBUCION`) values (51,1,15,0.1);

-- Gastos
insert into `gasto`(`ID`,`NRO_FOLIO`,`MONTO`,`DETALLE`,`ID_TIPO_GASTO`,`ID_EDIFICIO`) values (1,1,1500,'Octubre 2010',1,1);
insert into `gasto`(`ID`,`NRO_FOLIO`,`MONTO`,`DETALLE`,`ID_TIPO_GASTO`,`ID_EDIFICIO`) values (2,2,230,'Cerradura Puerta Entrada',11,1);
insert into `gasto`(`ID`,`NRO_FOLIO`,`MONTO`,`DETALLE`,`ID_TIPO_GASTO`,`ID_EDIFICIO`) values (3,3,1300,'Octubre 2010',8,1);
insert into `gasto`(`ID`,`NRO_FOLIO`,`MONTO`,`DETALLE`,`ID_TIPO_GASTO`,`ID_EDIFICIO`) values (4,4,700.35,'Pintura Octubre 2010',15,1);
insert into `gasto`(`ID`,`NRO_FOLIO`,`MONTO`,`DETALLE`,`ID_TIPO_GASTO`,`ID_EDIFICIO`) values (5,5,150,'Reparacion Porton',11,1);
insert into `gasto`(`ID`,`NRO_FOLIO`,`MONTO`,`DETALLE`,`ID_TIPO_GASTO`,`ID_EDIFICIO`) values (6,6,1600,'Noviembre 2010',1,1);
insert into `gasto`(`ID`,`NRO_FOLIO`,`MONTO`,`DETALLE`,`ID_TIPO_GASTO`,`ID_EDIFICIO`) values (8,7,1200,'Prevision 11 2010',8,1);
insert into `gasto`(`ID`,`NRO_FOLIO`,`MONTO`,`DETALLE`,`ID_TIPO_GASTO`,`ID_EDIFICIO`) values (9,8,1400,'Encargado Octubre 2010',1,1);
insert into `gasto`(`ID`,`NRO_FOLIO`,`MONTO`,`DETALLE`,`ID_TIPO_GASTO`,`ID_EDIFICIO`) values (10,9,1350,'Electricidad 10 2010',8,1);

-- Gastos Reales
insert into `gasto_real`(`ID`,`GASTO_ID`,`RAZON_SOCIAL`,`FECHA_PAGO`,`NRO_FACTURA_PAGO`,`FORMA_PAGO`,`ESTADO`) values (1,1,'Consorcio','2010-10-05 00:00:00',1234,'A','P');
insert into `gasto_real`(`ID`,`GASTO_ID`,`RAZON_SOCIAL`,`FECHA_PAGO`,`NRO_FACTURA_PAGO`,`FORMA_PAGO`,`ESTADO`) values (2,2,'El cerrojo','2010-10-26 00:00:00',123,'A','P');
insert into `gasto_real`(`ID`,`GASTO_ID`,`RAZON_SOCIAL`,`FECHA_PAGO`,`NRO_FACTURA_PAGO`,`FORMA_PAGO`,`ESTADO`) values (3,3,'Edesur','2010-10-25 00:00:00',32123,'A','P');
insert into `gasto_real`(`ID`,`GASTO_ID`,`RAZON_SOCIAL`,`FECHA_PAGO`,`NRO_FACTURA_PAGO`,`FORMA_PAGO`,`ESTADO`) values (4,4,'Pinturerias Blanco','2010-10-20 00:00:00',1234,'B','P');
insert into `gasto_real`(`ID`,`GASTO_ID`,`RAZON_SOCIAL`,`FECHA_PAGO`,`NRO_FACTURA_PAGO`,`FORMA_PAGO`,`ESTADO`) values (5,5,'CierraTodo','2010-10-26 00:00:00',123412,'A','P');


-- Previsiones
insert into `gasto_prevision`(`ID`,`GASTO_ID`,`ANIO`,`MES`) values (1,6,2010,11);
insert into `gasto_prevision`(`ID`,`GASTO_ID`,`ANIO`,`MES`) values (2,8,2010,11);
insert into `gasto_prevision`(`ID`,`GASTO_ID`,`ANIO`,`MES`) values (3,9,2010,10);
insert into `gasto_prevision`(`ID`,`GASTO_ID`,`ANIO`,`MES`) values (4,10,2010,10);

