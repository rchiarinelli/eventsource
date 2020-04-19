@ECHO OFF 
mvn clean install && docker build -t rchiarinelli/eventsource:0.4 . && docker push rchiarinelli/eventsource:0.4 && kubectl delete replicationcontroller eventsource & kubectl delete service eventsource & kubectl apply -f .\eventsource-services.yaml
pause
