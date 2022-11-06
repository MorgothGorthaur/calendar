# calendar
<h2>Description:</h2>
  <div>
  Calendar is a simple RESTful Java Spring Boot application to delegate events to participants.
  </div>
  <div>
  Spring Data JPA is used to access data. The program implements two entities - Participant and Event with ManyToMany biderectional relationship.
The Participant entity has the following fields: first name, second name , status (Enum with fields Active and Removed) and the list of Events.
The Event entity has fields: start time, end time and the list of Participants.
The program allows you to get the list of participants (with Active status) and add/remove/modify them.
You can also get a list of events (those with actual dates) for each participant and add/modify/delete them.
The same Event may have multiple participants, so removing an event for one of them does not remove for the others.
</div>
<h2>Used Technologies:</h2>
 <div>
 Back-end: Spring Boot(Spring Web, Spring Data JPA, Validation), MariaDB, Mockito, Lombok.
  </div>
  <div>
 Front-end: ReactJS.
 </div>
 <div>
  Server Build: Maven.
  </div>
  <div>
 Client Build: npm.
 </div>
<h2>Run:</h2> 
  <h3>first way:</h3>
  <div>
    go to the project directory.
    run: ./mvnw clean package
    then: java -jar calendar-0.0.1-SNAPSHOT.jar
    go to the *project directory*/react/calendar
    run: npm install
    then: npm start
  </div>
  <h3>second way:</h3>
    <div>
    <>go to the project directory.</>
    <>run: docker build -t spring-calendar .</>
    <>then: docker run  -p 8080:8080 -t spring-calendar</>
    <>go to the *project directory*/react/calendar</>
    <>run: docker build -t react-calendar .</>
    <>then: docker run -p 5001:3000 -t react-calendar</>
    </div>
