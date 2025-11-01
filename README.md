# DOSW_TALLER_3
# API de Gestión de Recetas - Master Chef Celebrity

## Descripción del Proyecto

Este proyecto es una API REST para gestionar recetas de cocina del programa de televisión "Master Chef Celebrity". La API permite registrar, consultar, actualizar y eliminar recetas creadas por televidentes, participantes del programa o chefs jurado.

La aplicación está desarrollada con Spring Boot y utiliza MongoDB como base de datos para la persistencia de datos. La API está diseñada para ser consumida por el sitio web del programa, permitiendo a los televidentes consultar las recetas que han aparecido a lo largo de las temporadas y también publicar sus propias recetas.

## Características Principales

- Registro de recetas por televidentes, participantes y chefs jurado.
- Consulta de recetas por diferentes criterios (temporada, ingrediente, tipo de chef).
- Actualización y eliminación de recetas.
- Asignación automática de números consecutivos para las recetas.
- Manejo de excepciones con respuestas estandarizadas.
- Documentación con Swagger/OpenAPI 3.
- Pipeline de CI/CD con GitHub Actions para pruebas y despliegue automático en Azure.

## Instalación y Ejecución Local

### Prerrequisitos

- Java 17 o superior
- Maven 3.6 o superior
- MongoDB 4.4 o superior (o una conexión a MongoDB Atlas)
- Docker (opcional, para ejecutar en un contenedor)

### Pasos para la instalación

1.  Clonar el repositorio:
    ```bash
    git clone https://github.com/AnaFiquitiva/DOSW_TALLER_3.git
    cd DOSW_TALLER_3
    ```

2.  Configurar la conexión a MongoDB en el archivo `src/main/resources/application.properties`:
    ```properties
    spring.data.mongodb.uri=mongodb+srv://root:root@cluster0.yj7tdle.mongodb.net/masterchef_recipes?appName=Cluster0
    ```

3.  Ejecutar la aplicación:
    ```bash
    mvn spring-boot:run
    ```
    La aplicación estará disponible en `http://localhost:8080`.

### Ejecución con Docker

1.  Construir la imagen de Docker:
    ```bash
    docker build -t dosw .
    ```

2.  Ejecutar el contenedor:
    ```bash
    docker run --name dosw-run -p 8080:8080 dosw
    ```

## Documentación de la API

La API cuenta con documentación interactiva con Swagger/OpenAPI 3.

