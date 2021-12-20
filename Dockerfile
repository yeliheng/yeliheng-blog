FROM java:8
ENV JAR_PATH blog-web/target/
COPY application-prod.yml application-prod.yml
COPY ${JAR_PATH}*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=prod"]