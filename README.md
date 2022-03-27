# EComService

Prerequisites (for mysql):-

1. MySQL has to be installed.
2. Create a user with following query:- (create user 'adminTest'@'localhost' identified by 'password1*2';)
3. Grant privileges to the user with (grant all privileges on *.* to 'adminTest'@'localhost';)
4. Create the database with this user (create database ecom_test charset utf8 collate utf8_bin;)
5. Or grant privilege to the database to this user
6. Install maven.

Or if you are using postgresql:-
1. Create a user postgre with password given in application.properties
2. Create a database named 'ecom_test'
