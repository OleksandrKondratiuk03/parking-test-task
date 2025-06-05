FROM openjdk:23

COPY build/libs/parking-test-task-0.0.1-SNAPSHOT.jar parking-test-task-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar","parking-test-task-0.0.1-SNAPSHOT.jar"]