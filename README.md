#  SpringBoot Greeter

The Greeter application is based on SpringBoot, build using Jib plugin

What is Jib?: Build Java applications without a Docker daemon

### How to build docker image without docker using Jib

```bash
mvn compile jib:build
```

Create a new application in OpenShift from docker image

```bash
oc new-app docker.io/vikaspogu/greeter:latest
oc create service clusterip greeter --tcp=8080:8080
oc expose svc greeter
```