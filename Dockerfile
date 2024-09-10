# Usa una imagen base con Java
FROM openjdk:17-jdk

# Directorio de trabajo en el contenedor
WORKDIR /2fauthenticator

# Copia el archivo JAR construido en el contenedor
COPY target/2fauthenticator.jar 2fauthenticator.jar

COPY src/main/resources/application.properties ./resources/application.properties

# Expone el puerto en el que la aplicación se ejecuta
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "2fauthenticator.jar"]
