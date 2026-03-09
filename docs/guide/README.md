# Arquitectura del Proyecto Investment Funds

Este documento detalla y justifica las decisiones arquitectónicas tomadas para el desarrollo del proyecto **Investment Funds**, así como un diagrama de componentes que ilustra la estructura del sistema.

## 🏗️ Estilo de Arquitectura: Hexagonal (Puertos y Adaptadores)

El proyecto sigue estrictamente una **Arquitectura Hexagonal**. Este patrón permite que una aplicación sea controlada por usuarios, programas, pruebas automatizadas o scripts por igual, y que sea desarrollada y probada en aislamiento de sus eventuales dispositivos de ejecución y bases de datos.

### 📚 Justificación

La elección de la Arquitectura Hexagonal se basa en los siguientes beneficios clave para este dominio financiero:

1.  **Desacoplamiento del Núcleo de Negocio**:
    *   La lógica de dominio (`domain`) no depende de frameworks externos, ni de la base de datos, ni de la web. Esto garantiza que las reglas de negocio (ej. validación de saldos, reglas de suscripción) permanezcan puras y no se vean afectadas por cambios tecnológicos.
    *   *Ejemplo*: Si mañana decidimos cambiar MongoDB por PostgreSQL, el dominio no requiere ninguna modificación.

2.  **Testabilidad (TDD)**:
    *   Al aislar el dominio, es posible escribir pruebas unitarias rápidas y exhaustivas sin necesidad de levantar contextos de Spring o contenedores de base de datos.
    *   Los casos de uso (`application`) se pueden probar fácilmente usando mocks para los puertos de salida (repositorios).

3.  **Flexibilidad de Infraestructura**:
    *   Los adaptadores de entrada (Controladores REST) y salida (Persistencia, Notificaciones) son intercambiables.
    *   *Caso Real*: El proyecto cuenta con una implementación `InMemory` para desarrollo local y una implementación `Mongo` para producción, gestionadas transparentemente mediante perfiles de Spring.

4.  **Mantenibilidad a Largo Plazo**:
    *   La clara separación de responsabilidades facilita la navegación por el código y la incorporación de nuevos desarrolladores. Se sabe exactamente dónde va la lógica (Dominio/Aplicación) y dónde van los detalles técnicos (Infraestructura).

## ☁️ Infraestructura y Despliegue

### AWS Lambda (Serverless)
Se optó por un enfoque **Serverless** utilizando AWS Lambda y `aws-serverless-java-container`.

*   **Justificación**:
    *   **Costos**: Modelo de pago por uso, ideal para APIs que pueden tener tráfico variable o esporádico.
    *   **Escalabilidad**: AWS gestiona automáticamente el escalado horizontal.
    *   **Simplicidad Operativa**: No requiere gestión de servidores (EC2) ni orquestadores de contenedores (K8s) complejos para este alcance.

### Spring Boot
Aunque Spring Boot es un framework robusto, su uso se limita a la capa de **Infraestructura** para inyección de dependencias y exposición web.

*   **Adaptación**: Se utiliza `SpringBootServletInitializer` y el adaptador de AWS para que la aplicación Spring arranque dentro del ciclo de vida de una función Lambda.

### MongoDB
Base de datos NoSQL orientada a documentos.

*   **Justificación**:
    *   Flexibilidad para evolucionar el esquema de datos de `Fondos` y `Transacciones` sin migraciones rígidas.
    *   Alto rendimiento para operaciones de lectura/escritura.

## 🧩 Diagrama de Componentes

El siguiente diagrama muestra cómo interactúan las capas de la aplicación, respetando la regla de dependencia (las dependencias apuntan hacia adentro).

```mermaid
graph TD
    subgraph "External Actors"
        User(Cliente - API Consumer)
        Admin(Administrador)
    end

    subgraph "Infrastructure Layer - Driving Adapters"
        APIGateway[AWS API Gateway]
        LambdaHandler[StreamLambdaHandler]
        Controllers[Rest Controllers]
    end

    subgraph "Application Layer"
        UseCases[Casos de Uso - Subscribe, Cancel, History]
        DTOs[DTOs Input/Output]
    end

    subgraph "Domain Layer - Core"
        Entities[Entidades - Client, Fund, Transaction]
        DomainServices[Servicios de Dominio]
        PortsIn[Puertos Entrada - Interfaces UseCase]
        PortsOut[Puertos Salida - Interfaces Repository/Notification]
    end

    subgraph "Infrastructure Layer - Driven Adapters"
        MongoAdapter[Mongo Repository Adapter]
        InMemoryAdapter[In-Memory Repository Adapter]
        NotifyAdapter[Notification Adapter]
    end

    subgraph "External Systems"
        MongoDB[(MongoDB Atlas)]
        EmailSvc[Email Service]
        SMSSvc[SMS Service]
    end

    %% Relationships
    User --> APIGateway
    Admin --> APIGateway
    APIGateway --> LambdaHandler
    LambdaHandler --> Controllers
    
    Controllers --> UseCases
    UseCases -.-> DTOs
    
    UseCases --> DomainServices
    UseCases --> Entities
    UseCases ..|> PortsIn
    UseCases --> PortsOut
    DomainServices --> Entities
    
    MongoAdapter ..|> PortsOut
    InMemoryAdapter ..|> PortsOut
    NotifyAdapter ..|> PortsOut
    
    MongoAdapter --> MongoDB
    NotifyAdapter --> EmailSvc
    NotifyAdapter --> SMSSvc

    %% Styles
    classDef domain fill:#e1f5fe,stroke:#01579b,stroke-width:2px;
    classDef app fill:#fff3e0,stroke:#e65100,stroke-width:2px;
    classDef infraDriving fill:#e8f5e9,stroke:#1b5e20,stroke-width:2px;
    classDef infraDriven fill:#f3e5f5,stroke:#4a148c,stroke-width:2px;

    class Entities,DomainServices,PortsIn,PortsOut domain;
    class UseCases,DTOs app;
    class APIGateway,LambdaHandler,Controllers infraDriving;
    class MongoAdapter,InMemoryAdapter,NotifyAdapter infraDriven;
```

## 📂 Estructura de Paquetes

```text
com.investment.funds
├── application         <-- Capa de Aplicación
│   ├── usecase         <-- Implementación de Casos de Uso
│   └── dto             <-- Objetos de transferencia
├── domain              <-- Capa de Dominio (Núcleo)
│   ├── model           <-- Entidades y Value Objects
│   ├── port            <-- Interfaces (Puertos)
│   ├── service         <-- Lógica de dominio pura
│   └── exception       <-- Excepciones de negocio
└── infrastructure      <-- Capa de Infraestructura
    ├── adapter         <-- Implementaciones de Puertos (Mongo, Email, etc.)
    ├── configuration   <-- Configuración de Spring Beans
    └── controller      <-- Endpoints REST
```
