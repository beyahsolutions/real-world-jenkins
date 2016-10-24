FROM jenkinsci/jenkins:2.24

ENV JENKINS_VERSION 2.24

COPY config/ /usr/share/jenkins/ref/init.groovy.d/
COPY plugins.txt /usr/share/jenkins/plugins.txt
COPY root_seed.xml /usr/share/jenkins/ref/jobs/root_seed/config.xml.override

USER root
RUN chown -R jenkins /usr/share/jenkins/ref
USER jenkins

RUN echo ${JENKINS_VERSION} > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state && \
    echo ${JENKINS_VERSION} > /usr/share/jenkins/ref/jenkins.install.InstallUtil.lastExecVersion

RUN /usr/local/bin/install-plugins.sh $(cat /usr/share/jenkins/plugins.txt | tr '\n' ' ')
