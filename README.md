# Proyecto Fullstack con React y Quarkus

Este repositorio contiene un proyecto fullstack que incluye un frontend en React y un backend en Quarkus. La base de datos utilizada es H2, por lo que no tendrás que preocuparte por la configuración de la base de datos.

## Estructura del Proyecto

```
├── back
│ ├── .idea
│ ├── .mvn
│ ├── src
│ │ ├── main
│ │ │ ├── docker
│ │ │ └── java
│ │ │ └── org
│ │ │ └── acme
│ │ │ ├── controllers
│ │ │ ├── entities
│ │ │ ├── Generics
│ │ │ ├── pagination
│ │ └── test
│ ├── target
│ ├── Dockerfile
│ ├── pom.xml
|
├── front
│ ├── node_modules
│ ├── public
│ ├── src
│ │ ├── assets
│ │ │ ├── fonts
│ │ │ └── icons
│ │ ├── components
│ │ ├── styles
│ │ └── utils
│ ├── .eslint.cjs
│ ├── Dockerfile
│ ├── package-lock.json
│ ├── package.json
│ └── vite.config.js
└── ...
```

## Requisitos

- Node.js (para el frontend)
- Maven (para el backend)
- Java JDK 11 o superior (para el backend)

## Configuración y Ejecución

### Frontend (React)

1. Navega al directorio `front`:

   ```bash
   cd front
   ```

2. Instala las dependencias del frontend:

   ```bash
   npm install
   ```

3. Ejecuta la aplicación [dev]:

   ```bash
   npm run dev
   ```

### Backend (Quarkus)

La aplicación React estará corriendo en `http://localhost:8080`.

Backend (Quarkus)
Navega al directorio back:

```bash
  cd back
```

Ejecuta la aplicación:

```bash
./mvnw quarkus:dev
```

El servidor Quarkus estará corriendo en `http://localhost:8080`.

Base de Datos
Este proyecto utiliza una base de datos en memoria H2, por lo que no necesitas realizar configuraciones adicionales para la base de datos. Los datos se restablecerán cada vez que reinicies la aplicación.

Endpoints API
El backend expone varios endpoints RESTful. Puedes acceder a ellos a través del navegador o herramientas como Postman. Algunos ejemplos de endpoints podrían ser:
