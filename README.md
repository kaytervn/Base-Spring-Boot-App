# Base Spring Boot Application

This is a base Spring Boot application that provides a foundation for building robust and scalable web services.

[BitBucket Projects](https://git.developteam.net/projects)

[Gorgeous Swagger Converter](https://kaytervn.github.io/Gorgeous-Swagger-Converter-Web/)

[.gitignore](.gitignore)

## Notes

**MySQL Dump:**

```cmd
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqldump" -u root -p <database-name> > D:\Downloads\dump_file.sql
```

**MySQL Change Root Password:**

```cmd
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqladmin" -u root -p password <new-password>
```

**Liquibase Changelog Export Data:**

```
mvn liquibase:generateChangeLog -Dliquibase.diffTypes=data
```

---

### Starting the Application

**1.** Rename server PORT, database credentials in `application-dev.properties` and **Liquibase** properties in `pom.xml`

**2.** Run **Maven** `clean` and rebuild project

**3.** Set **SDK** to Version **11** in **File** → **Project Structure** (`Ctrl` + `Alt` + `Shift` + `S`)

**4.** Ensure database is **created** before running application

> [!NOTE]
> **Swagger UI:** `localhost:<PORT>/swagger-ui.html`

| **Topic**               | **Details** |
|-------------------------|-------------|
| **CRUD for a new Model** | <details><summary><b>CRUD for a new Model</b></summary><br> **1.** Create model class in `src/main/<your-package>/model`<br> **2.** Run **Maven** `clean` and rebuild project<br> **3.** Generate **Liquibase** changelog: `Maven` → `Plugins` → `liquibase` → `liquibase:diff`<br> **4.** Apply new changelog in `src/main/resources/liquibase/db.changelog-master.xml`<br><br> |                         File Creation Order                          |<br> | :------------------------------------------------------------------: |<br> | `Repository` → `Criteria` → `Form` → `DTO` → `Mapper` → `Controller` |<br><br> **Directory Structure**<br> ``` src/ ├── controller/<ModelName>Controller.java │ ├── dto/<ModelName>/ │   ├── <ModelName>Dto.java │   └── <ModelName>AdminDto.java │ ├── form/<ModelName>/ │   ├── Create<ModelName>Form.java │   └── Update<ModelName>Form.java │ ├── mapper/<ModelName>Mapper.java ├── model/criteria/<ModelName>Criteria.java └── repository/<ModelName>Repository.java ``` <br><br> |                                                         Controller Method Order                                                          |<br> | :--------------------------------------------------------------------------------------------------------------------------------------: |<br> | **get** (`MODEL_V`) → **list** (`MODEL_L`) → **autoComplete** → **create** (`MODEL_C`) → **update** (`MODEL_U`) → **delete** (`MODEL_D`) |<br><br> **Note:** `MODEL` is a 2-3 character abbreviation of the model name (e.g., `SE_P` for `ServerProvider`).<br><br></details> |
| **RabbitMQ Setup**       | <details><summary><b>RabbitMQ Setup</b></summary><br> **1.** Install RabbitMQ<br>- In Terminal: Run `docker pull rabbitmq:3.13.6-management`<br>- In Docker Desktop: Run `rabbitmq` **Image**<br>- Set ports: `15672`:15672/tcp (**UI**), `5672`:5672/tcp (**AMQP**)<br> **2.** Access Management UI<br>- Open: http://localhost:15672/<br>- Login: `guest` / `guest`<br> **3.** Create Admin User<br>- Go to **Admin** tab<br>- Add new user with `Admin` tag<br> **4.** Run application and login with new admin account<br><br></details> |

---

### Cloning Source Code

**1.** Open Terminal: Run `git clone <HTTP-Git-URL>`

**2.** Refactor package in `src/main/java/<your-package>`

**3.** Update `pom.xml`: `groupId`, `artifactId`, `name`, **Liquibase** properties

**4.** Modify `application-dev.properties`: database credentials, server **PORT**

**5.** Rename database table prefix in **models** and **Liquibase** changesets

**6.** Delete classes in folders: `controller`, `mapper`, `dto`, `form`, `model/criteria`, `repository`, `validation`

**7.** Modify: `dto/ErrorCode.java`, `constant/<your-app>Constant.java`

**8.** Keep base modules: `Account`, `Group`, `Permission`, `Setting`, `File`

---

**SourceTree Set up and Basic Operations:**

---

**Create a custom grant type**
