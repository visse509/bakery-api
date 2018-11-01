This is task is implemented in Java using Springboot.

To run the application:
1. build the jar: mvn clean install
2. in a terminal run.sh will start the springboot application.

To test the application:
1. in a browser, navigate to: http://localhost:8080/swagger-ui.html#/package-controller/packagesUsingPOST
2. Press the "Try it out" in the top right
3. Modify the values of the request body, to test the case provided in the assignment, use the request body:
{
  "items": {
    "VS5": 10,
    "MB11": 14,
    "CF": 13
  }
}

4. The response will be shown below.
