package org.foo.functions;
import groovy.transform.Field

@Field String containerName   = "docker"

def getConstants() {
    return new org.foo.functions.constants()
}

def buildImage(Map args=[:]) {
    if (!args.version) args.version = "latest"
    return docker.build("${args.imageName}:${args.version}")
}

def getImage(Map args=[:]) {
    if (!args.version) args.version = "latest"
    return docker.image("${args.imageName}:${args.version}")
}

def deployImage(Map args=[:]) {
    if (!args.version) args.version = "latest"
    sh(
        script:
        """
            docker run -it \
            --name ${args.svcName} \
            -d -p81:${args.port_destination} \
            ${args.imageName}:${args.version}
        """,
    returnStdout: true)
}