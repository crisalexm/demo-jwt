# Demo-jwt
Este proyecto es una demostración de una API RESTful que utiliza JWT (JSON Web Tokens) para la autenticación y protección de endpoints. Permite el registro y autenticación de usuarios, donde cada usuario autenticado recibe un token para acceder a recursos protegidos.

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
| Método | Endpoint | Descripción | Protegido |
| ------ | ------ | ------ | ------ |
| POST | /auth/register | Registro de usuario | No |
| POST | /auth/login | Inicio de sesión | No |
| GET | /api/v1/demo | Obtener todos los usuarios | Sí |

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
    "count": 2,
    "users": [
        {
            "id": "a5cf56ba-4301-4e25-aec7-673d0935818c",
            "name": "Cristhian Martinez",
            "email": "cristhian2@gmail.com",
            "password": "$2a$10$Sge4DmV9q6xzdcGwl7ohF.3l.zig2i3tkDnFajO53IGpcWOBeWVMK",
            "phones": [
                {
                    "number": "12345678",
                    "citycode": "1",
                    "contrycode": "57"
                }
            ],
            "created": "2023-12-07T07:32:00.931+00:00",
            "modified": "2023-12-07T07:32:00.931+00:00",
            "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmlzdGhpYW4yQGdtYWlsLmNvbSIsImlhdCI6MTcwMTkzNDMyMCwiZXhwIjoxNzAxOTM3OTIwfQ.Cw0zuceZdB9Biuyd4tiYhocH5YgS4gsi0dzsQqzDkwE",
            "last_login": "2023-12-07T07:32:00.931+00:00",
            "isactive": true
        },
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
    ]
}
```