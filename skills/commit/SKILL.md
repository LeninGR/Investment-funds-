---
name: commit
description: >
  Habilidad para generar mensajes de commit siguiendo el estándar del proyecto
  para alimentar el CHANGELOG. Trigger: Al finalizar una tarea y antes de
  realizar el commit de los cambios.
license: Apache-2.0
metadata:
  author: flexible-scheduling-team
  version: '1.1'
  scope: [root]
  auto_invoke:
    - 'Generar mensaje de commit'
    - 'Clasificar cambios para el historial'
allowed-tools: Read, Edit, Write, Glob, Grep, Bash
---

# Skill: Commit

Esta habilidad guía al agente para crear mensajes de commit que sean
descriptivos y sigan el estándar del proyecto para facilitar el seguimiento de
cambios.

## 📋 Reglas Críticas

- **Formato**: `tipo: descripción corta` (ej. `feat: añadir selector de fecha`).
- **Minúsculas**: El tipo debe estar siempre en minúsculas.
- **Descripción**: Debe ser clara, en español y en tiempo presente (ej.
  "añadir", no "añadido").
- **Concisión**: La primera línea no debe exceder los 50 caracteres.
- **Contexto**: Si el cambio es complejo, usar el cuerpo del commit para
  explicar el "por qué".

## 🏗️ Tipos de Commit y Mapeo

| Tipo       | Uso principal                          |
| ---------- | -------------------------------------- |
| `feat`     | Nuevas funcionalidades.                |
| `fix`      | Corrección de errores.                 |
| `docs`     | Cambios en documentación.              |
| `style`    | Cambios de estilo/formato (no lógica). |
| `refactor` | Refactorización de código existente.   |
| `perf`     | Mejoras de rendimiento.                |
| `test`     | Añadir o modificar pruebas.            |
| `build`    | Cambios en dependencias o scripts.     |
| `security` | Mejoras de seguridad.                  |

## 💡 Ejemplos de Mensajes

- `feat: implementar servicio de persistencia de citas`
- `fix: corregir error de renderizado en el calendario de Android`
- `docs: actualizar guía de configuración de Supabase`
- `refactor: simplificar lógica de validación en BookingService`

## 🤖 Uso por el Agente

Al finalizar una tarea, el agente debe:

1. Analizar los archivos modificados.
2. Elegir el tipo de commit adecuado según la tabla anterior.
3. Generar el mensaje siguiendo el formato estricto.
