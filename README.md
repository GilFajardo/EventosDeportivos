# Sistema de Reservaciones para Eventos Deportivos

Aplicacion de escritorio para gestionar la reservacion de boletos y asientos
en eventos deportivos. El proyecto esta desarrollado en Java Swing, utiliza
MySQL/MariaDB como base de datos y se administra como un proyecto Java SE de
NetBeans con Ant.

## Funcionalidades

### Clientes

- Inicio de sesion.
- Consulta y modificacion del perfil.
- Consulta de eventos disponibles.
- Seleccion y reservacion de asientos.
- Consulta y cancelacion de reservas.
- Consulta de puntos o recompensas.
- Exportacion de las reservas a PDF.

### Administradores

- Inicio de sesion con acceso al panel administrativo.
- Alta, modificacion y eliminacion de eventos.
- Alta, consulta y eliminacion de usuarios.
- Consulta de estadisticas de eventos, asientos y clientes.
- Graficas de informacion gerencial.

## Tecnologias y dependencias

- Java JDK 17 o superior. El proyecto compila con `source` y `target` 17.
- Java Swing para la interfaz grafica.
- MySQL o MariaDB.
- MySQL Connector/J 8.3.0.
- iTextPDF 5.5.9 para generar reportes PDF.
- JFreeChart 1.0.19 y JCommon 1.0.23 para las graficas.
- Apache Ant, o Apache NetBeans, para compilar el proyecto.

Las dependencias necesarias estan incluidas en el repositorio:

- `mysql-connector-j-8.3.0\mysql-connector-j-8.3.0.jar`
- `PDF's\itextpdf-5.5.9.jar`
- `graficas\jfreechart-1.0.19.jar`
- `graficas\jcommon-1.0.23.jar`

## Requisitos previos

1. Instalar un JDK compatible y comprobarlo:

   ```powershell
   java -version
   javac -version
   ```

2. Instalar y ejecutar MySQL o MariaDB en `localhost:3306`.
3. Tener disponible un usuario con permisos para crear e importar la base de
   datos.
4. Instalar Apache Ant si se desea compilar desde la terminal. NetBeans ya
   incluye la integracion necesaria para ejecutar los objetivos del proyecto.

## Configuracion de la base de datos

El esquema y los datos iniciales se encuentran en `dbpfp3.sql`. El codigo
actual intenta conectarse con estos valores:

| Propiedad | Valor actual |
| --- | --- |
| Motor | MySQL/MariaDB |
| Host | `localhost` |
| Puerto | `3306` |
| Base de datos | `dbpfp3` |
| Usuario | `root` |
| Contraseña | vacia |

Si la base de datos aun no existe, ejecuta desde PowerShell o una terminal de
MySQL:

```sql
CREATE DATABASE dbpfp3 CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

Despues importa el respaldo:

```powershell
mysql -u root -p dbpfp3 < .\dbpfp3.sql
```

Si el usuario local no tiene contraseña, omite `-p`. Tambien es posible abrir
`dbpfp3.sql` desde phpMyAdmin y ejecutarlo dentro de la base de datos
`dbpfp3`.

> Importante: la configuracion de conexion esta escrita directamente en
> `src\ConexionDB\Conexion.java`. Para otro host, puerto, usuario o contraseña
> hay que modificar esa clase antes de compilar. En un entorno real no se
> recomienda usar `root` ni guardar credenciales en el codigo fuente.

## Ejecucion desde NetBeans

1. Abrir la carpeta del proyecto en NetBeans.
2. Confirmar que el proyecto utiliza un JDK 17 o superior.
3. Verificar que las cuatro dependencias locales aparezcan en las bibliotecas
   del proyecto.
4. Confirmar que MySQL/MariaDB este iniciado y que la base `dbpfp3` haya sido
   importada.
5. Ejecutar el proyecto (`Run Project`). La clase principal es
   `Clases.Main`.

La aplicacion inicia en la pantalla de inicio de sesion y muestra el panel de
cliente o administrador segun las credenciales consultadas en la base de
datos.

## Compilacion y ejecucion desde la terminal

Desde la raiz del proyecto, con Ant instalado:

```powershell
ant clean jar
java -jar .\dist\ProyectoFinal.jar
```

El proceso de compilacion copia las dependencias al directorio
`dist\lib`. Para ejecutar el artefacto distribuible se debe conservar la
estructura completa:

```text
dist\
  ProyectoFinal.jar
  lib\
    itextpdf-5.5.9.jar
    jcommon-1.0.23.jar
    jfreechart-1.0.19.jar
    mysql-connector-j-8.3.0.jar
```

Tambien existe un JAR ya generado en `dist\ProyectoFinal.jar`; aun asi, se
recomienda recompilar despues de cambiar la configuracion de la base de datos
o el codigo fuente.

## Estructura del proyecto

```text
ProyectoFinal\
├── src\
│   ├── Clases\                 Modelos, sesion, inicio y generacion de PDF
│   ├── ConexionDB\             Acceso a MySQL y consultas de administracion
│   ├── Clientesproyecto\       Ventanas y operaciones del cliente
│   ├── administradorproyecto\  Ventanas y operaciones del administrador
│   ├── asientos\               Seleccion y reserva de asientos
│   ├── IniciarSesion\          Pantalla de autenticacion
│   └── hilocarga\              Pantalla de carga
├── dbpfp3.sql                  Esquema y datos iniciales
├── nbproject\                  Configuracion de NetBeans y Ant
├── build.xml                   Script principal de Ant
├── dist\                       JAR distribuible y bibliotecas
├── PDF's\                      Biblioteca iTextPDF
└── graficas\                   JFreeChart y JCommon
```

## Modelo de datos

El script SQL define las tablas principales:

- `administradores`: cuentas con acceso al panel administrativo.
- `clientes`: datos de los usuarios y sus puntos.
- `eventos`: nombre, tipo, fecha, capacidad y precio.
- `asientos`: disponibilidad, evento, cliente y precio de cada asiento.
- `reservas`: relacion entre cliente, evento y asiento.

Las relaciones entre eventos, clientes, asientos y reservas se implementan con
llaves foraneas en el script de importacion.

## Notas de seguridad y mantenimiento

- Las contraseñas se almacenan y comparan actualmente como texto plano. Antes
  de usar el sistema fuera de un entorno academico, se deben aplicar hashes
  seguros y evitar mostrar o generar contraseñas a partir del nombre del
  usuario.
- La conexion usa credenciales fijas y no configura TLS. Se recomienda mover
  estos valores a variables de entorno o a un archivo de configuracion fuera
  del control de versiones.
- `dbpfp3.sql` contiene datos de ejemplo. Revisar o reemplazar esos datos antes
  de desplegar el sistema.
- El proyecto no incluye una suite de pruebas automatizadas; la validacion
  actual se realiza mediante la interfaz y la base de datos configurada.

## Licencia

No se especifica una licencia para el codigo de este proyecto.
