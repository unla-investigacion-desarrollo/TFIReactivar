CREATE DEFINER=`root`@`localhost` FUNCTION `f_calcularDistancia`( lat1 varchar(255), long1 varchar(255), lat2 varchar(255), long2 varchar(255)) RETURNS varchar(255) CHARSET utf8
BEGIN
	
    DECLARE distancia varchar(255);
    DECLARE x1, x2, dLat, dLong, a, c varchar(255);
    
    DECLARE R varchar(255) DEFAULT 6371.137; /*Radio de la tierra en Km */
    
    SET x1 = 0 , x2 = 0 , dLat = 0 , dLong = 0 , a = 0 , c = 0 ;
    
    SET x1 = lat2- lat1;
	SET dLat = (x1 * PI()) / 180;
    
    SET x2 = long2 - long1;
    SET dLong = (x2 * PI()) / 180;
    
    SET a = sin(dLat/2) * sin(dLat/2) + cos( (lat1 * PI()) / 180) * cos( (lat2 * PI()) / 180) * sin( dLong/2) * sin(dLong/2);
    
    SET c = 2*atan2(sqrt(a), sqrt(1-a));
    
    SET distancia =  R * c ;    
    
RETURN distancia;
END