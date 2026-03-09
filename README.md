# Investment Funds - Prueba Técnica

> **Disclaimer:** Este repositorio no corresponde a código oficial de BTG Pactual, únicamente corresponde a una prueba técnica personal.

Este proyecto implementa una API REST para la gestión de fondos de inversión, permitiendo a los clientes suscribirse y cancelar suscripciones a fondos, así como consultar su historial de transacciones.

## 🏗 Arquitectura

El proyecto sigue una **Arquitectura Hexagonal (Puertos y Adaptadores)** para desacoplar la lógica de negocio de los detalles de implementación y la infraestructura.

### Estructura del Proyecto
- **Application (`application/`)**: Contiene los casos de uso (Use Cases) que orquestan la lógica de negocio.
- **Domain (`domain/`)**: Contiene las entidades del dominio, excepciones y puertos (interfaces) para repositorios y servicios externos.
- **Infrastructure (`infrastructure/`)**: Contiene la implementación de los adaptadores (Controladores REST, Repositorios MongoDB, Notificaciones) y la configuración del framework.

## 🛠 Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación.
- **Spring Boot 3.2.5**: Framework para el desarrollo de la aplicación.
- **MongoDB**: Base de datos NoSQL.
- **AWS Lambda**: Plataforma de computación serverless donde se despliega la aplicación.
- **Serverless Framework**: Herramienta para facilitar el despliegue en AWS.
- **Gradle**: Gestor de dependencias y construcción.
- **JUnit 5 & Mockito**: Frameworks para pruebas unitarias y TDD.

## 🚀 Ejecución Local

### Prerrequisitos
- Java 17+
- Gradle 8.x (o usar el wrapper incluido)
- MongoDB (local o contenedor Docker)

### Pasos
1. Clonar el repositorio.
2. Configurar las variables de entorno para la base de datos en `src/main/resources/application.properties` o mediante variables del sistema:
   ```properties
   MONGO_HOST=localhost
   MONGO_PORT=27017
   MONGO_DATABASE=investment_funds_db
   ```
3. Ejecutar la aplicación:
   ```bash
   ./gradlew bootRun
   ```

## 🧪 Pruebas (TDD)

El proyecto ha sido desarrollado siguiendo prácticas de **TDD (Test Driven Development)**.
Las pruebas unitarias cubren casos de uso, controladores y servicios de dominio.

Para ejecutar las pruebas:
```bash
./gradlew test
```

## ☁️ Despliegue en AWS Lambda

El proyecto utiliza `aws-serverless-java-container` para ejecutar Spring Boot dentro de una función Lambda.

### Pasos de Despliegue
1. Generar el artefacto (Shadow Jar):
   ```bash
   ./gradlew clean shadowJar
   ```
2. Desplegar con Serverless Framework:
   ```bash
   serverless deploy
   ```

### Configuración en AWS
Es necesario configurar las siguientes variables de entorno en la función Lambda (o en `serverless.yml`):
- `MONGO_HOST`
- `MONGO_PORT`
- `MONGO_DATABASE`
- `MONGO_USERNAME`
- `MONGO_PASSWORD`
- `SPRING_PROFILES_ACTIVE=prod`

## 📚 API Endpoints

### Clientes
- `GET /clients/{clientId}`: Obtener información de un cliente.

### Fondos
- `POST /funds/subscribe`: Suscribirse a un fondo.
- `POST /funds/cancel`: Cancelar suscripción a un fondo.

### Transacciones
- `GET /transactions/history/{clientId}`: Obtener historial de transacciones.

### Utilidades
- `POST /helper/seed`: Poblar la base de datos con datos de prueba (Clientes y Fondos iniciales).
