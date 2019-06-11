#  SpringBoot Greeter

#### Prerequisites
* amq-broker
* monogodb.

#### How this application works?
Frontend sends order as a message, once message is received a new record is created in database

#### How to build with Jib?
This application image be build using JIB. What is Jib?: Building Java applications without a Docker daemon

```xml
<plugin>
    <groupId>com.google.cloud.tools</groupId>
    <artifactId>jib-maven-plugin</artifactId>
    <version>1.2.0</version>
    <configuration>
        <from>
            <image>openjdk:alpine</image>
        </from>
        <to>
            <image>docker.io/vikaspogu/greeter</image>
            <auth>
                <username>${env.REGISTRY_USERNAME}</username>
                <password>${env.REGISTRY_PASSWORD}</password>
            </auth>
        </to>
    </configuration>
</plugin>
```

This command will build and push into docker registry

```bash
mvn compile jib:build
```

### How to run this application in OpenShift?

```bash
$ oc new-app openshift/amq-broker-73-openshift --name=broker-amq -e AMQ_USER=amquser -e AMQ_PASSWORD=amqpass \
-e AMQ_QUEUES=mainQueue -e AMQ_NAME=greeter-broker
$ oc new-app rhscl/mongodb-36-rhel7 --name=greeterdb-mongo -e MONGODB_USER=mongouser -e MONGODB_PASSWORD=mongopass \
-e MONGODB_DATABASE=greeterdb
$ oc new-app openshift/openjdk18-openshift~https://github.com/Vikaspogu/greeter-jib --name=greeter \
-e ACTIVEMQ_BROKER_URL=tcp://greeter-broker-amq-tcp:61616 -e MONGODB_URL=mongodb://mongouser:mongopass@greeterdb-mongo:27017/greeterdb
```