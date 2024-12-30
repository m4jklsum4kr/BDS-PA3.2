# BDS - JavaFX Demo Template
This project is a template project to train basics of JavaFX (Java Desktop GUI).

## To build&run the project
Enter the following command in the project root directory to build the project.
```shell
$ mvn clean install
```

Run the project:
```shell
$ java -jar target/my-app-1.0.0.jar
```

The `application.properties` file can be located in any place on the system that the application can access.

Sign-in with the following credentials:
- Username: ``
- Password: ``

## To generate the project and external libraries licenses
Enter the following command in the project root directory
```shell
$ mvn project-info-reports:dependencies
```

The licenses info and documentation will be located in the `target/site` folder.
