To start the application:
CMD:
.\gradlew.bat bootJar
docker build -t parking-app .
docker run -p 8080:8080 parking-app

API:
Method: POST 
URL: localhost:8080/users/super-admin (create super admin. only for testing purposes. Only super admin can create admins, admins can create users.)
body:
  {
    "username":"",
    "password":""
  }

Method: POST 
URL: localhost:8080/users (create a user.)
body:
  {
    "username":"",
    "password":"",
    "userRole":"" (USER, ADMIN, SUPER_ADMIN)
  }
Headers: Authorization (Bearer token)

Method: GET 
URL: localhost:8080/auth/login (login to get token. Any role.)
body:
  {
    "username":"",
    "password":""
  }

Method: POST 
URL: localhost:8080/lots (create parking lot. ADMIN, SUPER-ADMIN)
body:
  {
    "name":""
  }
Headers: Authorization (Bearer token)

Method: DELETE 
URL: localhost:8080/lots/{lotId} (delete parking lot. ADMIN, SUPER-ADMIN)
Headers: Authorization (Bearer token)

Method: POST 
URL: localhost:8080/floors (create floor. ADMIN, SUPER-ADMIN)
body:
  {
    "parkingLotId":""
  }
Headers: Authorization (Bearer token)

Method: DELETE 
URL: localhost:8080/floors/{floorId} (delete floor. ADMIN, SUPER-ADMIN)
Headers: Authorization (Bearer token)

Method: POST 
URL: localhost:8080/slots (create slot. ADMIN, SUPER-ADMIN)
body:
  {
    "type":"", (COMPACT, LARGE, MOTORCYCLE)
    "floorId":""
  }
Headers: Authorization (Bearer token)

Method: PUT 
URL: localhost:8080/slots/{slotId} (change slot availability. ADMIN, SUPER-ADMIN)
Headers: Authorization (Bearer token)

Method: DELETE 
URL: localhost:8080/slots/{slotId} (delete slot. ADMIN, SUPER-ADMIN)
Headers: Authorization (Bearer token)

Method: POST 
URL: localhost:8080/vehicles/check-in (check-in a vehicle. any role)
body:
  {
    "vehicleType":"", ( CAR, MOTORCYCLE, TRUCK)
    "licencePlate":""
  }
Headers: Authorization (Bearer token)

Method: POST 
URL: localhost:8080/vehicles/check-out/{licencePlate} (check-out a vehicle. any role)
Headers: Authorization (Bearer token)

Method: GET 
URL: localhost:8080/auth/{lotId} (get all records in parking lot. Any role.)
Headers: Authorization (Bearer token)




