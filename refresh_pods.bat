@ECHO OFF 
kubectl delete replicationcontroller eventsource & kubectl delete service eventsource & kubectl apply -f .\eventsource-services.yaml
pause
