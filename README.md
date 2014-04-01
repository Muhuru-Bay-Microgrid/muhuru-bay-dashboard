# Muruhu Bay Micro-grid Dashboard

## Overview

This is a [Spring Boot](http://projects.spring.io/spring-boot/) based  [Vaadin](https://vaadin.com/home) web application to support the display of remote telemetry data from the [Muruhu Bay Micro-Grid Project](https://www.facebook.com/MuhuruBayCommunityMicrogrid).

### Prerequesites

 * Java 1.7+ (may move to 1.8 quickly)
 
### Building and Running the App
This is a [Gradle](http://www.gradle.org) based build with support for [GradleWrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html). This means you should be able to clone this repository and from the command-line (at the project root) execute either (Unix)

	> gradlew build
or on Windows

	> gradlew.bat build
	
*Editors Note: I develop on a Mac so my examples will be Unix oriented*

Run `gradlew tasks` to view all the tasks.  To run the app simply type `gradlew run` and the app will launch locally at <http://localhost:9000>  

### Debugging
In Eclipse there appears to be at least two convienent ways to debug the app (at the moment):

* As a Java main() application - simply select the org.mbmg.SpringBootVaadin's main() method and [Right Click] Debug As -> Java Application.
* Using M2E - Simply select the Project in the Package Explorer and [Right Click] Debug As -> Spring Boot Application.

Not yet sure how this gets modifed for running devmode and superdevmode (more to come)


#### TODO

* Keep fleshing out this readme
* Integrate the open source charts support from <https://vaadin.com/directory#addon/freecode-charts-and-d3-wrapper:vaadin>
* Setup CI in the cloud (Cloudbees or equivalent)
* Automate deployment to our EC2 instance
* Move to Java 1.8
* Build the app!

