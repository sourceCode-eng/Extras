# Contents
1. Contents of the repo
2. How to import the database
3. How to run the app
# Contents of the repo
This repo contains 2 main folders as well as an sql file
ServerSide is a java project that has the driver in order to ensure proper communication with the database , it listens to port 1234 and communicates with MySql
it also has a second file ServerTest if you encounter any connection problems and want to test it , you can start it and the one of the files inside ClientTest to Test it .
ClientTest has the files used in order to visualize the communication in the backround and test the connection to the server
# how to import the database 
Simply start Xampp's apache server and Sql and visit http://localhost/phpmyadmin/index.php?route=/server/import 
You can then choose the file "courses.sql" and import it
# How to run the app 
Make sure the ip in /app/java/server is that of your sever 
Start the server and xampp's sql
Start the app and it should work perfectly fine
