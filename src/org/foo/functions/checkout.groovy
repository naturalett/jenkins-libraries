package org.foo.functions;

def shortCommit() {
    return sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
}
