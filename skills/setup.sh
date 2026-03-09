#!/bin/bash

# Colores para la salida
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}Configurando Agent Skills para Flexible Scheduling...${NC}"

# Crear directorios para diferentes herramientas
mkdir -p .claude/skills
mkdir -p .github/skills
mkdir -p .gemini/skills

# Función para crear symlinks de todas las skills
link_skills() {
    local target_dir=$1
    for skill in skills/*/; do
        if [ -d "$skill" ]; then
            skill_name=$(basename "$skill")
            # Evitar linkear carpetas internas como assets o scripts si existieran
            if [ "$skill_name" != "scripts" ] && [ "$skill_name" != "assets" ]; then
                # Usar rutas relativas correctas
                ln -sfn "../../$skill" "$target_dir/$skill_name"
            fi
        fi
    done
}

echo "Creando enlaces simbólicos..."
link_skills ".claude/skills"
link_skills ".github/skills"
link_skills ".gemini/skills"

echo -e "${GREEN}✅ Configuración completada.${NC}"
echo "Los agentes (Claude, Copilot, Gemini) ahora pueden descubrir las skills automáticamente."
