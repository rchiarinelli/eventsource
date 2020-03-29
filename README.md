# eventsource

### Guides
If needed, generate a new image version and push it to Dockerhub:

* [mvn clean install]
* [docker build -t rchiarinelli/eventsource:VERSION .]
* [docker push rchiarinelli/eventsource:VERSION]

Config K8S in the following order:

* [kubectl apply -f client-config.yaml]
* [docker run -d --name my-axon-server -p 8024:8024 -p 8124:8124 axoniq/axonserver]
* [kubectl apply -f eventsource-services.yaml]
