# Base Spring Boot Application

This is a base Spring Boot application that provides a foundation for building robust and scalable web services.

## Features

- RESTful API support
- Database integration with JPA
- Authentication and Authorization with custom token enhancement
- Asynchronous processing capabilities
- RabbitMQ integration for messaging
- Email sending functionality
- Validation support
- Liquibase for database migration

## Notes

**MySQL Dump:**

```cmd
cd C:\Program Files\MySQL\MySQL Server 8.0\bin
```

```cmd
mysqldump -u root -p db_name > D:\Downloads\dump_file.sql
```

**MySQL Change Root Password:**

```cmd
cd C:\Program Files\MySQL\MySQL Server 8.0\bin
```

```cmd
mysqladmin -u root -p password new_password
```

**Liquibase Changelog Export Data:**

```
mvn liquibase:generateChangeLog -Dliquibase.diffTypes=data
```
