# AirPlus Transactions Batch Import

A Spring Batch application created with Spring Boot to import AirPlus Transactions into a PostgreSQL database.

Lines of this format can be imported:
```
Card No | Invoice No | Invoice Date | Invoice-Item No | Purchase Date | Entry Date | Service Provider | Service Description | Currency | Amount | Debit/Credit
112233xxxxxx1234;88993344;2018-01-02;6;2017-12-13;2017-12-14;Taxi 4432;Internet;EUR;88,80;S
```

The original exported file contains a few more columns which where excluded from import.

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
