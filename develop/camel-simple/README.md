camel-simple: Demonstrates how to use a simple Camel route
======================================================
Level: Beginner  
Technologies: Camel, Blueprint
Summary: Demonstrates how to use a simple Camel route
Target Product: Fuse


What is it?
-----------

This quick start shows a simple hello world with apache camel.

System requirements
-------------------

Before building and running this quick start you need:

* Maven 3.0.4 or higher
* JDK 1.6 or 1.7
* JBoss Fuse 6


Build and Deploy the Quickstart
-------------------------

1. Change your working directory to `camel-simple` directory.
* Run `mvn clean compile` to build the quickstart.
* Run `mvn fabric8:deploy` to upload the quickstart and create a profile.


Use the bundle
---------------------

Check the logs


Undeploy the Archive
--------------------

To stop and undeploy the bundle in Fuse:

1. Enter `osgi:list` command to retrieve your bundle id
2. To stop and uninstall the bundle enter

        osgi:uninstall <id>
 