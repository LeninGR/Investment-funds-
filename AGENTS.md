# AGENTS.md

Este archivo proporciona contexto y guías específicas para agentes de IA que trabajan en el proyecto **Investment Funds**.

## 🚀 Comandos de Configuración

- **Compilar proyecto**: `./gradlew build`
- **Ejecutar pruebas**: `./gradlew test`
- **Ejecutar localmente**: `./gradlew bootRun`
- **Generar artefacto Lambda**: `./gradlew clean shadowJar`
- **Desplegar en AWS**: `serverless deploy`

## 🛠️ Stack Tecnológico

- **Lenguaje**: Java 17 (Gestionado vía Gradle Toolchains)
- **Framework**: Spring Boot 3.2.5
- **Base de Datos**: MongoDB (Spring Data MongoDB)
- **Infraestructura**: AWS Lambda (Serverless Framework)
- **Gestor de Construcción**: Gradle 8.5
- **Testing**: JUnit 5, Mockito

## 🏗️ Arquitectura Hexagonal

El proyecto sigue estrictamente una arquitectura hexagonal (Puertos y Adaptadores):

- **Domain (`src/main/java/com/investment/funds/domain`)**:
  - Núcleo de la lógica de negocio.
  - Entidades: `Client`, `Fund`, `Transaction`.
  - Puertos (Interfaces): `ClientRepository`, `FundRepository`, `Notification`.
  - Servicios de Dominio: Lógica que no pertenece a una sola entidad.
  - **Regla**: No debe tener dependencias de frameworks externos (Spring, AWS).

- **Application (`src/main/java/com/investment/funds/application`)**:
  - Casos de Uso (`UseCases`): Orquestan la lógica de negocio implementando interfaces de entrada.
  - DTOs: Objetos de transferencia de datos para entrada/salida de casos de uso.

- **Infrastructure (`src/main/java/com/investment/funds/infrastructure`)**:
  - Adaptadores de Entrada: Controladores REST (`Controller`).
  - Adaptadores de Salida: Implementaciones de Repositorios (`MongoRepository`), Notificaciones (`Email`, `SMS`).
  - Configuración: Clases de configuración de Spring (`@Configuration`).

## 📝 Convenciones de Código

- **Estilo**: Google Java Style.
- **Nombramiento de Pruebas**: Convención TDD `methodName_ShouldExpectedBehavior_WhenState` (ej: `subscribe_ShouldReturnOk_WhenSuccessful`).
- **Controladores**:
  - Usar `ResponseEntity<?>` para todas las respuestas.
  - Devolver JSON válido.
  - Manejar excepciones de negocio mediante `ControllerAdvice` (si aplica) o `try-catch` con respuestas HTTP adecuadas (400, 404, 500).
- **Inyección de Dependencias**: Preferir inyección por constructor.

## 🔄 Flujo de Trabajo y Commits

- **Commits**: Deben seguir el formato estándar (`tipo: descripción`).
  - Tipos: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`.
- **TDD**: Escribir pruebas unitarias antes o durante la implementación de la lógica.

## 🎯 Skills del Agente

Para tareas específicas, consulta las skills disponibles en el directorio `skills/`:

| Skill | Descripción |
|-------|-------------|
| [tdd](file:///skills/tdd/SKILL.md) | Guía para desarrollo guiado por pruebas (Test Driven Development). |
| [commit](file:///skills/commit/SKILL.md) | Guía para realizar commits convencionales. |
| [pr](file:///skills/pr/SKILL.md) | Convenciones para Pull Requests. |
| [skill-creator](file:///skills/skill-creator/SKILL.md) | Crear nuevas habilidades. |
| [skill-sync](file:///skills/skill-sync/SKILL.md) | Sincronización de skills entre repositorios. |

## Auto-invoke Skills

Al realizar estas acciones, SIEMPRE invoca primero la skill correspondiente:

| Acción | Invocar Primero | Motivo |
|--------|-----------------|--------|
| Implementar nueva funcionalidad o corregir bugs | `tdd` | Asegurar cobertura de pruebas y diseño robusto |
| Escribir commits | `commit` | Formato convencional y descripciones claras |
| Crear Pull Request | `pr` | Checklist y formato estándar |

## Notas Específicas del Proyecto

- **Serverless**: La aplicación corre en AWS Lambda usando `aws-serverless-java-container`.
- **Arranque**: La clase principal `InvestmentFundsApplication` extiende `SpringBootServletInitializer`, sobreescribe `configure()` y usa `@EnableWebMvc` junto con `@Import(DispatcherServletAutoConfiguration.class)` para garantizar la inicialización correcta del `DispatcherServlet` en Lambda.
- **Respuestas HTTP**: Se requiere la dependencia `jackson-databind` para la serialización JSON correcta y evitar errores 406. Los controladores deben devolver `ResponseEntity<?>`.
- **Logging**: Se usa SLF4J/Logback. En producción (Lambda), mantener niveles de log en INFO para evitar costos excesivos y ruido.
