| Date| Author | Description | Version |
| --- | --- | --- | --- |
| 20/06/2018 | Wagner Alves (aka Barão) | Dynatrace Lambda Monitor | 1.2.3-SNAPSHOT | 

# Dynatrace Lambda Monitor

## Description:
Library for monitoring services based on Dynatrace OpenKit.

## What is the Dynatrace Lambda Monitor?
The Dynatrace Lambda Monitor provides an easy and lightweight way to get insights into applications with Dynatrace/AppMon by instrumenting the source code of those applications.

It is best suited for applications running separated from their backend and communicating via HTTP, like rich-client-applications, embedded devices, terminals, and so on.

## The big advantages of the Dynatrace Lambda Monitor are that it's designed to

be as easy-to-use as possible
be as dependency-free as possible (no third party libraries or Dynatrace/AppMon Agent needed)
be easily portable to other languages and platforms
This repository contains the reference implementation in pure Java. Other implementations are listed as follows:

## What you can do with the Dynatrace Lambda Monitor

Create Sessions and User Actions
Report events, errors and crashes
Trace web requests to server-side PurePaths

## How to use:

1-Add the dependency to maven pom.xml

```
<!-- Dynatrace Lambda Monitor -->		
<dependency>
    <groupId>com.aos.dynatrace.lambda.monitor</groupId>
    <artifactId>dynatrace-lambda-monitor</artifactId>
    <version>1.2.3-SNAPSHOT</version>
</dependency>
```
2-Add key/values to a 'application.properties' file at 'src/main/resources' source folder.

```
dynatrace.enable=true
dynatrace.enable.verbose=true
dynatrace.application.name=<ApplicationName>
dynatrace.application.id=<ApplicationID>
dynatrace.endpoint.url=<ServerURL>
dynatrace.device.id=42
dynatrace.client.ip=8.8.8.8
dynatrace.user=wagner.aos.sa@gmail.com
dynatrace.application.version=1.0.0.0
dynatrace.operating.system=Linux
dynatrace.manufacturer=MyCompany
dynatrace.model.id=MyModelID
```

> OBS: You can set the following environment variables, this variables below overwrites the key/value at application.properties file!!!

```
DYNATRACE_ENABLE_MONITOR=true
DYNATRACE_ENABLE_VERBOSE=true
DYNATRACE_APPLICATION_NAME=<ApplicationName>
DYNATRACE_APPLICATION_ID=<ApplicationID>
DYNATRACE_ENDPOINT_URL=<ServerURL>
DYNATRACE_DEVICE_ID=42
DYNATRACE_CLIENT_IP=8.8.8.8
DYNATRACE_USER=wagner.aos.sa@gmail.com
DYNATRACE_APPLICATION_VERSION=1.0.0.0
DYNATRACE_OPERATION_SYSTEM=Linux
DYNATRACE_MANUFACTURER=MyCompany
DYNATRACE_MODEL_ID=MyModelID
```

# Examples:

## Starting a monitor:
```
private DynatraceMonitor monitor;

try {
    monitor = new DynatraceMonitorStarter().getMonitor();
}...
```

## Creating a RootAction:
```
try {
    monitor.createRootAction("HandlerRequestStart");
    logger.log("Dynatrace RootAction: Handler Request - STARTED");
}..

```

## Creating a ChildAction:
```
private Action childAction;

try {
    monitor.getRootAction();
    childAction = monitor.getRootAction().enterAction("SaveRecords");
    logger.log("ChildAction: Gravar registro no DynamoDB - STARTED")
}...

```

## Leaving a ChildAction:
```
try {

    ...

    childAction.leaveAction();
    logger.log("ChildAction: Gravar registro no DynamoDB - FINISHED");
}
```

## Leaving all Actions and shutdown the Session:
```
try {
    ...
}
catch{
    ...
}
finally{
    monitor.leaveAllActions(childAction);
    logger.log("Dynatrace RootAction: Handler Request - FINISHED");
}
```

## Reporting a Crash (any exception occurrency) :
```
try {
   ...

} catch (DynatraceMonitorException e) {
    DynatraceLogger.log(e);
    String stacktrace = ExceptionUtils.getStackTrace(e);
    monitor.reportCrash(e.getClass().getName(), e.getMessage(), stacktrace);
}

```

## AWS Lambda Example:

```
public Integer handleRequest(DynamodbEvent dbEvent, Context context) {

        try {
            // dynatrace
            monitor = new DynatraceMonitorStarter().getMonitor();
            monitor.createRootAction("HandlerRequestStart");
            logger.log("Dynatrace RootAction: Handler Request - STARTED");
            monitor.getRootAction();
            childAction = monitor.getRootAction().enterAction("SaveRecords");
            logger.log("ChildAction: Gravar registro no DynamoDB - STARTED");

            // Processing Something
            this.logger = Optional.ofNullable(context).map(Context::getLogger).orElse(System.err::println);
            dbEvent.getRecords().forEach(this::processar);

        } catch (DynatraceMonitorException e) {
            DynatraceLogger.log(e);
            
            //Reporting any exception (crashes)
            String stacktrace = ExceptionUtils.getStackTrace(e);
            monitor.reportCrash(e.getClass().getName(), e.getMessage(), stacktrace);
            
        } finally {
            monitor.leaveAllActions(childAction);
            logger.log("Dynatrace RootAction: Handler Request - FINISHED");
        }

        return dbEvent.getRecords().size();
    }

```

## How to Test this Library:

1-Go to Dynatrace Console and create a new application configuration.

2-Get The ApplicationName, ApplicationID and ServerURL and modify the file 'app.properties' at 'src/test/resources'.
```
dynatrace.application.name=<ApplicationName>
dynatrace.application.id=<ApplicationID>
dynatrace.endpoint.url=<ServerURL>
```

3-Go to pom.xml and modify maven-surefire plugin to skipTest=false
```
 <build>
    <plugins>
    
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.21.0</version>
       	<configuration>
          <skipTests>false</skipTests>
        </configuration>
      </plugin>

      ....

```

4-Now you can run 'mvn clean package' in order get tests executed, and sending results to Dynatrace Server!

## I hope you enjoy it!  ;-) and feel free to make some improvements!!!

