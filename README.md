# Sistemas Distribuidos 

---

## Unidad II: Comunicación entre procesos

### Práctica de Programación - Invocación Remota de Métodos con Java RMI

##### Integrantes del equipo 

- Jesús Virgilio Ayala Gaspar 

- Maricarmen Buenfil Perez 

---

### Sistema de Acceso a Servicios Remotos de Persistencia (SASeRPe).

El sistema distribuido SASeRPe realiza la invocación remota de métodos que incluyen el acceso a una base de datos. Esta aplicación está organizada en 3 capas: cliente, servidor RMI y la base de datos.

#### Descripción

El cliente es una aplicación liviana, ya que solamente es responsable de administrar la interfaz de usuario. Los procesos de negocio se transfieren al servidor RMI, el cual contiene objetos servidor distribuidos en el servicio de registro. El cliente realizará una búsqueda en el registro para obtener un objeto de manera remota, y poder invocar sus métodos. El servidor de la base de datos reside en una tercera capa que será accedida por los descriptores de acceso de datos que residen en el servidor RMI.

Este sistema mantiene un esquema básico de seguridad sobre los datos ya que el acceso a la base de datos desde los clientes es mediado por el servidor; además, la distribución en capas permite que la actualización y mantenimiento sean más sencillos, ya que el núcleo principal de los procesos de negocio se localizan en un servidor. Sin embargo, el sistema puede enfrentar cuellos de botella en el servidor, en el caso de que haya muchas peticiones concurrentes.

El código del sistema implementa operaciones de Crear, Leer, Actualizar y Eliminar registros de una lista de Provincias. El cliente invocará los métodos del servidor RMI para salvar, modificar, borrar y buscar registros de la base de datos, a través de la comunicación de dichas operaciones a los descriptores de acceso de datos y eventualmente al servidor de la base de datos.

**Base de Datos**

* Province contiene una tabla llamada Province con 3 atributos: Id (integer), ShortName(text:3), Name (text:255).

**Servidor**

* RMI
    * Province.java
    * IRemoteProvince.java
    * ProvinceObject.java
    * ProvinceServer.java
* Descriptores de acceso de datos
    * DBManager.java
    * ProvinceRepository.java

**Cliente**
* Province.java
* IRemoteProvince.java
* ProvinceClient.java

#### Instrucciones

Realice lo siguiente:

1. Capture, compile y ejecute el proyecto de sistema distribuido. Utilice una versión de java 1.8 (recomendación para no tener problemas de versiones).

2. Para que compile y ejecute este proyecto requiere utilizar un servidor de MySQL instalado y el conector MySQL para java correspondiente versión 8.0.24 (configurado en su IDE o CLASSPATH del sistema según sea el caso). En el servidor de base de datos es necesario crear la base de datos llamada Province de acuerdo a lo especificado. Puedes configurar un ambiente equivalente con MS Access y el conector correspondiente.

3. Puedes utilizar los IDEs: Eclipse, Netbeans o línea de comando para capturar el código, compilar y ejecutar. En cualquier caso, es necesario agregar al path del proyecto el conector del manejador de base de datos (MySQL o Access) para java para que pueda compilar y ejecutarse correctamente.

4. Identifica los elementos del modelo RMI en el código y describe la secuencia de interacción entre cliente y servidor. Modifica el código para solicitar y leer un nombre de ciudad desde teclado, agregarlo a la base de datos y desplegar la lista de ciudades existentes.