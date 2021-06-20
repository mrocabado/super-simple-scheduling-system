# S4 API

# Requirements

## Summary
Create a REST API to for a system that assigns students to classes.  API will be used by both a UI and programmatically by other systems.

## Detailed Requirements

### Models
Student = { student id, last name, first name }
Class = { code, title, description }
Student can attend an unlimited number of classes.  Classes can have an unlimited number of students

### Operations
- Create/Edit/Delete Student
- Create/Edit/Delete Class
- Browse list of all Student
- Browse list of all Classes
- View all Students assigned to a Class
- View all Classes assigned to a Student
- Search Student/Classes by available fields/associations

# Technologies/frameworks

## Base
- Java 8
- Spring Boot 2
- Spring Core 5
- Spring MVC 5
- Lombok

## Util
- Apache Commons Beanutils
- Apache Commons Lang 3

## Layers

Layers are segregated using packages, creating a separated jar artifact for every layer seemed overkill 
for this exercise.

Layering is **NOT** done by technology but by abstraction level:

See: [The Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

### Domain/Core (com.mrocabado.s4.domain.*)
Highest abstraction layer. Main players here are Domain Services, Entities and data access **interfaces**.
The code at this layer has no references to names in neither REST nor DB implementation layers.

### REST (com.mrocabado.s4.rest.*)
The HTTP based Input/Output layer. Main players here are Spring MVC Controller, DTOs and error handlers.
The code at this layer receives input data executes Domain services and builds/returns responses. 
JSON was chosen as the data exchange format.

### DB implementation (com.mrocabado.s4.db.*)
This is an in-memory, map-based mock implementation of the repository interfaces defined in Domain/Core layer. 
This could be changed to use a real DB engine with no impact in Domain/Core. ([Dependency Inversion](https://en.wikipedia.org/wiki/Dependency_inversion_principle) Principle)

## Pending aspects
- Code cleanup (consolidate duplicated code)
- More testing on error handling
- Add more endpoints (removing registrations, for example)
- Enhance controller annotations to improve generated documentation at [swagger-ui](http://localhost:8080/swagger-ui.html) page.

## API documentation and sample calls

In order to ease testing, a few data records are added to the DB implementation layer when the app starts.
[swagger-ui](http://localhost:8080/swagger-ui.html) documentation page.

### Student endpoints

Create
```
POST http://localhost:8080/students/
{
    "id": "1",
    "firstName": "John",
    "lastName": "Doe"
}
```    
Edit
```
PUT http://localhost:8080/students/
{
    "id": "1",
    "firstName": "John",
    "lastName": "Doe"
}
```    
Delete
```
DELETE http://localhost:8080/students/<id>
```
Search (Just a simple property matching strategy for now, use [matrix params](https://www.logicbig.com/quick-info/web/matrix-param.html) to pass a filtering criteria)
```
GET http://localhost:8080/students/id=1;firstName=Mary
```
List of courses
```
GET http://localhost:8080/students/<id>/classes
```

### Course/Class endpoints
```
POST http://localhost:8080/classes/
{
    "code": "c-1",
    "title": "Class-1",
    "description": "Class-1 description"
}
```    
Edit
```
PUT http://localhost:8080/students/
{
    "code": "c-1",
    "title": "Class-1",
    "description": "Class-1 description"
}
```    
Delete
```
DELETE http://localhost:8080/classes/<code>
```
Search (omit matrix parameters if you want to retrieve all courses)
```
GET http://localhost:8080/classes/code=c-1;title=Class-1
```
List of students
```
GET http://localhost:8080/classes/<code>/students
```

### Registration endpoint

```
POST http://localhost:8080/registrations/

{
"studentId": "1",
"courseCode": "c-2"
}
```