# Descripción de Clases

---

## Cliente

### IRemoteProvince

La interfaz IRemoteProvince define el contrato remoto del sistema distribuido. En ella se declaran los métodos que podrán ser invocados remotamente por el cliente:

    - save(Province p): Guarda la información de una ciudad
    - update(Province p): Actualiza la información de una ciudad
    - delete(Province p): Elimina una ciudad 
    - deleteAll(): Elimina todas las ciudades de la Base de Datos
    - findAll(): Muestra todas las ciudades de la Base de Datos
    - findByName(String criteria): Encuentra una ciudad por su nombre

De igual manera, extiende el paquete Remote, lo cual indica que sus métodos podrán ejecutarse remotamente a través de Java RMI. Todos los métodos lanzan RemoteException para manejar posibles errores de comunicación en la red.


### Province

La clase Province representa la entidad que será almacenada y consultada en la base de datos. Cada objeto Province contiene los siguientes parámetros:

    - id: Identificador de la ciudad
    - name: Nombre de la ciudad
    - shortName: Abreviatura 

Además, implementa la interfaz Serializable, lo cual permite que los objetos puedan ser serializados y transmitidos entre cliente y servidor mediante RMI. Esto es necesario porque los objetos Province viajan como parámetros y resultados de las invocaciones remotas.

### ProvinceClient

La clase ProvinceClient representa la aplicación cliente del sistema distribuido. Su función principal es conectarse al registro RMI, localizar el objeto remoto publicado por el servidor e invocar sus métodos.

El cliente obtiene una referencia remota mediante LocateRegistry.getRegistry() y lookup(). Posteriormente utiliza dicha referencia para ejecutar operaciones CRUD sobre la base de datos sin acceder directamente a ella.

En nuestra implementación, el cliente es capaz de ejecutar múltiples acciones como solicitar información y agregar o eliminar ciudades dinámicamente.

---

## Servidor

### ProvinceObject

La clase ProvinceObject implementa la lógica del objeto remoto del servidor. Hereda de UnicastRemoteObject e implementa la interfaz IRemoteProvince.

Cada método remoto recibe solicitudes del cliente y delega las operaciones a ProvinceRepository, que es la capa encargada del acceso a datos. Además, utiliza getClientHost() para mostrar la dirección IP del cliente que realiza la invocación remota.

Esta clase funciona como intermediario entre el cliente RMI y la capa de persistencia.

### ProvinceServer

La clase ProvinceServer inicializa el servidor RMI. Su responsabilidad es crear el registro RMI en el puerto 1099, instanciar el objeto remoto ProvinceObject y registrarlo mediante rebind().

Gracias a este proceso, los clientes pueden localizar el objeto remoto utilizando el nombre "Province" y posteriormente invocar sus métodos

---

## Manejo de datos

### DBManager

La clase DBManager administra la conexión con la base de datos MySQL utilizando JDBC.

Su objetivo principal es centralizar el manejo de la conexión y evitar la creación innecesaria de múltiples conexiones durante la ejecución del servidor.

La clase implementa el patrón de diseño Singleton, lo que garantiza que solamente exista una única instancia compartida de DBManager en toda la aplicación.

    - public static synchronized DBManager getInstance() {}: crea y devuelve la única instancia de la clase DBManager
    - public Connection getConnection() {}: devuelve el objeto Connection almacenado en la variable _con
    - private static Connection getMySQLConnection() {}: es el encargado de establecer la conexión con la base de datos MySQL.

### ProvinceRepository

La clase ProvinceRepository constituye la capa de acceso a datos del sistema. Contiene los métodos encargados de ejecutar las operaciones SQL sobre la tabla Province.

Entre sus funciones se encuentran:

    - save(Province p) {}: Inserta registros al recibir id, name, shortName
    - update(Province p) {}: Se encarga de actualizar el registro que recibe como parámetro
    - delete(Province p) {}: Se encarga de eliminar el registro que recibe como parámetro
    - deleteAll() {}: Se encarga de eliminar todos los registros
    - findAll() {}: Se encarga de consultar todos los registros 
    - findByName(String name) {}: Busca el registro del parámetro recibido

La clase utiliza objetos PreparedStatement y ResultSet para ejecutar consultas SQL de manera segura y obtener resultados desde la base de datos.
