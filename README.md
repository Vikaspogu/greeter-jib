=  SpringBoot Greeter

The Greeter application is based on SpringBoot, build using Jib plugin

What is Jib?: Build Java applications without a Docker daemon

### How to build
```bash
mvn compile jib:build -Dimage=docker.io/vikaspogu/greeter:0.0.1
```