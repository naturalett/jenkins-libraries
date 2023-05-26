## Setting up Jenkins Shared Library
Navigate to `Manage Jenkins -> Configure System` and configure the  **Global Pipeline Libraries**
* Name: **top10devops**
* Default version: **main**
* Modern SCM
* Source Code Management: **Git**
* Project repository: **https://github.com/naturalett/jenkins-libraries.git**

Create a pipeline job and set the script path: **vars/my-first-pipeline.groovy**
