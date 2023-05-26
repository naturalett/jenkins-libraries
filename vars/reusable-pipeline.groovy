#!/usr/bin/env groovy
@Library('top10devops') _
import groovy.transform.Field

@Field String svcName = "reusable-pipeline"
def constants = new org.foo.functions.constants()
def docker_library = new org.foo.functions.dockerLib()
def checkout_library = new org.foo.functions.checkout()

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
                    image = docker_library.buildImage(
                        imageName: "${constants.ORGANIZATION}/${svcName}",
                        version: "${constants.CURRENT_DATE}-${env.BUILD_ID}-${checkout_library.shortCommit()}"
                    )
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    docker_library.deployImage(
                        imageName: "${constants.ORGANIZATION}/${svcName}",
                        version: "${constants.CURRENT_DATE}-${env.BUILD_ID}-${checkout_library.shortCommit()}",
                        port_destination: "81",
                        svcName: svcName
                    )
                }
            }
        }
    }
}