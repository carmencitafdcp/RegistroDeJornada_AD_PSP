# RegistroDeJornada
API para gestionar departamentos, empleados y fichajes (Registro de jornada).  

Clonar Repositorio: https://github.com/carmencitafdcp/RegistroDeJornada_AD_PSP.git

## Esta API hace:
Controlar departamentos, empleados y sus fichajes (entrada/salida), aplicando reglas de negocio (evitar fichajes duplicados, controlar presupuesto al cambiar salarios, etc.).

### Entidades principales
1. Departamento
  - id (Long, PK)
  - nombre (String)
  - presupuesto (BigDecimal)
  - Relación: OneToMany → Empleado

2. Empleado
  - id (Long, PK)
  - nombreCompleto (String)
  - cargo (String)
  - salario (BigDecimal)
  - departamento (ManyToOne → Departamento)
  - Relación: OneToMany → Fichaje

3. Fichaje
  - id (Long, PK)
  - momento (LocalDateTime)
  - tipo (Enum: ENTRADA, SALIDA)
  - empleado (ManyToOne → Empleado)

### Relaciones
- Departamento (1) ←→ (N) Empleado
- Empleado (1) ←→ (N) Fichaje

### Principales funcionalidades
- CRUD de Departamentos
- CRUD de Empleados
- Registrar fichajes (POST /api/v1/empleados/{empleadoId}/fichar): alterna ENTRADA/SALIDA
- Asignar empleado a departamento (PUT /api/v1/empleados/{empleadoId}/departamento/{deptoId})
- Reglas de negocio:
  - FichajeDuplicadoException → evita fichar dos veces seguidas el mismo tipo
  - PresupuestoExcedidoException → al aumentar salario si supera presupuesto del departamento

## Gestión de errores
El proyecto usa excepciones personalizadas mapeadas a ProblemDetail mediante un controlador global:
- EntidadNoEncontradaException → 404
- FichajeDuplicadoException → 409
- PresupuestoExcedidoException → 400
- Otras validaciones → 400 / 422 según el caso

## DTOs utilizados (resumen)
- DepartamentoRequestDTO / DepartamentoResponseDTO
- EmpleadoRequestDTO / EmpleadoResponseDTO
- FichajeResponseDTO

## Arquitectura del proyecto (carpeta src/main/java)
- com.salesianostriana.dam.registro_de_jornada
  - controller
  - dto
  - error
  - model
  - repository
  - service
  - config (opcional: OpenApiConfig, DataLoader)

## Servicios (resumen)
- DepartamentoService
  - findAll, getById, create, edit, delete
- EmpleadoService
  - findAll, getById, create, edit, delete
  - assignDepartment(empleadoId, deptoId)
  - fichar(empleadoId) -> crea Fichaje alternando ENTRADA/SALIDA
- FichajeService (si existe separado): lógica de negocio del fichaje

## Endpoints principales (base: /api/v1)
- Departamentos
  - GET  /api/v1/departamentos — listar
  - GET  /api/v1/departamentos/{id} — obtener por id
  - POST /api/v1/departamentos — crear
  - PUT  /api/v1/departamentos/{id} — editar
  - DELETE /api/v1/departamentos/{id} — eliminar

- Empleados
  - GET  /api/v1/empleados — listar empleados
  - GET  /api/v1/empleados/{id} — obtener por id
  - POST /api/v1/empleados — crear empleado
    Ejemplo body:
    {
      "nombreCompleto": "Ana Pérez",
      "cargo": "Backend",
      "salario": 30000,
      "departamentoId": 1
    }
  - PUT  /api/v1/empleados/{id} — editar empleado (body similar)
  - DELETE /api/v1/empleados/{id} — eliminar
  - PUT  /api/v1/empleados/{empleadoId}/departamento/{deptoId} — asignar departamento

- Fichajes
  - POST /api/v1/empleados/{empleadoId}/fichar — registrar fichaje (sin body)
  - GET  /api/v1/empleados/{empleadoId}/fichajes — historial de fichajes

### Swagger / OpenAPI
- Swagger UI (interfaz visual, probar endpoints):  
  http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON (spec):  
  http://localhost:8080/v3/api-docs
- Consejo: si usas Spring Security, asegúrate de permitir acceso a /v3/api-docs/** y /swagger-ui/**

