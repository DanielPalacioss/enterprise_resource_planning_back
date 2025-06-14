📦 enterprise_resource_planning_back
Backend en Spring Boot para un sistema de planificación de recursos empresariales (ERP).

📝 Descripción:

Este proyecto implementa el backend de un ERP usando Spring Boot. Proporciona una API REST para gestionar entidades como clientes, productos, órdenes, stock y usuarios. Se integra con bases de datos, validación, seguridad y manejo de errores.

✨ Características:
Estructura modular y organizada por capas (controller-service-repository-model).

API REST con endpoints CRUD.

Validación de datos con javax.validation.

Manejo de excepciones personalizado.

Soporte para seguridad con Spring Security.

Base de datos relacional PostgreSQL.

🧰 Tecnologías
Java 17+

Spring Boot (Web, Data JPA, Validation, Security)

Base de datos:PostgreSQL

Maven como gestor de construcción

🚀 Instalación
Clona el repositorio:

bash
Copy
Edit
git clone https://github.com/DanielPalacioss/enterprise_resource_planning_back.git
cd enterprise_resource_planning_back
Ajusta la configuración en src/main/resources/application.properties.

Ejecuta la aplicación:

bash
Copy
Edit
mvn spring-boot:run
⚙️ Configuración
En application.properties (o application.yml), configura:

# Producción
# spring.datasource.url=jdbc:postgresql://...
# spring.datasource.username=...
# spring.datasource.password=...
# spring.jpa.hibernate.ddl-auto=update
Puedes cambiar la base de datos y credenciales según tus necesidades.

📌 Endpoints principales
Recurso	Métodos	Ruta	Descripción
Productos	GET, POST, PUT, DELETE	/api/products	Gestión de productos
Clientes	GET, POST, PUT, DELETE	/api/clients	Gestión de clientes
Órdenes	GET, POST, PUT, DELETE	/api/orders	Gestión de órdenes
Usuarios	GET, POST, PUT, DELETE	/api/users	Gestión de usuarios
