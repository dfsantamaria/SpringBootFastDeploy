# Springboot Fast Deployer

This repository allows for a fast and secure deployment of a pre-packaged Springboot 2.7.1 application that is also high customizable. You can start you own Springboot appication in a moment with minimum effort. The project is organized as follows.

## Licensing information
Copyright (C) 2022.  Daniele Francesco Santamaria. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see https://www.gnu.org/licenses/.


## Database Layer

A double-sourced Database is provided: one called "userSource" for users and login and one for data entries, called "dataSource". The configuration files are  available at /application/configuration/user/JpaTransactUser.java and /application/configuration/data/JpaTransactData.java, respectively. Edit the application.properties file in /resources for to configure your database credentials. In the folder /application/persistence/model, you can find the @Entity classes: the folder /user is dedicated to the user account data, while the data entries model are available in the subfolder /data. If you change the name of the catalogs, remember also to update the file /test/resources/schema.sql used for the JUnit module and the content of the test file ApplicationTest.java in the /context subfolder.

Specifically, you can find four models deticated to users, namely "Organization" (Organization.java) for your users affiliation, "Privilege" for your users role within the platform, "User" for you users info, and "SecureToken" for the users security tokens. You can change your user info as you desire. Remember also to customize the file data.java for your own data entries.

## Persistence Layer

One jpa repository for each model is available at /application/persistence/repository, in order to serialize data. User repositories are available in the /user subfolder while data repository is available in the /data subfolder. If you change your models, remember to updates the related repositories.


## Business Layer
For each repository is avaiable a service interface in the folder /spring/application/serviceinterface, while their implementations are available at /spring/application/service. As usual, two distinc folders are provided for data and users. Add you business functionalities in the files present in those folders and remember to update the related interfaces. 

Authorization methods are available at  /spring/application/configuration/security. You can find the login and logout handlers in the subfolders /login and /logout respectively.  The web security configurator is available in the subfolder /websecurity.

Currently, four user roles are available, "superadmin", "admin", "staff", and "user".  Login APIs are available in the folder /spring/application/controller/access.

For customizing the provided logger, edit the file /resources/logback-spring.xml.

## Presentation layer

You can find all your static web pages in resources/static. Thymeleaf templates are available in resources/templates.


## JUnit test

A set of JUnit test in the folder /test are provided to ensure that your modifications are consistent. In the /application/context subfolder a set of tests ensures that the context and datasources are loaded correctly. In the subfolder /application/service there is one test for each available service, thus ensuring that the persistent layer and the related services work correctly. In the subfolder /application/controller, there is a test for each available controller too.


## Main application
The main application is available at /spring/application. Remember to change the final name of your application before deploying, by changing the name "application" with you application name alongside the project files. Remember also to change the name of the context in the file "context.xml" in /resources/webapp/META-INF/.
