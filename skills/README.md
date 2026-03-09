# AI Agent Skills

Este directorio contiene habilidades (skills) específicas para agentes de IA,
siguiendo un formato estándar para extender sus capacidades con conocimiento
especializado del proyecto.

## 📂 Estructura

```text
skills/
├── {skill-name}/
│   ├── SKILL.md              # Instrucciones principales y metadatos
│   ├── scripts/              # (Opcional) Scripts ejecutables
│   └── assets/               # (Opcional) Plantillas y recursos
└── README.md                 # Este archivo
```

## 🛠️ Skills Disponibles

1. **[commit](commit/SKILL.md)**: Reglas para commits que alimentan el sistema de changelog automático.
2. **[pr](pr/SKILL.md)**: Convenciones para Pull Requests.
3. **[skill-creator](skill-creator/SKILL.md)**: Meta-skill para crear nuevas habilidades.
4. **[tdd](tdd/SKILL.md)**: Guía para desarrollo guiado por pruebas.
5. **[skill-sync](skill-sync/SKILL.md)**: Sincronización de skills entre repositorios.

## 🚀 Cómo usar una Skill

Los agentes pueden cargar estas instrucciones leyendo el archivo `SKILL.md`
correspondiente antes de realizar una tarea. Por ejemplo: "Antes de hacer el
commit, lee `skills/commit/SKILL.md`".
