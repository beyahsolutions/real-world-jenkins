FROM openjdk:8-jre-alpine

WORKDIR /app
ENV SWARM_VERSION 2.2
ENV JENKINS_URL http://master:8080/

COPY docker-entrypoint.sh /docker-entrypoint.sh
COPY credentials.groovy /app/
# ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /usr/local/bin/wait-for-it.sh

VOLUME /config
VOLUME /workspace

RUN chmod +x /docker-entrypoint.sh && \
    apk add --update curl tini docker bash && \
    curl -Lso swarm-client.jar https://repo.jenkins-ci.org/releases/org/jenkins-ci/plugins/swarm-client/${SWARM_VERSION}/swarm-client-${SWARM_VERSION}-jar-with-dependencies.jar

ENV WAITFORIT_VERSION v1.3.1
RUN curl -o /usr/local/bin/waitforit -sSL https://github.com/maxcnunes/waitforit/releases/download/$WAITFORIT_VERSION/waitforit-linux_amd64 && \
    chmod +x /usr/local/bin/waitforit

ENTRYPOINT ["/sbin/tini", "--", "/docker-entrypoint.sh"]
