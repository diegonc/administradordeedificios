alter table PROPIEDAD
    add unique index PROPIEDAD_UQ1 (TIPOPROPIEDAD_ID, NIVEL, ORDEN),
    drop index NIVEL;

