#!/bin/bash

# Script para sincronizar los metadatos de las skills con AGENTS.md

set -e

# Colores
BLUE='\033[0;34m'
GREEN='\033[0;32m'
NC='\033[0m'

AGENTS_FILE="AGENTS.md"
SKILLS_DIR="skills"

echo -e "${BLUE}Iniciando sincronización de skills con $AGENTS_FILE...${NC}"

# --- Generar Tabla de Skills ---
SKILLS_TABLE="| Skill | Descripción |\n|-------|-------------|"

for skill_dir in "$SKILLS_DIR"/*/; do
    skill_file="${skill_dir}SKILL.md"
    if [ -f "$skill_file" ]; then
        name=$(grep '^name:' "$skill_file" | cut -d' ' -f2-)
        # Extraer descripción (manejar formato multilínea simple)
        description=$(grep -A1 '^description:' "$skill_file" | tail -n1 | sed 's/^  //' | sed 's/>//' | xargs)
        SKILLS_TABLE="$SKILLS_TABLE\n| [$name](file://$(pwd)/$skill_file) | $description |"
    fi
done

# --- Generar Tabla de Auto-Invoke ---
AUTO_INVOKE_TABLE="| Acción | Invocar Primero | Motivo |\n|--------|------------------|--------|"

for skill_dir in "$SKILLS_DIR"/*/; do
    skill_file="${skill_dir}SKILL.md"
    if [ -f "$skill_file" ]; then
        name=$(grep '^name:' "$skill_file" | cut -d' ' -f2-)
        description=$(grep -A1 '^description:' "$skill_file" | tail -n1 | sed 's/^  //' | sed 's/>//' | xargs)
        
        # Extraer la lista de auto_invoke usando awk para manejar el bloque YAML
        auto_invokes=$(awk '/^  auto_invoke:/,/^[^ ]/ {if (!/^  auto_invoke:/ && /^    - /) print}' "$skill_file" | sed 's/^    - //' | sed 's/"//g' | sed "s/'//g")

        while IFS= read -r invoke_action; do
            if [ -n "$invoke_action" ]; then
                AUTO_INVOKE_TABLE="$AUTO_INVOKE_TABLE\n| $invoke_action | \`$name\` | $description |"
            fi
        done <<< "$auto_invokes"
    fi
done

# --- Actualizar AGENTS.md usando un archivo temporal y marcadores ---

TEMP_FILE="${AGENTS_FILE}.tmp"

# Limpiar el archivo temporal
> "$TEMP_FILE"

# Leer AGENTS.md y reemplazar las secciones
# Usamos una variable de estado para saber si estamos dentro de una sección a reemplazar
IN_SKILLS_TABLE=false
IN_AUTO_INVOKE=false

while IFS= read -r line || [ -n "$line" ]; do
    if [[ "$line" == *"<!-- SKILLS_TABLE_START -->"* ]]; then
        echo "$line" >> "$TEMP_FILE"
        echo -e "$SKILLS_TABLE" >> "$TEMP_FILE"
        IN_SKILLS_TABLE=true
    elif [[ "$line" == *"<!-- SKILLS_TABLE_END -->"* ]]; then
        echo "$line" >> "$TEMP_FILE"
        IN_SKILLS_TABLE=false
    elif [[ "$line" == *"<!-- AUTO_INVOKE_TABLE_START -->"* ]]; then
        echo "$line" >> "$TEMP_FILE"
        echo -e "$AUTO_INVOKE_TABLE" >> "$TEMP_FILE"
        IN_AUTO_INVOKE=true
    elif [[ "$line" == *"<!-- AUTO_INVOKE_TABLE_END -->"* ]]; then
        echo "$line" >> "$TEMP_FILE"
        IN_AUTO_INVOKE=false
    else
        if [ "$IN_SKILLS_TABLE" = false ] && [ "$IN_AUTO_INVOKE" = false ]; then
            echo "$line" >> "$TEMP_FILE"
        fi
    fi
done < "$AGENTS_FILE"

# Mover el temporal al original
mv "$TEMP_FILE" "$AGENTS_FILE"

echo -e "${GREEN}✅ Sincronización completada.${NC}"
