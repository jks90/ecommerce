# ecommerce


# Acces to swagger
- http://localhost:8080/swagger-ui/index.html

# Acces to h2-console
- http://localhost:8080/h2-console/


# Time develop

- Development and test manually -> 3 hours
- JUnit test -> 1 hour 30 minuts



# Docker

docker build -t juankanh/ecommerce:0.1 .
docker push juankanh/ecommerce:0.1

docker run --name ecommerce -p 8080:8080 -ti juankanh/ecommerce:0.1