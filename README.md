# Contacts List
Project for iTechArt's internship with multiple modules: business logic and web module.<br/>
According to the project technical requirement, no modern framework (Spring, React, Angular etc.) can be used.<br/>
Work period: 6 July 2020 - 12 August 2020

### Getting Started
1) Set url, username, password for connection to database ( `LogicModule/src/main/resources/db.properties`)
2) Set the path to a directory for uploading files ( `LogicModule/src/main/resources/diskPath.properties`)
3) Run the script `dataBaseScript.sql`


### Functionality
* Browse over the contacts list with pagination
* CRUD operations with contacts
* Upload a photo and attachments to the server
* Search contacts by search filters
* Send messages to selected contacts via email by ready-made templates or without them
* Automatic (at 12:00) sending notification about birthdays via email to admin


### Technology Stack
* **Frontend:** JavaScript (ECMAscript 5), HTML5/CSS3, Bootstrap 4.5 CSS
* **Backend:** Java 13, Servlets, JDBC, REST API, Slf4j (Logback), Apache libraries, Quartz-scheduler, Java Mail API, FreeMarker Template, Maven, Apache Tomcat
* **Database:** MySQL