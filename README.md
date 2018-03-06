# A Simple implementation of a Reservation management.
#### 0. Introduction.
This is a short documentation about a reservation management API presentation which can create tables and add simply reservation to the available slots.
A table is simply described by `name`. Also the table has a list of `reservations` added after it's created. Tables have a unique `id` and contains list of reservations.

In this documentation, it can also be found how to install and run this RESTful API service written in Java with the help of Spring Boot framework, and as NoSQL database, Redis.

In the following sections, the detailed requirements list and compiling steps will be explained.

#### 1. Requirements.
This service is realized by using:
  - Java - v1.8
  - Apache Maven - v3.5.2
  - Spring Boot Framework and libraries - v2.0.0.RELEASE
  - [Redis]<https://redis.io> - v4.0.8

#### 2. Compiling and packaging.
###### 2.1. Clone the repository.
You can clone the repository from the github link below and then enter the directory where the code has been pushed:
```sh
$ git clone git@github.com:zafatar/reservation-api.git
$ cd reservation-api/
```
###### 2.2. Clean and pack the code.
At this step, we'll need to clean the folder (although it was kept clean in git repo) and then build the JAR package:
```sh
$ mvn clean package
```
This will compile the code, run the test cases and create a JAR file named as `reservation-api-0.0.1.jar` under the `/target` folder in the current directory.

### 3. Running the service.
In order to run the service, the command below can be run in the current directory:
```sh
$ java -jar target/reservation-api-0.0.1.jar
```
After the running this command, the logs for the application and spring will be output in the shell. The service is running on the port `8080` of `localhost` by default. You need to make sure that this port is not occupied by another process or service.

> Note: The other way of running the service is to use spring build-in command, `spring-boot:run` with the help of maven, this call skips the tests. But at this level, for the sake of portability, we will stick to the JAR file.
```sh
$ mvn clean spring-boot:run
```
Default connection to Redis is to `127.0.0.1` and to the default port of a Redis server `6379`. The default database is `1`. If you need to use and reach another Redis server, you'll need to update `application.properties` file under the `src/main/resources` folder.
Example of `application.properties` :
```sh
redis.host=127.0.0.1
redis.port=6379
redis.database=1
```
### 4. API Endpoints.
##### 4.0. Create a table.
At the beginning, since there are no table, an initial requirement in runtime, a Table should be created.

| HTTP Method | Path |
| ------ | ------ |
| POST | /api/v1/table |

Request Body Parameters:

| Parameter name | Type | Possible Values |
| ------ | ----- | ----- |
| name | String | * |

Possible return codes:

| HTTP Status Code | Message |
| ---------------- | ------- |
| 201 | Created |

Example Request:
```sh
$ curl -XPOST -H "Content-Type: application/json" -d '{"name": "Table with Lago di Garda view"}' http://localhost:8080/api/v1/table
```
Example Response:
```sh
{
    "status": "CREATED",
    "message": "Table created",
    "data": {
    	    "id": 1,	
	    "name": "Table with Lago di Garda view",
	    "reservations":  []
    }
}
```

##### 4.1. Retrieve a table by id.
This endpoint will retrieve the table based on the given id.

| HTTP Method | Path |
| ------ | ------ |
| GET | /api/v1/table/<id> |

Possible return codes:

| HTTP Status Code | Message |
| ---------------- | ------- |
| 200 | OK |
| 404 | Table Not Found |

Example Request:
```sh
curl -XGET http://localhost:8080/api/v1/table/1
```
Example Response:
```sh
{
    "status": "OK",
    "message": "Table returned",
    "data": {
    	"id": 1,
        "name": "Table with Lago di Garda view",
        "reservations": [
            {
                "customer_name": "Aang",
   	        "from": "2018-01-04T16:30:00.000Z",
	 	"to": "2018-01-04T17:00:00.000Z"
	    },
	    {
	        "customer_name": "Appa",
	        "from": "2018-01-04T17:00:00.000Z",
	        "to": "2018-01-04T17:30:00.000Z"
	    },
	    {
	        "customer_name": "Momo",
	        "from": "2018-01-04T18:00:00.000Z",
	        "to": "2018-01-04T18:30:00.000Z"
	    }
	]
    }
}
```
##### 4.3. Add a reservation to a table.
This endpoint will add a reservation with a customer name and a given timeslot.

| HTTP Method | Path |
| ------ | ------ |
| POST | /api/v1/table/{id}/reservation |

Request Body Parameters:

| Parameter name | Type | Possible Values |
| ------ | ----- | ----- |
| customer_name | String | * |
| timeslot.from | datetime as String | |
| timeslot.to   | datetime as String | | 

Possible return codes:

| HTTP Status Code | Message |
| --------------   -- | ------- |
| 200 | OK |
| 409 | Conflict |

Example:
```sh
curl -XPOST -H "Content-Type: application/json" -d '{"customer_name": "Aang", "timeslot": {"from": "2018-01-04T22:00:00.000Z", "to": "2018-01-04T22:30:00.000Z"}}' http://localhost:8080/api/v1/
```
Example Response:
```sh
{
    "status": "OK",
    "message": "Reservation added",
    "data": {
    	"id": 1,
        "name": "Table with Lago di Garda view",
        "reservations": [
            {
                "customer_name": "Aang",
   	        "from": "2018-01-04T16:30:00.000Z",
	 	"to": "2018-01-04T17:00:00.000Z"
	    },
	    {
	        "customer_name": "Appa",
	        "from": "2018-01-04T17:00:00.000Z",
	        "to": "2018-01-04T17:30:00.000Z"
	    },
	    {
	        "customer_name": "Aang",
	        "from": "2018-01-04T22:00:00.000Z",
	        "to": "2018-01-04T22:30:00.000Z"
	    }
	]
    }
}
```
### 5. Future works.
 - Better error handling (ex. the case of missing parameters in Table POST).
 - Bugfixes if any found.
 - Writing some integration tests.