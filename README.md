# RegistroDeJornada
API para gestionar departamentos, empleados y fichajes (Registro de jornada).  

Clonar Repositorio: https://github.com/carmencitafdcp/RegistroDeJornada_AD_PSP.git

## Esta API gestiona los registros de jornada de los empleados en los departamentos, para ello se han implementado las siguientes entidades:
- Departamento (Entidad Principal): id (Long, PK), nombre (String, único), presupuesto (BigDecimal).
- Empleado (Entidad Secundaria): id (Long, PK), nombreCompleto (String), cargo (String), salario (BigDecimal).
- Fichaje (Entidad de Asociación/Registro): id (Long, PK), momento (LocalDateTime): Registro de la hora de entrada o salida, tipo (Enum: ENTRADA, SALIDA).

### Con las siguientes relaciones:
Departamento → Empleado (OneToMany/ManyToOne)
Empleado → Fichaje (OneToMany/ManyToOne)
-------- 
## Qué hace la API
API REST para:
- CRUD de Departamentos
- CRUD de Empleados
- Registrar fichajes (ENTRADA / SALIDA alternando)
- Reglas de negocio: fichaje duplicado, control de presupuesto al cambiar salario
--------
## Requisitos para esta API
- JDK 21 (recomendado para Spring Boot 4) o la versión que uses en el proyecto
- Maven
- Postman para importar la colección
--------
La aplicación arranca por defecto en: http://localhost:8080
--------
## Swagger / OpenAPI
- Swagger UI (interfaz): http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON (spec): http://localhost:8080/v3/api-docs
--------

