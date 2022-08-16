# Springboot Fast Deployer

This repository allows for a fast and secure deployment of a pre-packaged **Springboot 2.7.1** + **Thymeleaf** + **Bootstrap** application that is also high customizable. You can start you own Springboot appication in a moment with minimum effort. The project is organized as follows.

## Licensing information
Copyright (C) 2022.  Daniele Francesco Santamaria. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see https://www.gnu.org/licenses/.


## Database Layer

A Database  with two different catalogs is provide, one called **data** for data and one called **useraccount** for user information. The configuration file is  available at **/platform/configuration/data/JpaTransactData.java**. Edit the **application.properties** file in **/resources** for to configure your database credentials. In the folder **/platform/persistence/model**, you can find the **@Entity** classes: the folder **/user** is dedicated to the user account data, while the data entry models are available in the subfolder **/data**. If you change the name of the  schemas, remember also to update the file **/test/resources/schema.sql** used for the JUnit module and the content of the test file **ApplicationTest.java** in the **/context** subfolder.

Specifically, you can find four models deticated to users, namely **Organization** (Organization.java) for your user affiliations, **Privilege** for your user roles within the platform, **User**" for the user info, and **SecureToken** for the user security tokens. You can change your user info as you desire. Remember also to customize the file **data.java** for your own data entries.

## Persistence Layer

One jpa repository for each model is available at **/platform/persistence/repository**, in order to serialize data. User repositories are available in the **/user** subfolder, while data repository is available in the **/data** subfolder. If you change your models, remember to updates the related repositories.


## Business Layer
For each repository is avaiable a service interface in the folder **/platform/serviceinterface**, while their implementations are available at **/platform/service**. As usual, two distinc folders are provided for data and users. Add you business functionalities in the files present in those folders and remember to update the related interfaces. 

Authorization methods are available at  **/platform/configuration/security**. You can find the login and logout handlers in the subfolders **/login** and **/logout** respectively.  The web security configurator is available in the subfolder **/websecurity**.

Currently, four user roles are available, **superadmin**, **admin**, **staff**, and **user**.  Login APIs are available in the folder **/platform/controller/access**. Users are required to validate their email used in the registration via email. Configure the **application.properties** in ordet to set the credentials used by the email client used to send and receive email.

A controller showing the application information is available at **/platform/controller/info/**. To edit the information modify the **pom.xml** file. The module **PomReader.java** in **/platform/utility/user** permits to read the pom information. Please do not change the method **initFork()**, since it allows to manifest that your project has been built upon **SpringBootFastDeploy** for authorship purposes.

A logging system is provided through **SLF4J** and **Logback**. For customizing the provided logger, edit the file **/resources/logback-spring.xml**.

## Presentation layer

You can find all your static web pages in **/resources/static**. Thymeleaf templates are available in **/resources/templates**.


## JUnit test

A set of JUnit test in the folder **/test** are provided to ensure that your modifications are consistent. In the **/platform/context** subfolder a set of tests ensures that the context and datasources are loaded correctly. In the subfolder **/platform/service** there is one test for each available service, thus ensuring that the persistent layer and the related services work correctly. In the subfolder **/platform/controller**, there is a test for each available controller too.


## Main application
The main application is available at **/spring/platform**. Remember to change the final name of your application before deploying, by changing the name **platform** with you application name alongside the project files. Remember also to change the name of the context set in the **context.xml** file in **/resources/webapp/META-INF/**.