- **Local:** `http://localhost:8080/swagger-ui.html`
- **Azure:** [https://masterchef-api-az-2025.azurewebsites.net/swagger-ui.html](https://masterchef-api-az-2024.azurewebsites.net/swagger-ui.html) <!-- ¡REEMPLAZA ESTE ENLACE! -->

## Ejemplos de Endpoints

A continuación se muestran ejemplos de solicitud y respuesta para los endpoints principales.

### 1. Registrar una receta de un televidente

**Endpoint:** `POST /api/v1/recipes/viewer`

**Request:**
```json
{
  "title": "Arroz con leche casero",
  "ingredients": [
    "Arroz",
    "Leche",
    "Azúcar",
    "Canela en rama",
    "Cáscara de limón"
  ],
  "preparationSteps": [
    "Lavar el arroz y ponerlo a cocer con agua y la cáscara de limón",
    "Cuando el arroz esté casi hecho, añadir la leche caliente",
    "Añadir el azúcar y la canela en rama",
    "Cocinar a fuego lento hasta que espese",
    "Dejar enfriar antes de servir"
  ],
  "chefName": "María García"
}
```
**Response:**
```json
{
  "id": "64a8b1c2d3e4f5g6h7i8j9k0",
  "consecutiveNumber": 1,
  "title": "Arroz con leche casero",
  "ingredients": [
    "Arroz",
    "Leche",
    "Azúcar",
    "Canela en rama",
    "Cáscara de limón"
  ],
  "preparationSteps": [
    "Lavar el arroz y ponerlo a cocer con agua y la cáscara de limón",
    "Cuando el arroz esté casi hecho, añadir la leche caliente",
    "Añadir el azúcar y la canela en rama",
    "Cocinar a fuego lento hasta que espese",
    "Dejar enfriar antes de servir"
  ],
  "chefName": "María García",
  "chefType": "VIEWER",
  "season": null,
  "createdAt": "2023-07-07T10:30:00",
  "updatedAt": "2023-07-07T10:30:00"
}
```
### 2. Registrar una receta de un participante
**Endpoint:** `POST /api/v1/recipes/contestant`
**Request:**
```json
{
  "title": "Tarta de chocolate con frambuesas",
  "ingredients": [
    "Chocolate negro 70%",
    "Mantequilla",
    "Huevos",
    "Azúcar",
    "Harina",
    "Frambuesas frescas"
  ],
  "preparationSteps": [
    "Derretir el chocolate con la mantequilla al baño María",
    "Batir los huevos con el azúcar hasta obtener una mezcla espumosa",
    "Añadir el chocolate derretido y mezclar bien",
    "Incorporar la harina tamizada",
    "Verter en un molde y hornear a 180°C durante 25 minutos",
    "Decorar con frambuesas frescas antes de servir"
  ],
  "chefName": "Carlos Rodríguez",
  "season": 3
}
```
**Response:**
```json
{
  "id": "64a8b1c2d3e4f5g6h7i8j9k1",
  "consecutiveNumber": 2,
  "title": "Tarta de chocolate con frambuesas",
  "ingredients": [
    "Chocolate negro 70%",
    "Mantequilla",
    "Huevos",
    "Azúcar",
    "Harina",
    "Frambuesas frescas"
  ],
  "preparationSteps": [
    "Derretir el chocolate con la mantequilla al baño María",
    "Batir los huevos con el azúcar hasta obtener una mezcla espumosa",
    "Añadir el chocolate derretido y mezclar bien",
    "Incorporar la harina tamizada",
    "Verter en un molde y hornear a 180°C durante 25 minutos",
    "Decorar con frambuesas frescas antes de servir"
  ],
  "chefName": "Carlos Rodríguez",
  "chefType": "CONTESTANT",
  "season": 3,
  "createdAt": "2023-07-07T11:15:00",
  "updatedAt": "2023-07-07T11:15:00"
}
```
### 3. Registrar una recetad de un chef
**Endpoint** `POST /api/v1/recipes/judge-chef`
**Request:**
```json
{
  "title": "Risotto de setas con trufa blanca",
  "ingredients": [
    "Arroz arborio",
    "Setas variadas",
    "Caldo de pollo",
    "Vino blanco",
    "Cebolla",
    "Ajo",
    "Mantequilla",
    "Queso parmesano",
    "Trufa blanca"
  ],
  "preparationSteps": [
    "Saltear la cebolla y el ajo picados en mantequilla",
    "Añadir las setas laminadas y cocinar hasta que estén doradas",
    "Incorporar el arroz y tostarlo brevemente",
    "Añadir el vino blanco y dejar que se evapore",
    "Añadir el caldo caliente poco a poco, removiendo constantemente",
    "Cuando el arroz esté al dente, añadir mantequilla y queso parmesano",
    "Servir con láminas de trufa blanca por encima"
  ],
  "chefName": "Chef Santiago"
}
```
**Response:**
```json
{
  "id": "64a8b1c2d3e4f5g6h7i8j9k2",
  "consecutiveNumber": 3,
  "title": "Risotto de setas con trufa blanca",
  "ingredients": [
    "Arroz arborio",
    "Setas variadas",
    "Caldo de pollo",
    "Vino blanco",
    "Cebolla",
    "Ajo",
    "Mantequilla",
    "Queso parmesano",
    "Trufa blanca"
  ],
  "preparationSteps": [
    "Saltear la cebolla y el ajo picados en mantequilla",
    "Añadir las setas laminadas y cocinar hasta que estén doradas",
    "Incorporar el arroz y tostarlo brevemente",
    "Añadir el vino blanco y dejar que se evapore",
    "Añadir el caldo caliente poco a poco, removiendo constantemente",
    "Cuando el arroz esté al dente, añadir mantequilla y queso parmesano",
    "Servir con láminas de trufa blanca por encima"
  ],
  "chefName": "Chef Santiago",
  "chefType": "JUDGE_CHEF",
  "season": null,
  "createdAt": "2023-07-07T12:00:00",
  "updatedAt": "2023-07-07T12:00:00"
}
```
### 4. Consultar todas las recetas guardadas
**Endpoint** `GET /api/v1/recipes`
**Response:**
```json
[
  {
    "id": "64a8b1c2d3e4f5g6h7i8j9k0",
    "consecutiveNumber": 1,
    "title": "Arroz con leche casero",
    "ingredients": ["Arroz", "Leche", "Azúcar"],
    "preparationSteps": ["Paso 1", "Paso 2"],
    "chefName": "María García",
    "chefType": "VIEWER",
    "season": null,
    "createdAt": "2023-07-07T10:30:00",
    "updatedAt": "2023-07-07T10:30:00"
  },
  {
    "id": "64a8b1c2d3e4f5g6h7i8j9k1",
    "consecutiveNumber": 2,
    "title": "Tarta de chocolate con frambuesas",
    "ingredients": ["Chocolate", "Mantequilla"],
    "preparationSteps": ["Paso 1", "Paso 2"],
    "chefName": "Carlos Rodríguez",
    "chefType": "CONTESTANT",
    "season": 3,
    "createdAt": "2023-07-07T11:15:00",
    "updatedAt": "2023-07-07T11:15:00"
  }
]
```

### 5. Consultar una receta por su número consecutivo
**Endpoint** `GET /api/v1/recipes/{consecutiveNumber}`
**Response:**
```json
{
  "id": "64a8b1c2d3e4f5g6h7i8j9k1",
  "consecutiveNumber": 2,
  "title": "Tarta de chocolate con frambuesas",
  "ingredients": [
    "Chocolate negro 70%",
    "Mantequilla",
    "Huevos",
    "Azúcar",
    "Harina",
    "Frambuesas frescas"
  ],
  "preparationSteps": [
    "Derretir el chocolate con la mantequilla al baño María",
    "Batir los huevos con el azúcar hasta obtener una mezcla espumosa",
    "Añadir el chocolate derretido y mezclar bien",
    "Incorporar la harina tamizada",
    "Verter en un molde y hornear a 180°C durante 25 minutos",
    "Decorar con frambuesas frescas antes de servir"
  ],
  "chefName": "Carlos Rodríguez",
  "chefType": "CONTESTANT",
  "season": 3,
  "createdAt": "2023-07-07T11:15:00",
  "updatedAt": "2023-07-07T11:15:00"
}
```
### 6. Devolver las recetas de participantes
**Endpoint** `GET /api/v1/recipes/contestant`
**Response:**
```json
[
  {
    "id": "64a8b1c2d3e4f5g6h7i8j9k1",
    "consecutiveNumber": 2,
    "title": "Tarta de chocolate con frambuesas",
    "ingredients": ["Chocolate", "Mantequilla"],
    "preparationSteps": ["Paso 1", "Paso 2"],
    "chefName": "Carlos Rodríguez",
    "chefType": "CONTESTANT",
    "season": 3,
    "createdAt": "2023-07-07T11:15:00",
    "updatedAt": "2023-07-07T11:15:00"
  }
]
```
### 7. Devolver las recetas de televidentes
**Endpoint** `GET /api/v1/recipes/viewer`
**Response:**
```json
[
  {
    "id": "64a8b1c2d3e4f5g6h7i8j9k0",
    "consecutiveNumber": 1,
    "title": "Arroz con leche casero",
    "ingredients": ["Arroz", "Leche", "Azúcar"],
    "preparationSteps": ["Paso 1", "Paso 2"],
    "chefName": "María García",
    "chefType": "VIEWER",
    "season": null,
    "createdAt": "2023-07-07T10:30:00",
    "updatedAt": "2023-07-07T10:30:00"
  }
]
```
### 8. Devolver las recetas de chefs
**Endpoint** `GET /api/v1/recipes/judge-chef`
**Response:**
```json
[
  {
    "id": "64a8b1c2d3e4f5g6h7i8j9k2",
    "consecutiveNumber": 3,
    "title": "Risotto de setas con trufa blanca",
    "ingredients": ["Arroz", "Setas"],
    "preparationSteps": ["Paso 1", "Paso 2"],
    "chefName": "Chef Santiago",
    "chefType": "JUDGE_CHEF",
    "season": null,
    "createdAt": "2023-07-07T12:00:00",
    "updatedAt": "2023-07-07T12:00:00"
  }
]
```
### 9. Devolver las recetas de chefs
**Endpoint** `GET /api/v1/recipes/season/{season}`
**Response:**
```json
[
  {
    "id": "64a8b1c2d3e4f5g6h7i8j9k1",
    "consecutiveNumber": 2,
    "title": "Tarta de chocolate con frambuesas",
    "ingredients": ["Chocolate", "Mantequilla"],
    "preparationSteps": ["Paso 1", "Paso 2"],
    "chefName": "Carlos Rodríguez",
    "chefType": "CONTESTANT",
    "season": 3,
    "createdAt": "2023-07-07T11:15:00",
    "updatedAt": "2023-07-07T11:15:00"
  }
]
```
### 10. Buscar recetas que incluyan un ingrediente específico
**Endpoint** `GET /api/v1/recipes/search?ingredient={ingredient(Chocolate)} `
**Response:**
```json
[
  {
    "id": "64a8b1c2d3e4f5g6h7i8j9k1",
    "consecutiveNumber": 2,
    "title": "Tarta de chocolate con frambuesas",
    "ingredients": [
      "Chocolate negro 70%",
      "Mantequilla",
      "Huevos",
      "Azúcar",
      "Harina",
      "Frambuesas frescas"
    ],
    "preparationSteps": [
      "Derretir el chocolate con la mantequilla al baño María",
      "Batir los huevos con el azúcar hasta obtener una mezcla espumosa",
      "Añadir el chocolate derretido y mezclar bien",
      "Incorporar la harina tamizada",
      "Verter en un molde y hornear a 180°C durante 25 minutos",
      "Decorar con frambuesas frescas antes de servir"
    ],
    "chefName": "Carlos Rodríguez",
    "chefType": "CONTESTANT",
    "season": 3,
    "createdAt": "2023-07-07T11:15:00",
    "updatedAt": "2023-07-07T11:15:00"
  }
]
```
### 11. Eliminar una receta
**Endpoint** `DELETE /api/v1/recipes/{consecutiveNumber}`
**Response:**
```json
{
  "message": "Recipe deleted successfully",
  "timestamp": "2023-07-07T14:30:00"
}
```
### 12. Actualizar una receta
**Endpoint** `PUT /api/v1/recipes/{consecutiveNumber}`
**Request:**
```json
{
  "title": "Tarta de chocolate belga con frambuesas",
  "ingredients": [
    "Chocolate belga 75%",
    "Mantequilla sin sal",
    "Huevos orgánicos",
    "Azúcar moreno",
    "Harina de trigo",
    "Frambuesas frescas",
    "Esencia de vainilla"
  ],
  "preparationSteps": [
    "Derretir el chocolate belga con la mantequilla al baño María",
    "Batir los huevos con el azúcar moreno hasta obtener una mezcla espumosa",
    "Añadir el chocolate derretido y la esencia de vainilla, mezclar bien",
    "Incorporar la harina tamizada poco a poco",
    "Verter en un molde engrasado y hornear a 180°C durante 25-30 minutos",
    "Decorar con frambuesas frescas antes de servir"
  ],
  "chefName": "Carlos Rodríguez",
  "season": 3
}
```
**Response:**
```json
{
  "id": "64a8b1c2d3e4f5g6h7i8j9k1",
  "consecutiveNumber": 2,
  "title": "Tarta de chocolate belga con frambuesas",
  "ingredients": [
    "Chocolate belga 75%",
    "Mantequilla sin sal",
    "Huevos orgánicos",
    "Azúcar moreno",
    "Harina de trigo",
    "Frambuesas frescas",
    "Esencia de vainilla"
  ],
  "preparationSteps": [
    "Derretir el chocolate belga con la mantequilla al baño María",
    "Batir los huevos con el azúcar moreno hasta obtener una mezcla espumosa",
    "Añadir el chocolate derretido y la esencia de vainilla, mezclar bien",
    "Incorporar la harina tamizada poco a poco",
    "Verter en un molde engrasado y hornear a 180°C durante 25-30 minutos",
    "Decorar con frambuesas frescas antes de servir"
  ],
  "chefName": "Carlos Rodríguez",
  "chefType": "CONTESTANT",
  "season": 3,
  "createdAt": "2023-07-07T11:15:00",
  "updatedAt": "2023-07-07T14:45:00"
}
```
#  compilar y empaquetar
mvn clean package


Swagger UI: http://localhost:8080/swagger-ui.html

OpenAPI JSON: http://localhost:8080/api-docs

API Base: http://localhost:8080/api/v1/recipes