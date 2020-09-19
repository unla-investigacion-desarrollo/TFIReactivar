CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_emprendimientosCercanos`(IN id_rubro bigint(20),IN id_persona bigint(20), IN cantidadKm varchar(255))
BEGIN
  DECLARE id_emp bigint(20);
  DECLARE lat2 varchar(255);
  DECLARE long2 varchar(255);
  DECLARE distancia varchar(255);
  DECLARE lat1 varchar(255);
  DECLARE long1 varchar(255);
  DECLARE fin INTEGER DEFAULT 0;
  DECLARE cursor_empxrubro CURSOR FOR  SELECT id_emprendimiento, latitud, longitud FROM temp_emprendimientosRubro;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin=1;
  
    /* Si existe la tabla temporal anteriormente, eliminarla */
    DROP TEMPORARY TABLE IF EXISTS temp_emprendimientosRubro;
	DROP TEMPORARY TABLE IF EXISTS temp_emprendimientosRubroKm;
    
    /* Filtrar Emprendimientos por Rubro y agregar a una Tabla Temporal */
    CREATE TEMPORARY TABLE temp_emprendimientosRubro
    SELECT e.id_ubicacion, e.id_emprendimiento, e.nombre , e.id_rubro, u.latitud, u.longitud  
    FROM emprendimiento AS e
	join ubicacion AS u ON e.id_ubicacion=u.id_ubicacion
    join rubro AS r ON e.id_rubro=r.id_rubro
    where e.id_rubro=id_rubro;

    /* Crear tabla temporal que aloja los emprendimientos cercanos por km solicitado */
    CREATE TEMPORARY TABLE temp_emprendimientosRubroKm ( 
    id_ubicacion bigint(20), id_emprendimiento bigint(20), nombre varchar(255),
	capacidad varchar(255), id_rubro bigint(20), calle varchar(255), 
	numero varchar(255), departamento varchar(255), id_localidad bigint(20) , 
	latitud varchar(255), longitud varchar(255) , distancia varchar(255) );
         

    /* Seleccionar lat1 , long1 de Persona */
    SET lat1 = (SELECT u.latitud From persona as p 
				join ubicacion as u ON p.id_ubicacion=u.id_ubicacion
				where p.id_persona=id_persona);
        
    SET long1 = (SELECT u.longitud as long1 From persona as p 
				join ubicacion as u ON p.id_ubicacion=u.id_ubicacion
                where p.id_persona=id_persona);
                
    -- Variables donde almacenar lo que nos traemos desde el SELECT


  OPEN cursor_empxrubro;
  read_loop: LOOP
    FETCH cursor_empxrubro INTO id_emp, lat2, long2;
   
	IF fin = 1 THEN
      LEAVE read_loop;
    END IF;
    
	set distancia = (SELECT reactivardb.f_calcularDistancia( lat1, long1,lat2, long2));
    -- set distancia = (SELECT reactivardb.f_calcularDistancia( 51251, 51251, 51251, 51251));
	-- IF distancia <= @cantidadKm THEN
    IF cast(distancia as decimal) <= cast(cantidadKm as decimal) THEN
		INSERT INTO temp_emprendimientosRubroKm ( id_ubicacion, id_emprendimiento , nombre ,
							capacidad , id_rubro , calle , 
							numero , departamento , id_localidad , 
                            latitud , longitud , distancia ) 
		SELECT u.id_ubicacion, e.id_emprendimiento , e.nombre ,e.capacidad , e.id_rubro , u.calle ,  
							u.numero , u.departamento , u.id_localidad , 
                            u.latitud , u.longitud , distancia
		FROM emprendimiento as e 
        inner join ubicacion u on u.id_ubicacion=e.id_ubicacion
		where e.id_emprendimiento = id_emp;
	END IF;
	
  END LOOP read_loop;

  CLOSE cursor_empxrubro;
  
  /*SELECT * FROM reactivardb.temp_emprendimientosRubroKm;*/
  
  select e.* from emprendimiento AS e
  join temp_emprendimientosRubroKm AS r ON e.id_emprendimiento=r.id_emprendimiento;
    


END