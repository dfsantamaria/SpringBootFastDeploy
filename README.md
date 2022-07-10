# Springboot Fast Deployer

This repository allows a fast and secure deploy of a pre-packaged Springboot 2.7.1 application that is also high customizable. You can start you own Springboot appication in a moment with minimum effort. The project is organized as follows.

## Database Layer

A double-sourced Database, one called "userSource" for users and login and one for data entries, called "dataSource". The configuration files are  available at /application/configuration/user/JpaTransactUser.java and /application/configuration/data/JpaTransactData.java, respectively. Edit the application.properties file in /resources for your own data. In the folder /application/persistence/model, you can find the @Entity classes: the folder /user is dedicated to the user info data, while the data entries model are available in the subfolder /data. If you change the name of the catalogs, remember also to update the file /test/resources/schema.sql used for the JUnit module and the content of the test file ApplicationTest.java in the /context subfolder.

Specifically, you can find three models for users, namely "Organization" (Organization.java) for your users affiliation, "Privilege" for your users role within the platform, and "User" for you users info. You can change your user infor as you see fit. Remember also to customize the file data.java for your own data entries.

## Persistence Layer

One jpa repository for each model is available at /application/persistence/repository, in order to serialize data. User repositories are available in the /user subfolder while data repository is available in the /data subfolder. If you change your models, remember to updates the related repositories.


## Business Layer
For each repository is avaiable a service interface in the folder /spring/application/serviceinterface, while their implementations are available at /spring/application/service. As usual, two distinc folders are provided for data and users. Add you business functionalities in the files present in those folders and remeber to update the related interfaces. 

Authorization methods are available at  /spring/application/configuration/security. Currently, four user roles are available, "superadmin", "admin", "staff", and "user".  Login APIs are available in the folder /spring/application/controller/user.

For customizing the provided logger, edit the file /resources/logback-spring.xml.

## Presentation layer

You can find all your web pages in resources static.


## JUnit test

A set of JUnit test in the folder /test are provided to ensure that your modifications are consistent. In the /application/context subfolder a set of tests ensures that the context and datasources are loaded correctly. In the subfolder /application/service there is one test for each available service, thus ensuring that the persistent layer and the related services work correctly. In the subfolder /application/controller, there is a test for each controller too.


##Main application
The main application is available at /spring/application. Remember to change the final name of your application before deploying, by changing the name "application" with you application name alongside the project files. Remember also to change the name of the context in the file "context.xml" in /resources/webapp/META-INF/.
