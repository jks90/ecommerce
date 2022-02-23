FROM openjdk
WORKDIR /
ADD target/ecommerce-0.0.1.jar ecommerce.jar
EXPOSE 8080
CMD ["java", "-jar", "ecommerce.jar"]