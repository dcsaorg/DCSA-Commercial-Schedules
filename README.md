# DCSA Project Template

**[RECOMMENDED]** <br>
Setup a Github Personal Access Token as mentioned [here](https://github.com/dcsaorg/DCSA-Core/blob/master/README.md#how-to-use-dcsa-core-packages).

- Rename the project
- Update the README.md
- Update the pom.xml
- Update the application.yml
- Update the Dockerfile
- Update the docker-compose.yml file
- Update GitHub Actions

### Checklist to setup the `template` for your project:
- [ ] Rename the project, DCSA-{ProjectName} (e.g. DCSA-TNT)
- [ ] `DCSA-Project-Template > pom.xml`
  ```xml
   <groupId>org.dcsa.template</groupId>
   <artifactId>dcsa-template-parent</artifactId>

   <properties>
        <group.id>org.dcsa.template</group.id>
        <artifact.id>dcsa-template</artifact.id>
        <project.name>DCSA Template</project.name>
	  </properties>
  ```
- [ ] `DCSA-Project-Template > domain > pom.xml`
  ```xml
    <parent>
        <groupId>org.dcsa.template</groupId>
        <artifactId>dcsa-template-parent</artifactId>
        ...
    </parent>
  ```
- [ ] `DCSA-Project-Template > transfer-obj > pom.xml`
  ```xml
    <parent>
        <groupId>org.dcsa.template</groupId>
        <artifactId>dcsa-template-parent</artifactId>
        ...
    </parent>
  ```
- [ ] `DCSA-Project-Template > service > pom.xml`
  ```xml
    <parent>
        <groupId>org.dcsa.template</groupId>
        <artifactId>dcsa-template-parent</artifactId>
        ...
    </parent>
  ```
- [ ] `DCSA-Project-Template > application > pom.xml`
  ```xml
    <parent>
        <groupId>org.dcsa.template</groupId>
        <artifactId>dcsa-template-parent</artifactId>
        ...
    </parent>
  ```
- [ ] Rename package `DCSA-Project-Template > application > src > main > java > org.dcsa.template`
- [ ] `DCSA-Project-Template > application > src > main > resources > application.yml`
  ```yaml
    spring:
      application:
        name: dcsa-template
      datasource:
        url: jdbc:postgresql://${db_hostname}/dcsa_template
  ```
- [ ] `DCSA-Project-Template > Dockerfile`
  ```dockerfile
  COPY application/target/dcsa-template-application.jar .
  ```
- [ ] `DCSA-Project-Template > docker-compose.yml`
  ```yaml
    dcsa-template:
      container_name: dcsa_template

    dcsa-test-db:
      container_name: dcsa_template_db
  ```

#### Build and Run the Project
```shell
mvn clean package

docker-compose up -d -V --build

curl http://localhost:9090/actuator/health
```

#### Creating a Repository from the Template
https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template#creating-a-repository-from-a-template
