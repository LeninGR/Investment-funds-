---
name: pr
description: >
  Habilidad para generar descripciones de Pull Request que faciliten la revisión
  humana y mantengan la trazabilidad. Trigger: Al finalizar una tarea y antes de
  dar por terminado el trabajo.
license: Apache-2.0
metadata:
  author: flexible-scheduling-team
  version: '1.1'
  scope: [root]
  auto_invoke:
    - 'Generar resumen de cambios para PR'
    - 'Completar checklist de calidad'
allowed-tools: Read, Edit, Write, Glob, Grep, Bash
---

# Skill: Pull Request Conventions

Guía para que el agente genere descripciones de PR que faciliten la revisión
humana.

## 📋 Estructura Obligatoria

1. **Título**: `[TIPO] Descripción breve` (ej:
   `[FEAT] Integración con Supabase Auth`).
2. **Descripción**: Explicar claramente el **qué** y el **por qué**.
3. **Cambios Clave**: Lista de archivos o funciones principales modificadas.
4. **Cómo Probar**: Pasos para verificar el cambio.

## 📋 Reglas Críticas

- **ALWAYS**: Incluir los pasos de prueba detallados.
- **ALWAYS**: Mencionar si se requiere actualizar el `CHANGELOG.md`.
- **NEVER**: Dejar la descripción vacía o genérica.
- **NEVER**: Olvidar ejecutar `npm run validate` antes de proponer el PR.

## ✅ Checklist de Calidad

- [ ] ¿Pasan los tests y lint? (`npm run validate`)
- [ ] ¿El código sigue los patrones de la skill `react` y `typescript`?
- [ ] ¿Se ha actualizado el `CHANGELOG.md` siguiendo la skill `changelog`?
- [ ] ¿Se han eliminado logs o comentarios de depuración?

## 🤖 Regla para el Agente

Antes de dar por terminada una tarea, el agente debe proponer un resumen del PR
siguiendo este formato exacto para que el usuario pueda revisarlo y subirlo.
