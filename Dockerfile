FROM jboss/wildfly:latest

USER root
RUN mkdir -p /var/log/wildfly
RUN chown jboss /var/log/wildfly

USER jboss
ADD target/springbootapp.war /opt/jboss/wildfly/standalone/deployments/

ENV JAVA_OPTS "-Dspring.profiles.active=prod"
RUN echo $JAVA_OPTS

EXPOSE 8080