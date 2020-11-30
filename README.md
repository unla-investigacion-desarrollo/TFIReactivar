# ReactivAR - Backend


## Deploy

### Generar War ###
* Importar proyecto como Maven Project.
* Exportar como proyecto como War

### App-directory ###
Lo utilizaremos para almacenar todos los archivos externos necesarios.

[Link al repositorio](https://github.com/unla-investigacion-desarrollo/app-directory)

### Base de Datos ###
Antes de levantar el servidor, es necesario tener una base de datos mysql con el nombre "reactivardb"

### Servidor Tomcat ###
* Descargar Servidor Tomcat 8 ([Ir a Tomcat](https://tomcat.apache.org/download-80.cgi))
* Crear variable de entorno en Windows de apache Tomcat ([Guía](http://yellow-jbox.blogspot.com/2011/04/how-to-set-catalinahomepath-variables.html))
* Crear setenv.bat con el path donde se encuentra la carpeta app-directory (Ej. Si la ruta de app-directory es __C:/Users/Matias/Desktop/UnlaTesis/app-directory__ entonces:
   * Windows : {{Path Tomcat}}/bin/setenv.bat:
```bash
set "ROOT_PATH=C:/Users/Matias/Desktop/UnlaTesis"
  exit /b 0
```
   * Linux: {{Path Tomcat}}/bin/setenv.sh:
```bash
ROOT_PATH=/user/UnlaTesis
```

* Colocar War en {{Path Tomcat}}/webapps
* Modificar variables conexión a base de datos en ({{Path Tomcat}}/webapps/reactivar/WEB-INF/classes/application.yaml)
* Ejecutar servidor desde CMD -> catalina start o ejecutando startup.bat desde /bin
