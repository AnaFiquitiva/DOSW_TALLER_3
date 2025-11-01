# Usamos una imagen ligera con Java 17
FROM openjdk:17-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR generado por Spring Boot
ARG JAR_FILE=target/DOSW_TALLER_3-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Exponemos el puerto que utiliza la aplicación (por defecto 8080)
EXPOSE 8080

# Definimos el comando para arrancar la aplicación
ENTRYPOINT ["java","-jar","/app/app.jar"]
