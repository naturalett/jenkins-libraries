## Build Jenkins
Execute the following:
```bash
docker build -t naturalett/jenkins:jenkins-libraries .
```

Run the container:
```bash
docker run -d \
        --name jenkins -p 8080:8080 -u root -p 50000:50000 \
        -v /var/run/docker.sock:/var/run/docker.sock \
        naturalett/jenkins:jenkins-libraries
```