# wh_java

# Building
mvn clean compile package
mvn exec:java -Dexec.mainClass=Main -Dexec.args="--startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100"

# Config
Database.java has the MySql settings hard coded for now. Change these as needed.

# Running from project root
java -cp "./target/parser-jar-with-dependencies.jar" com.ef.Parser --accessLog=./target/classes/access.log --startDate=2017-01-01.15:00:00 --duration=hourly --threshold=200

