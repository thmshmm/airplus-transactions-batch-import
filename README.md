# AirPlus Transactions Batch Import

## Build application

```
$ ./gradlew build
```

## Run application

Create a PostgreSQL database + table
```
CREATE DATABASE payment;
CREATE TABLE creditcard_transactions  (
  id SERIAL NOT NULL PRIMARY KEY,
  card_no VARCHAR(20) NOT NULL,
  invoice_no VARCHAR(20),
  invoice_date DATE,
  invoice_item_no INTEGER,
  purchase_date DATE ,
  entry_date DATE,
  service_provider VARCHAR(50),
  service_description VARCHAR(50),
  currency VARCHAR(3),
  amount NUMERIC
);

```

Create app.yml
```
spring:
  datasource:
    url: "jdbc:postgresql://localhost:5432/payment"
    username: postgres
    password: postgres
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
    <logger name="de.thmshmm.airplus" level="debug" additivity="false">
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
