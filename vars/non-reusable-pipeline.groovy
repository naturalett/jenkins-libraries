#!/usr/bin/env groovy
@Library('top10devops') _
import groovy.transform.Field

@Field String svcName = "non-reusable-pipeline"
@Field String ORGANIZATION = "naturalett"
@Field String CURRENT_DATE = new java.text.SimpleDateFormat("MM-dd-yyyy").format(new Date())

pipeline {
    agent {
        dockerfile {
            filename 'resources/org/foo/Dockerfile.Docker'
            args "-v /var/run/docker.sock:/var/run/docker.sock --name ${svcName}-agent"
        }
    }
    stages {
        stage('Clone') {
            steps {
                git branch: 'main', url: 'https://github.com/naturalett/hello-world.git'
            }
        }
        stage('Build') {
            steps {
                script {
                    shortCommit = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
                    image = docker.build("${ORGANIZATION}/${svcName}:${CURRENT_DATE}-${env.BUILD_ID}-${shortCommit}")
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    sh(
                        script:
                        """
                            docker run -it \
                            --name ${svcName} \
                            -d -p81:81 \
                            "${ORGANIZATION}/${svcName}:${CURRENT_DATE}-${env.BUILD_ID}-${shortCommit}"
                        """,
                    returnStdout: true)
                }
            }
        }
    }
}