# Nombre del Proyecto : API Estudiantes

Esta API REST permite gestionar cursos en una plataforma educativa online, permitiendo operaciones como creación, actualización, eliminación y obtención de información de cursos.

## Requisitos

- Java 17 o superior.
- **Spring Boot 2.x**
- Maven 
- Base de datos MySQL
- **Postman** para probar los endpoints

## Instalación

Sigue estos pasos para instalar y ejecutar el proyecto:

## Instalación
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/usuario/proyecto.git
   ```
2. Navegar a la carpeta del proyecto:
   ```bash
   cd proyecto
   ```
3. Instalar las dependencias:
   ```bash
   mvn install
   ```
4. Ejecutar la aplicación:
   ```bash
   mvn spring-boot:run
   ```


## Acceso
La API estará disponible en `http://localhost:8080`.

## Autenticación y Seguridad

Esta API utiliza Spring Security, JWT (JSON Web Tokens) y OAuth2 para gestionar la seguridad y la autenticación. A continuación se detallan los aspectos clave:

-   **Spring Security:** Configura y gestiona la seguridad en la aplicación, protegiendo los endpoints y asegurando que solo los usuarios autenticados puedan acceder a ciertas rutas.

-   **JWT:** Se usa para la autenticación basada en tokens. Al iniciar sesión, el usuario recibe un token JWT que debe incluirse en el encabezado de las solicitudes para acceder a los recursos protegidos.

-   **OAuth2:** Implementa un flujo de autorización que permite la integración con proveedores externos de autenticación (como Google o GitHub) para permitir que los usuarios se autentiquen utilizando sus cuentas externas.

#### Flujo de Autenticación:

1.  El usuario se autentica enviando sus credenciales (usuario y contraseña) a un endpoint de autenticación.

2.  Si las credenciales son válidas, el servidor responde con un token JWT.

3.  Este token debe ser enviado en las solicitudes posteriores en el encabezado Authorization: Bearer {token}.

4.  Los endpoints protegidos requieren una validación del token JWT y pueden usar OAuth2 para permitir acceso mediante autenticación externa.


## Endpoints
### Crear un nuevo curso
- **Método**: `POST`
- **URL**: `/curso`
- **Descripción**: Crea un nuevo curso en el sistema.
- **Parámetros**:
    - `nombre`: Nombre del curso (requerido)
    - `modalidad`: Modalidad del curso (requerido)
    - `fecha_finalizacion`: Fecha de finalización del curso (requerido)
- **Ejemplo de solicitud**:
  ```json
  {
    "nombre": "Programación I",
    "modalidad": "Virtual",
    "fecha_finalizacion": "2025-12-01"
  }
  ```
- **Respuesta exitosa**:
  ```json
  {
    "id": 1,
    "nombre": "Programación I",
    "modalidad": "Virtual",
    "fecha_finalizacion": "2025-12-01"
  }
  ```
- **Código de estado**: `201 Created`


## Manejo de Errores
- **400 Bad Request**: Cuando los datos enviados en la solicitud no son válidos.
- **404 Not Found**: Cuando no se encuentra el recurso solicitado.
- **500 Internal Server Error**: Cuando ocurre un error inesperado en el servidor.

**Ejemplo de error**:
```json
{
  "error": "Curso no encontrado",
  "message": "No existe un curso con ID 123"
}
```

