# Chat App Rest API

Backend for basic chatroom website that utilizes Spring Security for user registration and authentication.

### Prerequisites

* Java JDK 8 or higher
* Tomcat 8 or higher
* MySQL database

### Setup

1. Setup MySQL server either on your local machine or remotely.
2. Create a hibernate.cfg.xml file to store your room and profile pictures.
>Note: The file should look like similar to the following, but with the url, username and
password properties filled in.
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="hibernate.connection.url"></property>
        <property name="hibernate.connection.username"></property>
        <property name="hibernate.connection.password"></property>
        <property name="hibernate.connection.pool_size">5</property>
        <property name="hibernate.current_session_context_class">thread</property>
        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
        <property name="hibernate.c3p0.min_size">10</property>
        <property name="hibernate.c3p0.max_size">20</property>
        <property name="hibernate.c3p0.acquire_increment">1</property>
        <property name="hibernate.c3p0.idle_test_period">3000</property>
        <property name="hibernate.c3p0.max_statements">50</property>
        <property name="hibernate.c3p0.timeout">1800</property>
    </session-factory>
</hibernate-configuration>
```
3 . Create a persistence-mysql.properties file to support user authentication.
>Note: The file should look like similar to the following, and with the url, username and
       password properties filled in.
```
#
# JDBC connection properties
#
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=
jdbc.user=
jdbc.password=

#
# Connection pool properties
#
connection.pool.initialPoolSize=5
connection.pool.minPoolSize=5
connection.pool.maxPoolSize=20
connection.pool.maxIdleTime=3000
```
4 . Setup your database for authentication using the following script.
```
DROP DATABASE  IF EXISTS `authentication`;

CREATE DATABASE  IF NOT EXISTS `authentication`;
USE `authentication`;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
```
5 . Lastly, setup you room/profile database with the following script.
```
DROP DATABASE  IF EXISTS `chatrooms`;

CREATE DATABASE  IF NOT EXISTS `chatrooms`;
USE `chatrooms`;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
CREATE TABLE `rooms` (
  `roomName` varchar(50) NOT NULL,
  `roomDesc` varchar(100) NOT NULL,
  PRIMARY KEY (`roomName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `room`
--

INSERT INTO `rooms` 
VALUES 
('Main','Default chatroom that is always open.');

--
-- Table structure for table `profiles`
--

DROP TABLE IF EXISTS `profiles`;
CREATE TABLE `profiles` (
  `username` varchar(50) NOT NULL,
  `avatar` MEDIUMBLOB NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
```
## Deployment

1. Complete setup instruction above.
2. Generate .war file
```
mvn clean install
```
3. Deploy to Tomcat server (or equivalent).
4. Check that you can access the data by navigating to ../ in your web browser.

## API Usage

>Note: ALL API endpoints for this project start with url/chatapi/api/
> Example: https://broomes.net/chatapi/api/rooms

Check controllers in the controller package for specific endpoint syntax.

## Built With

* [Spring MVC](https://spring.io/docs/) - The web framework used
* [Spring Security](https://spring.io/projects/spring-security) - User authentication
* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Granville Broomes** - *Creator* - [Broomes](https://github.com/Broomes)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
