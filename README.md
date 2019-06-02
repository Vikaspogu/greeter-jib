=  SpringBoot Greeter

The Greeter application is based on SpringBoot, build using Jib plugin

What is Jib?: Build Java applications without a Docker daemon

### How to build

```bash
mvn compile jib:build
oc new-app docker.io/vikaspogu/greeter:latest
oc create service clusterip greeter --tcp=8080:8080
oc expose svc greeter
```