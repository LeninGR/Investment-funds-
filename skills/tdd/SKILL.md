---
name: tdd
description: >
  Flujo de trabajo de Desarrollo Guiado por Pruebas (TDD) para el proyecto
  Flexible Scheduling. Trigger: AL implementar nuevas funcionalidades,  corregir
  errores o refactorizar lógica de negocio.
license: Apache-2.0
metadata:
  author: flexible-scheduling-team
  version: '1.0'
  scope: [root, components, services, hooks]
  auto_invoke:
    - 'Implementar nueva funcionalidad'
    - 'Corregir error de lógica'
    - 'Refactorizar servicios o hooks'
allowed-tools: Read, Edit, Write, Glob, Grep, RunCommand
---

# Skill: TDD Workflow

Ciclo MANDATORIO: **RED -> GREEN -> REFACTOR**

La pregunta NO es "¿debo escribir pruebas?", sino "¿qué pruebas necesito para
este comportamiento?"

## 📜 Las Tres Leyes de TDD

1. **No escribirás código de producción** hasta que tengas una prueba que falle.
2. **No escribirás más de una prueba de la necesaria para fallar** (los errores
   de compilación cuentan como fallos).
3. **No escribirás más código de producción del necesario para pasar** la prueba
   que falla.

## 🔄 El Ciclo en Flexible Scheduling

### Fase 0: Evaluación (SIEMPRE PRIMERO)

Antes de escribir CUALQUIER código:

1. **Identificar el componente/servicio**: ¿Es un `hook`, un `service` o un
   `component`?
2. **Buscar pruebas existentes**: `ls -R | grep test`
3. **Definir el caso de prueba**: ¿Cuál es el comportamiento mínimo esperado?

### Fase 1: RED - Escribir una prueba que falle

Escribe una prueba en un archivo `*.test.ts` o `*.test.tsx`.

- Debe fallar porque el código aún no existe o no maneja el caso.
- **Ejemplo (Service)**:

```typescript
it('should return error if client already exists', async () => {
  const result = await clientService.createClient(duplicateData);
  expect(result.success).toBe(false);
  expect(result.error).toBe('Ya existe un cliente con este email');
});
```

### Fase 2: GREEN - Código Mínimo

Escribe el MINIMO código necesario para que la prueba pase.

- **Fingirlo (Fake it)** es válido al principio (retornar un valor hardcodeado).
- El objetivo es llegar a verde lo antes posible.

### Fase 3: Triangulación

Añade más casos de prueba con diferentes entradas para forzar una implementación
real y robusta.

- Casos felices.
- Valores vacíos/nulos.
- Valores límite (boundaries).
- Condiciones de error.

### Fase 4: REFACTOR

Con las pruebas en verde, mejora la calidad del código sin cambiar su
comportamiento.

- Extraer funciones.
- Mejorar nombres.
- Eliminar duplicación.
- **Ejecutar pruebas tras cada cambio** -> Deben seguir en VERDE.

## 📋 Reglas Críticas

- **ALWAYS**: Escribir la prueba ANTES que el código.
- **ALWAYS**: Corregir errores reproduciéndolos primero con una prueba.
- **NEVER**: Probar detalles de implementación internos; probar comportamiento y
  contratos.
- **NEVER**: Escribir múltiples pruebas a la vez; ir de una en una.

## 🛠️ Comandos Útiles

- **Validar todo**: `npm run validate`
- **Type-check**: `npm run type-check`
- **Lint**: `npm run lint:check`
