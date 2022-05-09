# venus-spring

## Environment 
There are two different properties files are being used for Dev and Production environment respectively. Major difference between both of them is the type of database used with both of them. Property file ends with "-dev.properties" using H2 or file based database and then the file ends with "-prod.properties" using MYSQL as database.

Running Spring application using dev properties 

```
java -jar -Dspring.profiles.active=dev application.jar
```

Using Prod properties file
```
java -jar -Dspring.profiles.active=prod application.jar
```

**Note** :- If you running java application using PROD properties using above command then you also need to start the mysql which you can do using below docker command. Or you can use docker-compose file to run both container.

```
docker run -p 3306:3306  --name venus-mysql -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=venus mysql:latest
```

## Docker to run the application

To build the container including Spring backend named "beeple" and "mysql" database.
```
docker-compose build
```
To run both services in background.
```
docker-compose up -d
```
To list down all the running docker containers
```
docker ps
```
To stop any docker container. Here container id is the value from the first column of pervious command output.
```
docker stop container-id
```

## Data initialization

Starting spring applicaiton will initialize the database (intialization can be controlled with property named "spring.sql.init.mode=always") with three different tables users, authorities and userinfo. Afterwards initialize all these tables with data from below table.

| Email | First Name | Last Name | Role |
| --- | --- | --- | --- |
| admin@venus.com | admin | admin | ROLE_ADMIN
| paulaguilar@venus.com | Paul | Aguilar | ROLE_STAFF |
| jonoliver@venus.com | Jon | Oliver | ROLE_STAFF |
| claudinezhang@venus.com | Claudine |  Zhang | ROLE_STAFF |
| lovelinkumar@venus.com | Lovelin | Kumar | ROLE_STAFF |
| michelkouame@venus.com | Michel | Kouame | ROLE_STAFF |
| angelinacosta@venus.com | Angelina | Costa | ROLE_STAFF |
| brijeshgupta@venus.com | Brijesh | Gupta | ROLE_STAFF |
| amyfofana@venus.com | Amy | Fofana | ROLE_STAFF |
