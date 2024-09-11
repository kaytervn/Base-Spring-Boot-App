# Base Spring Boot Application

This is a base Spring Boot application that provides a foundation for building robust and scalable web services.

[BitBucket Projects](https://git.developteam.net/projects)

[Gorgeous Swagger Converter](https://kaytervn.github.io/Gorgeous-Swagger-Converter-Web/)

[.gitignore](.gitignore)

## Notes

**MySQL Dump:**

```cmd
cd C:\Program Files\MySQL\MySQL Server 8.0\bin
```

```cmd
mysqldump -u root -p <your-database-name> > D:\Downloads\dump_file.sql
```

**MySQL Change Root Password:**

```cmd
cd C:\Program Files\MySQL\MySQL Server 8.0\bin
```

```cmd
mysqladmin -u root -p password <your-new-password>
```

**Liquibase Changelog Export Data:**

```
mvn liquibase:generateChangeLog -Dliquibase.diffTypes=data
```

---

**Starting the Application:**

| Step | Directory/Location                              | Instruction                                                                                      |
| ---- | ----------------------------------------------- | ------------------------------------------------------------------------------------------------ |
| 1.   | `src/main/resources/application-dev.properties` | Update the database credentials.                                                                 |
| 2.   | `pom.xml`                                       | Update the Liquibase properties.                                                                 |
| 3.   | Right-side Navbar                               | Navigate to Maven -> `<your-app>` -> Lifecycle -> clean.                                         |
| 4.   | Top Navbar                                      | Go to Build -> Build Project (Ctrl + F9) or Rebuild Project.                                     |
| 5.   | Top Navbar                                      | Go to File -> Project Structure -> Select SDK Version 11.                                        |
| 6.   | Run Source                                      | Run the application via `Spring Boot App` or create a new configuration (Application) to run it. |

> **Note**: Ensure that a database is created before running the application.

---

**Cloning the Source Code:**

| Step | Directory/Location                              | Instruction                                                                    |
| ---- | ----------------------------------------------- | ------------------------------------------------------------------------------ |
| 1.   | `/<your-source-storage>`                        | Right-click -> Open in Terminal -> Run `git clone <HTTP-Git-URL>`              |
| 2.   | `src/main/java/<your-package>`                  | Refactor and rename the package to your desired name.                          |
| 3.   | `pom.xml`                                       | Update `groupId`, `artifactId`, and `name`. Also, modify Liquibase properties. |
| 4.   | `src/main/resources/application-dev.properties` | Update the database credentials and server `PORT`.                             |
| 5.   | `src/main/java/<your-package>/model`            | Refactor the database table prefix name.                                       |
| 6.   | `src/main/resources/liquibase`                  | Refactor the database table prefix name in the Liquibase changesets.           |

**7.** Delete the following modules in this order: `controller`, `mapper`, `dto`, `form`, `model/criteria`, `repository`, `validation` (if exists).

> **Modify these files:** `dto/ErrorCode.java`, `constant/<your-app>Constant.java`.

> **Keep the base modules:** `Account`, `Group`, `Permission`, `Setting`, `File`.

---

**Applying CRUD for a New Model:**

| Step | Directory/Location                                     | Instruction                                                            |
| ---- | ------------------------------------------------------ | ---------------------------------------------------------------------- |
| 1.   | `src/main/<your-package>/model`                        | Create a new model class.                                              |
| 2.   | Right-side Navbar                                      | Navigate to Maven -> `<your-app>` -> Lifecycle -> clean.               |
| 3.   | Top Navbar                                             | Go to Build -> Build Project (Ctrl + F9) or Rebuild Project.           |
| 4.   | Right-side Navbar                                      | Go to Maven -> `<your-app>` -> Plugins -> liquibase -> liquibase:diff. |
| 5.   | `src/main/resources/liquibase/db.changelog-master.xml` | Apply the newly generated Liquibase changelog file.                    |

**6.** Create and implement the following files in this order: `repository` -> `model/criteria` -> `form (Create/Update)` -> `dto` -> `mapper` -> `controller`.

**Directory Structure:**

```plaintext
src/
│
├── controller/
│   └── <your-model-name>Controller.java
│
├── dto/
│   └── <your-model-name>/
│       ├── <your-model-name>Dto.java
│       └── <your-model-name>AdminDto.java
│
├── form/
│   └── <your-model-name>/
│       ├── Create<your-model-name>Form.java
│       └── Update<your-model-name>Form.java
│
├── mapper/
│   └── <your-model-name>Mapper.java
│
├── model/
│   └── criteria/
│       └── <your-model-name>Criteria.java
│
└── repository/
    └── <your-model-name>Repository.java
```

**REST API Controller Order:**

1. **Methods should be written in the following order:**

   - `get` (`<MODEL-NAME>`\_V)
   - `list` (`<MODEL-NAME>`\_L)
   - `autoComplete`
   - `create` (`<MODEL-NAME>`\_C)
   - `update` (`<MODEL-NAME>`\_U)
   - `delete` (`<MODEL-NAME>`\_D)

2. **MODEL-NAME** refers to the permission code prefix, which is usually a 2 or 3 character abbreviation for the model.

   For example, if the model is `ServerProvider`, the permission codes would be:

   - `SE_P_V` for `get`
   - `SE_P_L` for `list`
   - `SE_P_C` for `create`
   - `SE_P_U` for `update`
   - `SE_P_D` for `delete`

---

**RabbitMQ Set up and Configuration:**

---

**SourceTree Set up and Basic Operations:**

---

**Tenant Configuration:**

```

```
