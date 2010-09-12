
    alter table PROPIEDAD 
        drop 
        foreign key FK7B38234821FB2DA0;

    alter table PROPIEDAD 
        drop 
        foreign key FK7B38234852D21872;

    alter table PROPIEDAD 
        drop 
        foreign key FK7B38234894A43229;

    alter table PROPIEDAD 
        drop 
        foreign key FK7B382348340888E4;

    alter table PROPIEDAD 
        drop 
        foreign key FK7B3823488F574992;

    drop table if exists EDIFICIO;

    drop table if exists PROPIEDAD;

    drop table if exists Responsable;

    drop table if exists TIPO_PROPIEDAD;

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
        VERSION integer,
        primary key (ID)
    );

    create table PROPIEDAD (
        id integer not null,
        CTA_EXT_SALDO_EXP double precision not null,
        CTA_EXT_SALDO_INT double precision not null,
        CTA_ORD_SALDO_EXP double precision not null,
        CTA_ORD_SALDO_INT double precision not null,
        NIVEL integer not null,
        ORDEN integer not null,
        version integer not null,
        propietario_dni integer not null,
        inquilino_dni integer,
        poderInquilino_dni integer,
        tipoPropiedad_ID integer not null,
        poderPropietario_dni integer,
        primary key (id)
    );

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
    );

    create table TIPO_PROPIEDAD (
        ID integer not null,
        NOMBRE_EDIFICIO varchar(255) not null,
        NOMBRE_TIPO varchar(255) not null,
        VERSION integer,
        primary key (ID)
    );

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
        add index FK7B38234894A43229 (tipoPropiedad_ID), 
        add constraint FK7B38234894A43229 
        foreign key (tipoPropiedad_ID) 
        references TIPO_PROPIEDAD (ID);

    alter table PROPIEDAD 
        add index FK7B382348340888E4 (inquilino_dni), 
        add constraint FK7B382348340888E4 
        foreign key (inquilino_dni) 
        references Responsable (dni);

    alter table PROPIEDAD 
        add index FK7B3823488F574992 (propietario_dni), 
        add constraint FK7B3823488F574992 
        foreign key (propietario_dni) 
        references Responsable (dni);
