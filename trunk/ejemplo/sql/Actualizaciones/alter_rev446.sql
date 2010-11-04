-- Se agrega la fecha en q se realiza la liquidacion de las expensas.
ALTER TABLE EXPENSA  ADD FECHA DATETIME;

-- Se agrega el monto de las expensas extraordinarias
ALTER TABLE TIPO_PROPIEDAD ADD MONTO_EXP_EXT DOUBLE DEFAULT 0;

