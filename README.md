# Automation of the service delivery
This is an interactive web software used to save student records with enrolled courses.
But the best part is: you will see the beauty of functionality testing, unit testing and web interface testing.

Before the start, make sure you have installed:
* JDK (i used version 17)
* Docker
* Maven

1. First, run this code:
```
mvn clean package
```

Your log should have messages like this (ending):
```angular2html
[INFO] --- spring-boot:2.2.6.RELEASE:repackage (repackage) @ webtest ---
[INFO] Replacing main artifact with repackaged archive
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  01:03 min
[INFO] Finished at: 2024-03-12T21:50:58+04:00
[INFO] ------------------------------------------------------------------------
```
We call this "happiness" - all tests are successfuly passed, which means your software units, functions and web interface work fine! No bugs (hopefully & thankfully)

2. Secondly, let's build Docker image
```angular2html
docker build -t springio/webtest .
```

3. Finally, let's run the container from Docker image:
```angular2html
docker run -p 8080:8080 springio/webtest
```

And that's it! App is running on port 8080