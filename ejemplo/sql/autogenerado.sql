
    alter table EXPENSA 
        drop 
        foreign key FKDCC05434FA69BF93;

    alter table EXPENSA_COBRO 
        drop 
        foreign key FK3CE6D24899F978A0;

    alter table EXPENSA_LIQUIDACION 
        drop 
        foreign key FKDA11553F99F978A0;

    alter table GASTO 
        drop 
        foreign key FK40752F438684391;

    alter table GASTO 
        drop 
        foreign key FK40752F446FF4F2C;

    alter table GASTO_PREVISION 
        drop 
        foreign key FK2E4184A045E1654F;

    alter table GASTO_REAL 
        drop 
        foreign key FKFB7B700945E1654F;

    alter table PROPIEDAD 
        drop 
        foreign key FK7B38234821FB2DA0;

    alter table PROPIEDAD 
        drop 
        foreign key FK7B38234852D21872;

    alter table PROPIEDAD 
        drop 
        foreign key FK7B382348340888E4;

    alter table PROPIEDAD 
        drop 
        foreign key FK7B38234894A43229;

    alter table PROPIEDAD 
        drop 
        foreign key FK7B3823488F574992;

    alter table TIPO_GASTO_EVENTUAL 
        drop 
        foreign key FKE7BAC7DCC53C9B5;

    alter table TIPO_GASTO_MONTO_FIJO 
        drop 
        foreign key FK54EBABF6D7503EFE;

    alter table TIPO_GASTO_MONTO_VARIABLE 
        drop 
        foreign key FK42FA7A2AD7503EFE;

    alter table TIPO_GASTO_ORDINARIO 
        drop 
        foreign key FKFCF9FB276250324E;

    alter table TIPO_GASTO_PERIODICO 
        drop 
        foreign key FK2F75B27EC53C9B5;

    alter table TIPO_GASTO_PERIODICO 
        drop 
        foreign key FK2F75B27E38684391;

    alter table TIPO_PROPIEDAD 
        drop 
        foreign key FK9CB2F7FD38684391;

    alter table TIPO_PROPIEDAD_TIPO_GASTO 
        drop 
        foreign key FK13637ABC8945F64;

    alter table TIPO_PROPIEDAD_TIPO_GASTO 
        drop 
        foreign key FK13637AB11C5FF6A;

    alter table USUARIO 
        drop 
        foreign key FK22E07F0EC8D3214B;

    drop table if exists EDIFICIO;

    drop table if exists EXPENSA;

    drop table if exists EXPENSA_COBRO;

    drop table if exists EXPENSA_LIQUIDACION;

    drop table if exists GASTO;

    drop table if exists GASTO_PREVISION;

    drop table if exists GASTO_REAL;

    drop table if exists PERFIL;

    drop table if exists PROPIEDAD;

    drop table if exists Responsable;

    drop table if exists TIPO_GASTO;

    drop table if exists TIPO_GASTO_EVENTUAL;

    drop table if exists TIPO_GASTO_MONTO_FIJO;

    drop table if exists TIPO_GASTO_MONTO_VARIABLE;

    drop table if exists TIPO_GASTO_ORDINARIO;

    drop table if exists TIPO_GASTO_PERIODICO;

    drop table if exists TIPO_PROPIEDAD;

    drop table if exists TIPO_PROPIEDAD_TIPO_GASTO;

    drop table if exists USUARIO;

    drop table if exists factura1;

    create table EDIFICIO (
        ID integer not null auto_increment,
        AMORTIZACION double precision not null,
        APTO_PROFESIONAL bit not null,
        CALLE varchar(255) not null,
        DIA_PRIMER_VTO integer not null,
        DIA_SEGUNDO_VTO integer,
        ENCARGADO_DEPTO varchar(255) not null,
        ENCARGADO_NOMBRE varchar(255) not null,
        ENCARGADO_PISO varchar(255) not null,
        ENCARGADO_TELEFONO varchar(255) not null,
        FONDO_EXTRAORDINARIO double precision not null,
        FONDO_ORDINARIO double precision not null,
        FORMA_LIQ_EXP varchar(255) not null,
        LOCALIDAD varchar(255) not null,
        NOMBRE varchar(255) not null,
        NUMERO integer not null,
        TASA_ANUAL double precision not null,
        primary key (ID)
    ) type=InnoDB;

    create table EXPENSA (
        ID integer not null auto_increment unique,
        INTERESES double precision,
        MONTO double precision not null,
        NRO_OP integer not null,
        ORD_EXT varchar(255) not null,
        ID_PROPIEDAD integer not null,
        primary key (ID)
    ) type=InnoDB;

    create table EXPENSA_COBRO (
        EXPENSA_ID integer not null,
        COMPROBANTE varchar(255) not null,
        FECHA datetime not null,
        primary key (EXPENSA_ID)
    ) type=InnoDB;

    create table EXPENSA_LIQUIDACION (
        EXPENSA_ID integer not null,
        DEUDA_PREVIA double precision,
        INT_SEGUNDO_VTO double precision,
        primary key (EXPENSA_ID)
    ) type=InnoDB;

    create table GASTO (
        ID integer not null auto_increment unique,
        DETALLE varchar(255) not null,
        MONTO double precision not null,
        NRO_FOLIO integer not null,
        ID_EDIFICIO integer not null,
        CODIGO_TIPO_GASTO integer not null,
        primary key (ID)
    ) type=InnoDB;

    create table GASTO_PREVISION (
        GASTO_ID integer not null,
        ANIO integer not null,
        MES integer not null,
        primary key (GASTO_ID)
    ) type=InnoDB;

    create table GASTO_REAL (
        GASTO_ID integer not null,
        FECHA_PAGO datetime not null,
        FORMA_PAGO varchar(255) not null,
        NRO_FACTURA_PAGO integer not null,
        RAZON_SOCIAL varchar(255) not null,
        primary key (GASTO_ID)
    ) type=InnoDB;

    create table PERFIL (
        ID integer not null auto_increment unique,
        DESCRIPCION varchar(255) not null,
        primary key (ID)
    ) type=InnoDB;

    create table PROPIEDAD (
        ID integer not null auto_increment unique,
        CTA_EXT_SALDO_EXP double precision not null,
        CTA_EXT_SALDO_INT double precision not null,
        CTA_ORD_SALDO_EXP double precision not null,
        CTA_ORD_SALDO_INT double precision not null,
        NIVEL integer not null,
        ORDEN integer not null,
        version integer not null,
        propietario_dni integer not null,
        poderInquilino_dni integer,
        poderPropietario_dni integer,
        tipoPropiedad_ID integer not null,
        inquilino_dni integer,
        primary key (ID)
    ) type=InnoDB;

    create table Responsable (
        dni integer not null,
        autoridad bit,
        calle varchar(255),
        email varchar(255),
        localidad varchar(255),
        telefono varchar(255),
        ubicacion varchar(255),
        version integer,
        primary key (dni)
    ) type=InnoDB;

    create table TIPO_GASTO (
        ID integer not null auto_increment unique,
        CODIGO varchar(255) not null,
        DESCRIPCION varchar(255) not null,
        primary key (ID)
    ) type=InnoDB;

    create table TIPO_GASTO_EVENTUAL (
        TIPO_GASTO_ID integer not null,
        primary key (TIPO_GASTO_ID)
    ) type=InnoDB;

    create table TIPO_GASTO_MONTO_FIJO (
        TIPO_GASTO_ID integer not null,
        DIA_LIMITE datetime not null,
        MONTO_ACTUAL double precision not null,
        primary key (TIPO_GASTO_ID)
    ) type=InnoDB;

    create table TIPO_GASTO_MONTO_VARIABLE (
        TIPO_GASTO_ID integer not null,
        MONTO_PREV double precision not null,
        PROXIMO_VENCIMIENTO datetime not null,
        primary key (TIPO_GASTO_ID)
    ) type=InnoDB;

    create table TIPO_GASTO_ORDINARIO (
        TIPO_GASTO_ID integer not null,
        primary key (TIPO_GASTO_ID)
    ) type=InnoDB;

    create table TIPO_GASTO_PERIODICO (
        TIPO_GASTO_ID integer not null,
        PERIODO varchar(255) not null,
        ID_EDIFICIO integer,
        primary key (TIPO_GASTO_ID)
    ) type=InnoDB;

    create table TIPO_PROPIEDAD (
        ID integer not null auto_increment unique,
        divisor double precision not null,
        montoExp double precision not null,
        NOMBRE_TIPO varchar(255) not null,
        ID_EDIFICIO integer not null,
        primary key (ID)
    ) type=InnoDB;

    create table TIPO_PROPIEDAD_TIPO_GASTO (
        ID integer not null auto_increment unique,
        COEFICIENTE_DISTRIBUCION double precision,
        ID_TIPO_PROPIEDAD integer not null,
        ID_TIPO_GASTO integer not null,
        primary key (ID)
    ) type=InnoDB;

    create table USUARIO (
        ID integer not null auto_increment unique,
        APELLIDO varchar(255) not null,
        DNI integer not null,
        NOMBRE varchar(255) not null,
        PASSWORD varchar(255) not null,
        USUARIO varchar(255) not null,
        ID_PERFIL integer not null,
        primary key (ID)
    ) type=InnoDB;

    create table factura1 (
        fa_id integer not null auto_increment,
        fa_name varchar(255),
        fa_version integer,
        primary key (fa_id)
    ) type=InnoDB;

    alter table EXPENSA 
        add index FKDCC05434FA69BF93 (ID_PROPIEDAD), 
        add constraint FKDCC05434FA69BF93 
        foreign key (ID_PROPIEDAD) 
        references PROPIEDAD (ID);

    alter table EXPENSA_COBRO 
        add index FK3CE6D24899F978A0 (EXPENSA_ID), 
        add constraint FK3CE6D24899F978A0 
        foreign key (EXPENSA_ID) 
        references EXPENSA (ID);

    alter table EXPENSA_LIQUIDACION 
        add index FKDA11553F99F978A0 (EXPENSA_ID), 
        add constraint FKDA11553F99F978A0 
        foreign key (EXPENSA_ID) 
        references EXPENSA (ID);

    alter table GASTO 
        add index FK40752F438684391 (ID_EDIFICIO), 
        add constraint FK40752F438684391 
        foreign key (ID_EDIFICIO) 
        references EDIFICIO (ID);

    alter table GASTO 
        add index FK40752F446FF4F2C (CODIGO_TIPO_GASTO), 
        add constraint FK40752F446FF4F2C 
        foreign key (CODIGO_TIPO_GASTO) 
        references TIPO_GASTO (ID);

    alter table GASTO_PREVISION 
        add index FK2E4184A045E1654F (GASTO_ID), 
        add constraint FK2E4184A045E1654F 
        foreign key (GASTO_ID) 
        references GASTO (ID);

    alter table GASTO_REAL 
        add index FKFB7B700945E1654F (GASTO_ID), 
        add constraint FKFB7B700945E1654F 
        foreign key (GASTO_ID) 
        references GASTO (ID);

    alter table PROPIEDAD 
        add index FK7B38234821FB2DA0 (poderPropietario_dni), 
        add constraint FK7B38234821FB2DA0 
        foreign key (poderPropietario_dni) 
        references Responsable (dni);

    alter table PROPIEDAD 
        add index FK7B38234852D21872 (poderInquilino_dni), 
        add constraint FK7B38234852D21872 
        foreign key (poderInquilino_dni) 
        references Responsable (dni);

    alter table PROPIEDAD 
        add index FK7B382348340888E4 (inquilino_dni), 
        add constraint FK7B382348340888E4 
        foreign key (inquilino_dni) 
        references Responsable (dni);

    alter table PROPIEDAD 
        add index FK7B38234894A43229 (tipoPropiedad_ID), 
        add constraint FK7B38234894A43229 
        foreign key (tipoPropiedad_ID) 
        references TIPO_PROPIEDAD (ID);

    alter table PROPIEDAD 
        add index FK7B3823488F574992 (propietario_dni), 
        add constraint FK7B3823488F574992 
        foreign key (propietario_dni) 
        references Responsable (dni);

    alter table TIPO_GASTO_EVENTUAL 
        add index FKE7BAC7DCC53C9B5 (TIPO_GASTO_ID), 
        add constraint FKE7BAC7DCC53C9B5 
        foreign key (TIPO_GASTO_ID) 
        references TIPO_GASTO_ORDINARIO (TIPO_GASTO_ID);

    alter table TIPO_GASTO_MONTO_FIJO 
        add index FK54EBABF6D7503EFE (TIPO_GASTO_ID), 
        add constraint FK54EBABF6D7503EFE 
        foreign key (TIPO_GASTO_ID) 
        references TIPO_GASTO_PERIODICO (TIPO_GASTO_ID);

    alter table TIPO_GASTO_MONTO_VARIABLE 
        add index FK42FA7A2AD7503EFE (TIPO_GASTO_ID), 
        add constraint FK42FA7A2AD7503EFE 
        foreign key (TIPO_GASTO_ID) 
        references TIPO_GASTO_PERIODICO (TIPO_GASTO_ID);

    alter table TIPO_GASTO_ORDINARIO 
        add index FKFCF9FB276250324E (TIPO_GASTO_ID), 
        add constraint FKFCF9FB276250324E 
        foreign key (TIPO_GASTO_ID) 
        references TIPO_GASTO (ID);

    alter table TIPO_GASTO_PERIODICO 
        add index FK2F75B27EC53C9B5 (TIPO_GASTO_ID), 
        add constraint FK2F75B27EC53C9B5 
        foreign key (TIPO_GASTO_ID) 
        references TIPO_GASTO_ORDINARIO (TIPO_GASTO_ID);

    alter table TIPO_GASTO_PERIODICO 
        add index FK2F75B27E38684391 (ID_EDIFICIO), 
        add constraint FK2F75B27E38684391 
        foreign key (ID_EDIFICIO) 
        references EDIFICIO (ID);

    alter table TIPO_PROPIEDAD 
        add index FK9CB2F7FD38684391 (ID_EDIFICIO), 
        add constraint FK9CB2F7FD38684391 
        foreign key (ID_EDIFICIO) 
        references EDIFICIO (ID);

    alter table TIPO_PROPIEDAD_TIPO_GASTO 
        add index FK13637ABC8945F64 (ID_TIPO_PROPIEDAD), 
        add constraint FK13637ABC8945F64 
        foreign key (ID_TIPO_PROPIEDAD) 
        references TIPO_PROPIEDAD (ID);

    alter table TIPO_PROPIEDAD_TIPO_GASTO 
        add index FK13637AB11C5FF6A (ID_TIPO_GASTO), 
        add constraint FK13637AB11C5FF6A 
        foreign key (ID_TIPO_GASTO) 
        references TIPO_GASTO (ID);

    alter table USUARIO 
        add index FK22E07F0EC8D3214B (ID_PERFIL), 
        add constraint FK22E07F0EC8D3214B 
        foreign key (ID_PERFIL) 
        references PERFIL (ID);
