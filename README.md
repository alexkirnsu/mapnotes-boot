# MapNotes

MapNotes is an application for secure storage simple notes on the google map. It provides possibility to separate notes connected to the place.

# Features:

  - Register of user
  - Authenticate user with login and password
  - Logout authenticated user
  - Create new note by click on the map and filling the form
  - Get information about note by click on marker
  - Create both private and public notes
  - Delete note ony if there is no alien comments
  - Add comment to public note by any user
  - Update own comment 

### Tech

MapNotes uses a number of open source projects to work properly:

* [Spring](https://spring.io) - a framework for web apps
* [Hibernate](https://hibernate.org) - ORM
* [Google Maps API](https://cloud.google.com/maps-platform/) - an API for creating maps and its elements
* [MySQL](https://www.mysql.com) - a database
* [Apache Log4j](https://logging.apache.org/log4j/2.x/) - logger
* [Lombok](https://projectlombok.org) - a java library that automatically plugs into your editor and build tools
* [Jetty](https://www.eclipse.org/jetty/) - a web server and javax.servlet container
* [Swagger](https://swagger.io/) - create documentation for API
* [Docker](https://www.docker.com/) - a container technology
* [Liquibase](https://www.liquibase.org/) - a library for tracking, managing and applying database schema changes
* [JUnit4](https://junit.org/junit4/) - a simple framework to write repeatable tests
* [Mockito](https://site.mockito.org/) - a  mocking framework for unit tests in Java

And of course MapNotes itself is open source with a [public repository](https://github.com/alexkirnsu/mapnotes) on GitHub.

### Installation

To simplify installation of database it recomends to use Docker image of MySql. So we will store our database in docker.
If you haven't Docker already, please [download](https://www.docker.com/get-started).
To create database you simply need to follow instructions:
```sh
$ docker pull mysql
$ docker run -p 6603:3306 --name myts1 -e MYSQL_ROOT_PASSWORD=password -d  mysql
$ docker exec -it  myts1 bash
```
Further create database with name 'mapnotes'
```sh
$ mysql -u root -p password
$ create database mapnotes
```
To obtain necessary database schema you should create new maven configuration with command line:
```sh
$ liquibase:update
```
and run it.
### Running
Just run 'Application' (Ctrl + Shift + F10)

Open your web browser and visit http://localhost:8080.
If everything is fine, you should see authentication page.

### Documentation
After authentication it is possible to find swagger documentation of API via http://localhost:8080/swagger-ui.html 
