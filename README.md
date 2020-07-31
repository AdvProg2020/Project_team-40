# Project_team-40  
>  Sharif University of Technology Advanced Programming course project

## Team Members: 
Name        |    Student ID
----------- | -------------
Amirali Ebrahimzadeh | 98105546
Matin Daghyani |98106456   
Mohammadbagher Soltani |98105813

## Setup guide
Some libraries used in this project do deep reflection, so they need to access all members, including private ones. You can grant this access running the following  commands in java command line, or just adding them to your IDE's JVM-options (based on the corresponding instructions).
```
--add-modules
java.sql
--add-opens
java.base/java.lang.reflect=com.jfoenix
--add-opens
java.base/java.util=yagson
--add-opens
java.base/java.io=yagson
--add-opens
javafx.base/javafx.util=yagson

```


