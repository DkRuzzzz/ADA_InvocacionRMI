# Funcionamiento del sistema

El sistema distribuido implementa una arquitectura de tres capas compuesta por:

* Cliente
    - IRemoteProvince
    - Province
    - ProvinceClient
* Servidor
    - ProvinceObject
    - ProvinceServer
* Base de datos
    - DBManager
    - ProvinceRepository

Cada clase participa en una parte específica del flujo de ejecución del programa.

---

## Flujo general del sistema

Cuando el programa inicia, primero se ejecuta ProvinceServer. Esta clase crea el registro RMI en el puerto 1099 e instancia el objeto remoto ProvinceObject. Posteriormente registra dicho objeto utilizando el nombre "Province" para que pueda ser localizado remotamente por los clientes.

Después se ejecuta ProvinceClient. El cliente utiliza:

```java
LocateRegistry.getRegistry("127.0.0.1")
```

para conectarse al registro RMI local y posteriormente ejecuta:

```java
registry.lookup("Province")
```

para obtener una referencia remota al objeto servidor.

Aunque el cliente obtiene un objeto de tipo IRemoteProvince, en realidad está interactuando con un objeto remoto que reside físicamente en el servidor RMI.

---

## Interacción del cliente con el servidor

La clase ProvinceClient contiene un menú interactivo que permite al usuario seleccionar operaciones CRUD sobre la base de datos.

Dependiendo de la opción elegida, el cliente solicita información desde teclado utilizando Scanner y crea un objeto Province.

Por ejemplo, cuando el usuario selecciona agregar una ciudad:

```java
rp.save(p);
```

el objeto Province es serializado automáticamente por RMI y enviado al servidor remoto.

---

## Procesamiento en el servidor

La llamada remota llega a ProvinceObject, que implementa la interfaz IRemoteProvince.

Cada método de esta clase funciona como intermediario entre el cliente y la capa de persistencia. Por ejemplo:

```java
public int save(Province p)
```
recibe el objeto enviado por el cliente y delega la operación a:

```java
ProvinceRepository.save(p);
```
Además, ProvinceObject utiliza:

```java
getClientHost()
```

para identificar la dirección IP del cliente que realizó la invocación remota.

---

## Acceso a la base de datos

La clase ProvinceRepository ejecuta las operaciones SQL correspondientes utilizando JDBC.

Cuando se invoca:

```java
ProvinceRepository.save(p);
```

la clase obtiene una conexión mediante:

```java
DBManager.getInstance().getConnection();
```

y posteriormente ejecuta instrucciones SQL usando PreparedStatement.

---

## Retorno de resultados al cliente

Después de ejecutar la operación SQL, el resultado regresa desde ProvinceRepository hacia ProvinceObject.

Posteriormente RMI transmite automáticamente la respuesta al cliente.

En operaciones de consulta como:

```java
rp.findAll();
```

el servidor devuelve un ArrayList<Province> que es serializado y enviado nuevamente al cliente para ser desplegado en pantalla.

---

## Comunicación distribuida del sistema

El sistema implementa comunicación distribuida mediante Java RMI, permitiendo que el cliente invoque métodos sobre objetos ubicados en otro proceso como si fueran métodos locales.

La serialización de objetos, el transporte de mensajes y la comunicación de red son gestionados automáticamente por RMI, lo que simplifica la implementación del sistema distribuido.