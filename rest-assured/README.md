# Java Rest Assured
Automated REST API tests with YAddress API.

## How to run

### Via IDE
Just open this project in an IDE of your preference (I use Intellij) and run the [YaddressApiTest](src/test/java/yaddress/YaddressApiTest.java) as a JUnit test.

### Via command line
#### Pre-requisites:
- have [mvn](https://maven.apache.org/download.cgi) installed
- have JAVA_HOME variable configured in your environment preferable pointing to [JAVA 11 JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) folder
#### Running 
- open your terminal in the [rest-assured](/rest-assured) project's folder
- run `mvn clean install` to download all the dependencies and run the tests
- you can also run `mvn test` if you have already downloaded all the 
- in the end, you should see this:

![mvn-test-results](/rest-assured/tutorial-img/results.png)