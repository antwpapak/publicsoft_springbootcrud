# Spring Boot Crud

### Structural Changes
* Upgraded to JAVA 11
* Upgraded Spring Boot to 2.2.5.RELEASE (due to Swagger incompatibility with spring-data-rest)
* Added [Swagger](https://swagger.io/), [Logback](http://logback.qos.ch/) and [Lombok](https://projectlombok.org/) dependencies

### API Documentation
You can find information about the API on 
`/swagger-ui/index.html`

### Database Settings
The database settings are configured in the `application.settings` file, located in the resources directory of the 
`springbootcrud-webapp` module. <br>
Currently, the settings are:
* MySQL port: 3307
* username: root
* password: <no-password>

Please edit them accordingly.

### Logging
Apart from the tasks asked, I also added a simple HTTP logger to log all incoming requests and their corresponding
responses.<br>
Currently the logs are printed on the console, but can easily be stored in the filesystem with a logback.xml configuration
file.

### Tests
I wrote some basic integration tests for the Supplier API.

### Things to Consider
Although I adapted to the architecture of the project, there are some things that I would design differently:<br>
* __Use DTOs:__ Exposing our entities directly to the API is considered a bad practice. The model and the API are 
tightly coupled and there is no way to alter one of them without affecting the other.<br>
By using DTOs, we hide the implementation details of our model from the web layer.

* __Don't use spring-data-rest:__ Although it saves some time, using spring-data-rest is very restrictive and makes layering of the application not possible. 
Services, controllers and repositories are squeezed into one, so we lose the ability to fine tune and extend the application.<br>
Note that one other downside of using spring-data-rest is that we cannot use AspectJ annotations, because internal service methods are not
being called by any bean defined in our app. 

* __Use a search library:__ If a search feature is desired, I would go with [Hibernate Search](https://hibernate.org/search/). 
It is highly configurable and provides fuzzy search and stemming.<br>
_(Note that using a search library is not possible now, as there is no service layer)_


### Front-End Application

A simple UI for managing persons and suppliers.<br><br>

Before using it be sure to install dependencies by executing `npm install` from the root directory
of the client app.<br>
* To run it locally (on a dev server), navigate to the root folder of the client app and execute `npm start`.<br>
* To create a chunked, minified version for production use, run `npm run build`. A `dist` directory will be created, ready 
to be deployed on a web server.<br>

_Please note that the built version is meant to be served over HTTP. Opening_ `index.html` _on a
web browser is not going to work_.
