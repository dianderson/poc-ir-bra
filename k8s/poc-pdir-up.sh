kubectl apply -f postgres-pod.yml
sleep 2
kubectl apply -f postgres-service.yml
sleep 1

kubectl apply -f zookeeper-pod.yml
sleep 2
kubectl apply -f zookeeper-service.yml
sleep 1

kubectl apply -f kafka-pod.yml
sleep 2
kubectl apply -f kafka-service.yml
sleep 1
kubectl apply -f kafka-init-job.yml
sleep 1

kubectl apply -f schema-registry-pod.yml
sleep 2
kubectl apply -f schema-registry-service.yml
sleep 1

kubectl apply -f control-center-pod.yml
sleep 2
kubectl apply -f control-center-service.yml
sleep 1

kubectl apply -f processing-service.yml
sleep 2

kubectl apply -f integration-service.yml
sleep 2

kubectl apply -f origin-service.yml
sleep 2

kubectl apply -f pdf-generator-service.yml
sleep 2

kubectl delete job kafka-init-job

echo 'All dependencies up success'
