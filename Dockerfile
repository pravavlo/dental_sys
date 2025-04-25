FROM amazoncorretto:23-alpine
LABEL authors="user"
#setting working directory in Docker
WORKDIR /app
# copy file from locally built jar file to the docker's workding directory
COPY build/libs/myApp-1.0.1.jar /app

ENTRYPOINT ["java","-jar","myApp-1.0.1.jar"]
