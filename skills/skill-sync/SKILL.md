---
name: skill-sync
description: >
  Sincroniza los metadatos de todas las skills (auto_invoke, scope, etc.) con el
  archivo AGENTS.md principal. Trigger: Al crear o modificar cualquier skill.
license: Apache-2.0
metadata:
  author: flexible-scheduling-team
  version: '1.0'
  scope: [root, skills]
  auto_invoke:
    - 'Sincronizar metadatos de skills'
    - 'Actualizar AGENTS.md después de cambiar una skill'
allowed-tools: Read, Edit, Write, Glob, Grep, Bash
---

# Skill: Skill Sync

Esta habilidad automatiza la actualización del archivo `AGENTS.md` principal,
asegurando que la tabla de skills y la sección de auto-invocación estén siempre
sincronizadas con los metadatos de los archivos `SKILL.md`.

## 🤖 Proceso de Sincronización

El script `assets/sync.sh` realiza las siguientes acciones:

1.  **Encuentra todas las skills**: Busca todos los directorios dentro de
    `skills/` que contengan un archivo `SKILL.md`.
2.  **Lee los Metadatos**: Extrae el `frontmatter` (name, description,
    auto_invoke) de cada `SKILL.md`.
3.  **Genera las Tablas**: Crea dinámicamente la tabla general de skills y la
    tabla de `Auto-invoke Skills` en formato Markdown.
4.  **Actualiza AGENTS.md**: Reemplaza los bloques marcados con
    `<!-- SKILLS_TABLE_START -->` y `<!-- AUTO_INVOKE_TABLE_START -->` en
    `AGENTS.md` con las tablas recién generadas.

## 🚀 Cómo Usar

Simplemente ejecuta el script desde la raíz del proyecto:

```bash
./skills/skill-sync/assets/sync.sh
```

**CRÍTICO**: Debes ejecutar este script cada vez que crees una nueva skill o
modifiques los metadatos (`auto_invoke`, `description`) de una existente.
