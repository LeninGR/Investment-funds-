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

1. **[commit](commit/SKILL.md)**: Reglas para commits que alimentan el sistema
   de changelog automático.
2. **[changelog](changelog/SKILL.md)**: Uso del sistema MCP para mantener el
   historial del proyecto.
3. **[react](react/SKILL.md)**: Guía de desarrollo para Expo y React Native en
   este entorno.
4. **[typescript](typescript/SKILL.md)**: Reglas de tipado estricto y patrones
   de TypeScript.
5. **[pr](pr/SKILL.md)**: Convenciones para Pull Requests.
6. **[skill-creator](skill-creator/SKILL.md)**: Meta-skill para crear nuevas
   habilidades.

## 🚀 Cómo usar una Skill

Los agentes pueden cargar estas instrucciones leyendo el archivo `SKILL.md`
correspondiente antes de realizar una tarea. Por ejemplo: "Antes de hacer el
commit, lee `skills/commit/SKILL.md`".
