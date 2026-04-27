Datos de arranque del programa en docker.

1. Docker:
   docker compose up --build

2. Kubernetes:
   kubectl apply -f k8s/
   kubectl port-forward service/app-spring 8099:8099

3. Login:
   usuario: admin
   contraseña: admin123