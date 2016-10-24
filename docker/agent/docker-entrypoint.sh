#!/bin/bash
set -e
set -x

/usr/local/bin/waitforit -host=master -port=8080

# https://coderwall.com/p/taqiyg/use-http-status-codes-from-curl
while true
do
  STATUS=$(curl -s -o /dev/null -w '%{http_code}' $JENKINS_URL/login)
  if [ $STATUS -eq 200 ]; then
    echo "Got 200! All done!"
    break
  else
    echo "Got $STATUS :( Not done yet..."
  fi
  sleep 10
done

echo "Retrieving Jenkins CLI"
cd /app

curl -Lso jenkins-cli.jar $JENKINS_URL/jnlpJars/jenkins-cli.jar
curl -Lso slave.jar $JENKINS_URL/jnlpJars/slave.jar

echo "Retrieving Credentials"
set +x
credentials=$(java -jar jenkins-cli.jar -s $JENKINS_URL -i /config/private.pem groovy credentials.groovy)
user=$(echo $credentials | cut -f1 -d=)
export JENKINS_TOKEN=$(echo $credentials | cut -f2 -d=)
echo "Found User: $user"
set -x

# we will be working in this directory
cd /workspace
mkdir -p $HOSTNAME
cd $HOSTNAME

exec java -cp /app/slave.jar:/app/swarm.jar -jar /app/swarm-client.jar \
    -master "$JENKINS_URL" \
    -username $user \
    -passwordEnvVariable JENKINS_TOKEN \
    -name docker \
    -labels docker \
    -executors 5