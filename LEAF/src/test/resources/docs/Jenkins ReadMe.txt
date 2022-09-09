Jenkins Job Basic Configuration: 

1. Restrict where this project can be run
Label Expression: Node name

2. Source Code Management
Git
Repository URL:
Credential:

3. Build
Execute Windows batch command
Command: cd /d %WORKSPACE%\<as per project, depends on Git structure>
mvn clean test -Dcucumber.options="--tags @smoke" -Pagile -DbrowserName=chrome

4. File Operation
Folder Copy
Source Folder: <project>\target\cucumber-reports
Destination Folder: \$BUILD_NUMBER
