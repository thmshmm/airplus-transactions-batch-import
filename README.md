# AirPlus Transactions Batch Import

## Build application

```
$ ./gradlew build
```

## Run application

Create app.yml
```
logging:
  config: "file:./logback.xml"
app:
  import:
    file: "./test.csv"
    headerLines: 0
    datePattern: "yyyy-MM-dd"
    fieldSeparator: ";"
    thousandsSeparator: "."
    decimalSeparator: ","
```

Create logback.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{dd-MM-yyyy HH:mm:ss.SSS} [%thread] %-5level %logger{36}.%M - %msg%n</pattern>
        </encoder>
    </appender>
    <root level="error">
        <appender-ref ref="STDOUT" />
    </root>
    <logger name="de.thmshmm.airplus" level="debug">
        <appender-ref ref="STDOUT" />
    </logger>
</configuration>
```

Run the application
```
$ java -jar airplus-transactions-batch-import-1.0-SNAPSHOT.jar --spring.config.location=file:./app.yml
```

## Run tests
```
$ ./gradlew test
```
