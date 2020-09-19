CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_mostrarPorRubro`(IN id_rubro bigint(20))
BEGIN
	select *
    from emprendimiento AS e
    join rubro AS r ON e.id_rubro=r.id_rubro
    where e.id_rubro=id_rubro;

END