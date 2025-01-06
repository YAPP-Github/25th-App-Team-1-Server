FROM openjdk:21
EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
ARG ACTIVE_PROFILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Duser.timezone=Asa/Seoul","/app.jar"]