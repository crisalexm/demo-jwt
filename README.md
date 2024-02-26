# Demo-jwt
Este proyecto es una demostración de una API RESTful que utiliza JWT (JSON Web Tokens) para la autenticación y protección de endpoints. Permite el registro y autenticación de usuarios, donde cada usuario autenticado recibe un token para acceder a recursos protegidos.  Esta versión incluye documentación de la API con Swagger, pruebas unitarias para garantizar la calidad del código y más.

## Características
- Autenticación y gestión de usuarios con JWT.
- Documentación completa de la API con Swagger.
- Pruebas unitarias integradas para validar la lógica de negocio.
- Configuración de seguridad con Spring Security.

## Documentación de la API con Swagger
Accede a la documentación completa de la API a través de Swagger en http://localhost:8080/swagger-ui.html, donde encontrarás todos los endpoints disponibles y podrás probarlos directamente desde tu navegador.

## Diagrama de solución
![Diagrama de solución](/images/diagrama-solucion.png)

## Requisitos
- Java 17
-  Maven
- IDE (IntelliJ IDEA, Eclipse, NetBeans, etc)

## Instalación y ejecución
1. Clonar el repositorio
```bash
git clone https://github.com/crisalexm/demo-jwt.git
```
2. Navegar a la carpeta del proyecto y ejecutar el comando
```bash
mvn spring-boot:run
```
La api estará disponible en http://localhost:8080/

## Uso
Utiliza Postman o curl para enviar solicitudes HTTP a los endpoints. Para endpoints protegidos, incluye el token JWT en el encabezado Authorization como Bearer {token}.

## Autenticación
Para obtener un token, envía una solicitud POST a **auth/register** con los datos mock requeridos o **/auth/login** con las credenciales del usuario. Usa este token para acceder a endpoints protegidos.

## Endpoints
| Método | Endpoint                | Descripción                          | Protegido |
|--------|-------------------------|--------------------------------------| ------ |
| POST   | /auth/register          | Registro de usuario                  | No |
| POST   | /auth/login             | Inicio de sesión                     | No |
| GET    | /api/v1/demo/users      | Obtener todos los usuarios (paginable)          | Sí |
| GET    | /api/v1/demo/users/{id} | Obtener usuario por id               | Sí |
| DELETE | /api/v1/demo/users/{id} | Eliminar usuario por id especificado | Sí |

## Ejemplos
### Registro de usuario
#### body
```bash
{
  "name": "Cristhian Martinez",
  "email": "cristhian3@gmail.com",
  "password": "Test@123",
  "phones": [
    {
      "number": "12345678",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}
```
#### response
```bash
{
    "id": "c7ed6295-64d8-4dc2-bcce-f087344c94c4",
    "name": "Cristhian Martinez",
    "email": "cristhian3@gmail.com",
    "password": "$2a$10$aP.8zjHzYZT1eu32KjAFTenjKE.jCaUDQiX0O30fACUhfoc7yzOU6",
    "phones": [
        {
            "number": "12345678",
            "citycode": "1",
            "contrycode": "57"
        }
    ],
    "created": "2023-12-07T07:32:10.551+00:00",
    "modified": "2023-12-07T07:32:10.551+00:00",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4zQGdtYWlsLmNvbSIsImlhdCI6MTcwMTkzNDMzMCwiZXhwIjoxNzAxOTM3OTMwfQ.L9yiDE931eTZi3F3I2h8ELE4o-Ta6-Nxcj2w4d4AUoE",
    "last_login": "2023-12-07T07:32:10.551+00:00",
    "isactive": true
}
```
### Inicio de sesión
#### body
```bash
{
    "email":"cristhian3@gmail.com",
    "password":"Test@123"
}
```
#### response
```bash
{
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4zQGdtYWlsLmNvbSIsImlhdCI6MTcwMTkzNDkwMSwiZXhwIjoxNzAxOTM4NTAxfQ.9IyakqagPGg9NOxkQhs716FgtzuSKYmNIrJgudELLlU"
}
```
### Obtener todos los usuarios
Pasando el token en el encabezado Authorization como Bearer {token}
#### response
```bash
{
    "totalItems": 1,
    "totalPages": 1,
    "currentPage": 0,
    "users": [
        {
            "id": "fca77ab8-4381-47f8-97cf-89a51eccbb47",
            "name": "Cristhian Martínez",
            "email": "cristhian@email.com",
            "password": "$2a$10$SM4rev0tC9imBBcDCYFr5Om9/K3qPaXmx3oeve90PLBl2R0FWe/Du",
            "phones": [
                {
                    "number": "12345678",
                    "citycode": "1",
                    "contrycode": "57"
                }
            ],
            "created": "2024-02-26T06:21:35.415+00:00",
            "modified": "2024-02-26T06:21:35.415+00:00",
            "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW5AZW1haWwuY29tIiwiaWF0IjoxNzA4OTI4NDk1LCJleHAiOjE3MDg5Mjg2MTV9.DbnTdRo49PPs9Dwae15gTQxwTqdO7fMtIIYoo44IxqE",
            "last_login": "2024-02-26T06:21:35.415+00:00",
            "isactive": true
        }
    ]
}
```
