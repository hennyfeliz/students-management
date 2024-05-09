
# Usa una imagen base de Node.js
FROM node:19-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia el package.json y el package-lock.json al contenedor
COPY package.json ./
COPY package-lock.json ./

# Instala las dependencias
RUN npm install

# Copia los archivos del proyecto al contenedor...
COPY . .

# Compila el proyecto de React
RUN npm run build

# Expone el puerto 5173 para acceder a la aplicación
EXPOSE 5173

# Comando para iniciar la aplicación de React
CMD ["npm", "start"]
