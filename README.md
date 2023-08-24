# springbootapp

# To compile:
mvn clean compile

# To verify and sonar:
mvn clean compile verify sonar:sonar

# To package:
mvn clean package

# To deploy
mvn wildfly:deploy

# Build docker image:
docker build -t springbootapp .

# Run docker image:
docker run -p 8080:8080 springbootapp