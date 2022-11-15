[[_TOC_]]

# Faircorp Smart Building Manager API

Faircorp is an application for managing smart buildings and its related entities such as Rooms, Heaters and Windows. The Faircorp app was built using Java's web Framework "Spring Boot". The dependencies used include Spring Security, Spring JPA, and more. In addition, the app is tested using testing libraries such as Junit, and Mockito.

The Faircorp app is currently deployed on clevercloud with the following url:

```
http://app-605073a9-f8f3-4f02-bc50-6017adc9bfbe.cleverapps.io/
```

## API Information

**User Types**:

There are 2 types of user role, the "admin" and the "user". To sign in with admin role, we have to use the username "`admin`" and password "`userpass`". To sign in with a normal user role, we have to use the username "`user`" and password "`userpass`".

### API EndPoints

The API route can only be accessed by a user with admin role. Once on the signed in, an admin has access to the Entities such as Building, Rooms, Heaters and Window. They can also make chnages and add new one as needed. Below are some useful endpoints.

```
http://app-605073a9-f8f3-4f02-bc50-6017adc9bfbe.cleverapps.io/api/buildings

http://app-605073a9-f8f3-4f02-bc50-6017adc9bfbe.cleverapps.io/api/rooms

http://app-605073a9-f8f3-4f02-bc50-6017adc9bfbe.cleverapps.io/api/heaters

http://app-605073a9-f8f3-4f02-bc50-6017adc9bfbe.cleverapps.io/api/windows
```

Some interesting functions include being able to manage related entities by building and also by rooms. Thus, an admin could delete or turn on all the heaters in a building from the Building Endpoint.

### API testing

To have an hands-on experience of the API, The API docs can be accessed via the [Swagger Documentation](https://app-605073a9-f8f3-4f02-bc50-6017adc9bfbe.cleverapps.io/swagger-ui/index.html#/building-controller/findAllUsingGET)

### Other Info

```
Environment variables: Java 17

Build Tool: Gradle

Database: H2
```
