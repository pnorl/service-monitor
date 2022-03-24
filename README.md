# Service Monitor Application

### Running the application
* In a terminal start the Docker database instance with `docker-compose up`
* In a terminal start the backend service with `cd monitor && mvn clean install && mvn spring-boot:run`
* In a terminal start the monitor UI with `cd monitor-ui && npm install && npm start`

The application can now be accessed on [localhost:3000](localhost:3000)
