---
name: skill-creator
description: >
  Habilidad para crear nuevas habilidades (skills) para este repositorio
  siguiendo el estándar de metadata y estructura. Trigger: Cuando el usuario
  solicita crear una nueva skill o automatizar un nuevo flujo de trabajo.
license: Apache-2.0
metadata:
  author: flexible-scheduling-team
  version: '1.1'
  scope: [root, skills]
  auto_invoke:
    - 'Crear una nueva skill'
    - 'Definir un nuevo flujo de trabajo para agentes'
    - 'Actualizar el estándar de skills'
allowed-tools: Read, Edit, Write, Glob, Grep, Bash
---

# Skill: Skill Creator

Esta habilidad permite al agente crear nuevas habilidades para este repositorio
siguiendo el estándar de metadatos y estructura de Prowler.

## 🏗️ Proceso de Creación

1. **Identificar la necesidad**: Determinar si hay un patrón repetitivo, una
   herramienta nueva o un flujo que requiere guías específicas.
2. **Crear carpeta**: `skills/{nombre-skill}/`.
3. **Crear SKILL.md**: Seguir el formato de metadatos (frontmatter) y secciones
   obligatorias.
4. **Registrar en AGENTS.md**: Añadir la nueva skill a la tabla de referencia y,
   si aplica, a la sección de `Auto-invoke Skills`.
5. **Sincronizar**: Ejecutar `./skills/setup.sh` para crear los enlaces
   simbólicos.

## 📝 Formato Obligatorio (SKILL.md)

Cada nueva skill DEBE comenzar con el bloque de frontmatter:

```markdown
---
name: { nombre-skill }
description: >
  Descripción clara de la skill. Trigger: Cuándo debe activarse esta skill.
license: Apache-2.0
metadata:
  author: flexible-scheduling-team
  version: '1.0'
  scope: [root, app, components, etc]
  auto_invoke:
    - 'Acción 1'
    - 'Acción 2'
allowed-tools: Read, Edit, Write, Glob, Grep, Bash
---

# Skill: {Nombre}

## 📋 Reglas Críticas

- **REGLA ALWAYS**: Qué debe hacer siempre el agente.
- **REGLA NEVER**: Qué tiene prohibido el agente.

## 🛠️ Ejemplos y Patrones

...
```

## 🎯 Criterios de Calidad

- **Concisión**: No repetir lo que la IA ya sabe por defecto.
- **Enfoque en Reglas**: Priorizar reglas críticas (ALWAYS/NEVER).
- **Ejemplos Reales**: Proporcionar fragmentos de código que sigan los
  estándares del proyecto.
