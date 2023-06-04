## Setting up Jenkins Shared Library
Navigate to `Manage Jenkins -> Configure System` and configure the  **Global Pipeline Libraries**
* Name: **top10devops**
* Default version: **main**
* Modern SCM
* Source Code Management: **Git**
* Project repository: **https://github.com/naturalett/jenkins-libraries.git**

## Create a pipeline job
* Create a pipeline job and set the script path: **vars/my-first-pipeline.groovy**

## Launch Jenkins in a docker container
```bash
docker run -d \
        --name jenkins -p 8080:8080 -u root -p 50000:50000 \
        -v /var/run/docker.sock:/var/run/docker.sock \
        naturalett/jenkins:jenkins-libraries
```

## Get the initial password for Jenkins
```bash
docker exec jenkins bash -c -- 'cat /var/jenkins_home/secrets/initialAdminPassword'
```