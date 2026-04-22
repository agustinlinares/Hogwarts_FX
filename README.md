# 🏰 Hogwarts Database Manager

> **Programa para gestionar base de datos (Magos, Varitas y Casas) en un sistema de escritorio desarrollado 100% en Java con entorno gráfico Swing. Soporta operaciones CRUD completas en MySQL/MariaDB.**

---

<p align="center">
    <img src="assets/hogwarts.png" width="55%" alt="Ventana de gestión de Magos">
</p>

## 1. 🧙‍♂️ Características Principales

* **Gestión de Magos**: Permite **Crear, Leer, Actualizar y Eliminar** registros completos de personajes.
* **Gestión de Varitas**: Funcionalidad para **asignar o actualizar** los detalles de la varita de cada mago (madera, núcleo, longitud).
* **Gestión de Casas**: Asignación de casas. Por defecto, carga la casa **NO HOUSE / NO FOUNDER** como regla de negocio del sistema.
* **Persistencia de datos**: Conexión y almacenamiento de toda la información en una base de datos **MySQL/MariaDB** dedicada.

---
<p align="center">
    <img src="assets/hogwarts_add.png" width="45%" alt="Ventana de gestión de Magos"> 
    &nbsp; &nbsp; &nbsp; &nbsp; 
    <img src="assets/hogwarts_edit.png" width="45%" alt="Ventana de asignación de Varitas">
</p>

## 2. 💻 Tecnologías Utilizadas

* **Lenguaje**: **Java** (JDK 23).
* **Interfaz de Usuario**: **Swing** (Entorno Gráfico de Escritorio).
* **Base de Datos**: **MySQL** / **MariaDB**.
* **Gestor de Dependencias**: Proyecto construido con arquitectura **Maven**.
* **Conector DB**: **JDBC Connector** (MySQL 8.0.33), gestionado como dependencia en el `pom.xml`.

---

## 3. ⚙️ Instalación y Configuración

### Paso 1: Clonar el Repositorio

Clona este repositorio en tu máquina local para comenzar.
```
bash
git clone [https://github.com/AaronSGomez/Hogwarts](https://github.com/AaronSGomez/Hogwarts)
 ```

### Paso 2: Configuración de la Base de Datos

El proyecto requiere un servidor de base de datos MySQL/MariaDB en funcionamiento:

1.  Crea la base de datos en tu servidor.
2.  Carga la estructura de las tablas y los datos de prueba usando los archivos provistos en la carpeta `App/`. Puedes usar tu entorno gráfico SQL preferido (como MySQL Workbench o DBeaver) para ejecutar estos scripts:
    * **Estructura y Datos (Recomendado):** `App/data.sql` (Carga las tablas y datos por defecto).
    * **Solo Estructura:** `App/db.sql` (Si deseas empezar sin datos precargados).

### Paso 3: Variables de Conexión (MUY IMPORTANTE)
<p align="center">
    <img src="assets/hogwarts_bd.png" width="55%" alt="Ventana de gestión de Magos">
</p>

Debes configurar las credenciales de conexión de tu base de datos (usuario, contraseña y URL) en el código fuente.

* Localiza la clase de conexión en el paquete: `Database/DBConenection`.
* Asegúrate de configurar estas variables en tu IDE antes de la ejecución.

### Paso 4: Ejecución

El proyecto puede ser ejecutado de dos maneras, tras compilarlo con Maven:

* **Opción Gráfica (Recomendada):** Ejecuta la clase **MainSwing.java**.
* **Opción Consola:** Ejecuta la clase **Main.java**.

---

## 4. 🌀 Uso y Validaciones

### Flujo de la Interfaz Gráfica

* La ventana principal carga, tras una conexión exitosa a la DB, la lista completa de magos, incluyendo datos de Casa y Varita mediante una consulta combinada.
* Las modificaciones o eliminaciones de registros solo se inician al **seleccionar un mago** previamente en la tabla.
* La eliminación requiere una **confirmación** adicional con un mensaje emergente tipo *warning*.

### Reglas del Mundo Mágico (Validaciones)

* **Creación de Mago:** Al agregar, solo se piden Nombre y Edad. La varita **no es obligatorio** añadirla inmediatamente.
* **Datos Obligatorios:**
    * El mago debe **obligatoriamente** tener Nombre y Edad.
    * La varita debe **obligatoriamente** tener Madera, Núcleo y Longitud si se crea su registro.
* **Edición:** Al editar un mago, los datos cargan, pero **no es posible modificar el ID** (identificador único).

<p align="center">
    <img src="assets/hogwarts_remove.png" width="55%" alt="Ventana de gestión de Magos">
</p>