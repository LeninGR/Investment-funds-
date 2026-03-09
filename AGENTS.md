# AGENTS.md

Este archivo proporciona contexto y guías específicas para agentes de IA que
trabajan en el proyecto **Flexible Scheduling**.

## 🚀 Comandos de Configuración

- **Instalar dependencias**: `npm install`
- **Verificar Configuración TS**: Asegurar que `tsconfig.json` incluya
  `esModuleInterop` y `jsx`.
- **Iniciar servidor de desarrollo**: `npm run dev`
- **Preparar Commit**: Ejecutar `npm run lint:check` y `npm run type-check`
  antes de intentar realizar un commit para evitar fallos en los hooks de Husky.
- **Validar proyecto**: `npm run validate` (Ejecuta type-check, lint, format y
  auditoría de seguridad)
- **Formatear código**: `npm run format`
- **Ejecutar Lint**: `npm run lint`

## 🛠️ Stack Tecnológico

- **Framework**: Expo (React Native) con Expo Router.
- **Lenguaje**: TypeScript.
- **UI**: Lucide React Native, React Native Calendars, Reanimated.
- **Servicios**: Supabase, EmailJS.
- **Calidad**: ESLint, Prettier, Husky (pre-commit hooks).

## 📝 Convenciones de Código

- **Componentes**: Usar componentes funcionales y Hooks.
- **Estilos**: Preferir el uso de `StyleSheet.create` o patrones de diseño
  consistentes con los componentes de UI existentes en `components/ui/`.
- **Tipado**: Mantener un tipado estricto en TypeScript. Evitar el uso de `any`.
- **Archivos**: Seguir la estructura de carpetas:
  - `app/`: Rutas y navegación.
  - `components/`: Componentes reutilizables.
  - `services/`: Lógica de negocio e integración con APIs.
  - `hooks/`: Lógica de estado reutilizable.
  - `utils/`: Funciones de utilidad.

## 🔄 Flujo de Trabajo y Commits

- **Commits**: Deben seguir el formato estándar (`tipo: descripción`).
- **Tipos soportados**: `feat`, `fix`, `docs`, `style`, `refactor`, `perf`,
  `test`, `build`, `ci`, `chore`, `security`.
- **Changelog**: Se gestiona manualmente siguiendo keepachangelog.com con la
  skill
  [changelog](file:///Users/max/Documents/workspace/flexible-scheduling/skills/changelog/SKILL.md).
  El flujo MCP previo queda DEPRECADO; no usar `npm run mcp:*` para este fin.

## 🎯 Skills del Agente

Para tareas específicas, consulta las skills disponibles en el directorio
`skills/`:

| Skill                                                                                                    | Descripción                                               |
| -------------------------------------------------------------------------------------------------------- | --------------------------------------------------------- |
| [commit](file:///Users/max/Documents/workspace/flexible-scheduling/skills/commit/SKILL.md)               | Guía detallada para realizar commits compatibles con MCP. |
| [changelog](file:///Users/max/Documents/workspace/flexible-scheduling/skills/changelog/SKILL.md)         | Gestión y actualización del historial de cambios.         |
| [react](file:///Users/max/Documents/workspace/flexible-scheduling/skills/react/SKILL.md)                 | Patrones y mejores prácticas de React/Expo en este repo.  |
| [typescript](file:///Users/max/Documents/workspace/flexible-scheduling/skills/typescript/SKILL.md)       | Reglas de tipado estricto y patrones de TypeScript.       |
| [pr](file:///Users/max/Documents/workspace/flexible-scheduling/skills/pr/SKILL.md)                       | Convenciones para Pull Requests.                          |
| [skill-creator](file:///Users/max/Documents/workspace/flexible-scheduling/skills/skill-creator/SKILL.md) | Crear nuevas habilidades para este repo.                  |

## Auto-invoke Skills

Al realizar estas acciones, SIEMPRE invoca primero la skill correspondiente:

| Acción                                             | Invocar Primero | Motivo                                                  |
| -------------------------------------------------- | --------------- | ------------------------------------------------------- |
| Crear o modificar componentes/pantallas React/Expo | `react`         | Patrones de UI, navegación, hooks y consistencia visual |
| Escribir commits                                   | `commit`        | Formato MCP, tipos de commit y descripciones claras     |
| Actualizar el changelog                            | `changelog`     | Reglas Keep a Changelog, formato y convenciones         |
| Añadir tipos o refactorizar TypeScript             | `typescript`    | Tipado estricto, utilidades y convenciones de TS        |
| Preparar un Pull Request                           | `pr`            | Título, descripción, pasos de prueba y checklist        |
| Crear una nueva skill                              | `skill-creator` | Estructura, nomenclatura y frontmatter de skills        |

## Cómo Funcionan las Skills

1. Detección: El asistente lee este AGENTS.md y los SKILL.md vinculados en
   `skills/`.
2. Selección: Antes de ejecutar una acción de la tabla anterior, debe abrir la
   skill indicada.
3. Aplicación: Se siguen exactamente los patrones y reglas de la skill
   correspondiente.
4. Verificación: Se ejecuta `npm run validate` y se revisa `CHANGELOG.md`
   actualizado.

## Estructura de Skills

```
skills/                               # Skills específicas del repositorio
├── setup.sh                          # Script de sincronización de symlinks
├── commit/SKILL.md                   # Reglas de commits
├── changelog/SKILL.md                # Gestión del CHANGELOG (Keep a Changelog)
├── react/SKILL.md                    # Patrones de React/Expo
├── typescript/SKILL.md               # Convenciones de TypeScript
├── pr/SKILL.md                       # Convenciones de Pull Requests
└── skill-creator/SKILL.md            # Crear nuevas skills

.claude/skills/                       # Enlaces simbólicos (interoperabilidad)
.github/skills/
.gemini/skills/
```

---

_Este archivo es una guía para agentes. Para humanos, consulte el
[README.md](file:///Users/max/Documents/workspace/flexible-scheduling/README.md)._
