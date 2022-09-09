********************************
Pre-requisites
********************************
1.Install java jdk 1.8 and configure JAVA_HOME environment variable
2.Install Git Bash in default location: C:\Program Files\Git\bin\git.exe
3.Install maven and configure MAVEN_HOME environment variable
4.Navigate to C:\<users>\.m2 folder and update settings.xml (in case your are using proxies or nexus repository)


********************************
Steps to run
********************************
1.Clone the project from Unicorn QA Automation GIT Respository
2.Checkout Dev branch
3.Launch command and change directory to project folder: e.g D:\worksapce\unicornqa\Unicorn[where you have pom.xml]
4.Run the execution with the following command:mvn test -Dcucmber.options="--tags @DF_Smoke"
Note Change the (@DF_Smoke") tag as per project execution need. Tags have to set in feature files as per framework guidelines
5.Report will be generated under:<project folder>\target\cucumber-reports\report.html

********************************
Cucumber Execution Commands
********************************
Sequential Execution:
Local:
Syntax:
mvn test -Dcucumber.options="src/test/resources/<application dir path OR feature dir path> --tags <anytag>" -Pagile -Dbrowser=<browserName>

Example:
mvn test -Dcucumber.options="src/test/resources/amazon --tags @Parallel" -Pagile -Dbrowser=ie

Remote:
Syntax:
mvn test -Dcucumber.options="src/test/resources/<application dir path OR feature dir path> --tags <anytag>" -Pagile -Dbrowser=<browserName> -Dremote=true -DbrowserVersion="<browserVersion>" -Dplatform="<operatingSystem>" -Dhuburl="<huburl>"

Example:
mvn test -Dcucumber.options="--tags @Parallel" -Pagile -Dbrowser=ie -Dremote=true -DbrowserVersion="ANY" -Dplatform="ANY" -Dhuburl="http://10.101.0.77:5555/wd/hub"

*********************************

Parallel Execution
Local:
Syntax:
mvn test -Dcucumber.options="src/test/resources/<application dir path OR feature dir path> --tags <anytag>" -Pagile -Dbrowser=<browserName> -DthreadCount=<number of scenarios to execution parallely>

Example:
mvn test -Dcucumber.options="--tags @Parallel" -Pagile -Dbrowser=ie -DthreadCount=3

Remote:
Syntax:
mvn test -Dcucumber.options="src/test/resources/<application dir path OR feature dir path> --tags <anytag>" -Pagile -Dbrowser=<browserName> -Dremote=true -DbrowserVersion="<browserVersion>" -Dplatform="<operatingSystem>" -Dhuburl="<huburl>" -DthreadCount=<number of scenarios to execution parallely>

Example:
mvn test -Dcucumber.options="--tags @Parallel" -Pagile -Dbrowser=ie -Dremote=true -DbrowserVersion="ANY" -Dplatform="ANY" -DthreadCount=3 -Dhuburl="http://10.101.0.77:5555/wd/hub"

**********************************
**********************************
**********************************
TestNG Execution Commands:
**********************************
Sequential Execution:
Local:
Syntax:
mvn test -DtestNG.options="<includeGroupName><~excludeGroupName> -Pwaterfall -Dbrowser=<browserName>

Example:
mvn test -DtestNG.options="Parallel" -Pwaterfall -Dbrowser=ie 

Remote:
Syntax:
mvn test -DtestNG.options="<includeGroupName><~excludeGroupName> -Pwaterfall -Dbrowser=<browserName> -Dremote=true -DbrowserVersion="<browserVersion>" -Dplatform="<operatingSystem>" -Dhuburl="<huburl>"

Example:
mvn test -DtestNG.options="Parallel" -Pwaterfall -Dbrowser=ie -Dremote=true -DbrowserVersion="ANY" -DthreadCount=3 -Dhuburl="http://10.101.0.77:5555/wd/hub"

******************************
Parallel Execution
Local:
Syntax:
mvn test -DtestNG.options="<includeGroupName><~excludeGroupName> -Pwaterfall -Dbrowser=<browserName> -DthreadCount=<number of scenarios to execution parallely>

Example:
mvn test -DtestNG.options="Parallel" -Pwaterfall -Dbrowser=ie -DthreadCount=3

Remote:
Syntax:
mvn test -DtestNG.options="<includeGroupName><~excludeGroupName> -Pwaterfall -Dbrowser=<browserName> -Dremote=true -DbrowserVersion="<browserVersion>" -Dplatform="<operatingSystem>" -Dhuburl="<huburl>"

Example:
mvn test -DtestNG.options="Parallel" -Pwaterfall -Dbrowser=ie -Dremote=true -DbrowserVersion="ANY" -DthreadCount=3 -Dhuburl="http://10.101.0.77:5555/wd/hub"
******************************
Note: One execution is recommended per node i.e. when any execution is in-progress in a node, do not trigger another execution on the same node.
*-P: Profile
**-D: and variable must be used in pom.xml or in script.
******************************   

 

  
