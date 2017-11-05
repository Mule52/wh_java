# wh_java

# Building
mvn clean compile package
<br/>
mvn exec:java -Dexec.mainClass=Main -Dexec.args="--startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100"

# Config
resources/db.properties has database specific settings that need updates.
<br/>
db_server_url=jdbc:mysql://localhost:3306/name?useSSL=false<br/>
db_name=name<br/>
db_username=fake<br/>
db_password=fake<br/>

# Running from project root
java -cp "./target/parser-jar-with-dependencies.jar" com.ef.Parser --accessLog=./target/classes/access.log --startDate=2017-01-01.15:00:00 --duration=hourly --threshold=200

# Debugging from project
Uncomment one of the args[] in ApplicationArguments and update db.properties.

