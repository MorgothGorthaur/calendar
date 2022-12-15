# calendar
<h2>Description:</h2>
  <div>
  Calendar is a simple RESTful Java Spring Boot application to delegate events to participants.
  </div>
  <div>
  Spring Data JPA is used to access data. The program implements two entities - Participant and Event with ManyToMany biderectional relationship.
The Participant entity has the following fields: first name, second name , status (Enum with fields Active and Removed), email, password, role (user or admin) and the list of Events.
The Event entity has fields: start time, end time and the list of Participants.
The program allows you to get the list of participants (with Active status), add new participant and remove/modify your participant (you must authorize at first).
You can also get a list of events (those with actual dates) for your participant and add/modify/remove them. You also can invite new participant to your event (you must know it email for it).
The same Event may have multiple participants, so removing an event for one of them does not remove for the others.
In the future I plan to add ability to admin work - removing or ban participants.
</div>
<h2>Used Technologies:</h2>
 <div>
 Back-end: Spring Boot, Spring Web, Spring Data JPA, Spring Security, JWT, ModelMapper, Hibernate Validator, MariaDB, Mockito, Lombok.
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
 <h2> Requirements:</h2>
 <div> Java 17 </div>
 <div> MariaDB </div>
 <div> Maven </div>
<h2>Run:</h2> 
  <h3>first way:</h3>
  <div>
    <div>go to the project directory.
      <div>run: mvn clean package</div>
      <div>then: java -jar target/calendar-0.0.1-SNAPSHOT.jar</div>
      <div>go to the *project directory*/react/calendar</div>
      <div>run: npm install</div>
      <div>then: npm start</div>
  </div>
  <h3>second way:</h3>
    <div>
    <div>go to the project directory.</div>
    <div>then run: mvn clean package</div>
    <div>run: docker build -t spring-calendar .</div>
    <div>then: docker run  -p 8080:8080 -t spring-calendar</div>
    <div>go to the *project directory*/react/calendar</div>
    <div>run: docker build -t react-calendar .</div>
    <div>then: docker run -p 5001:3000 -t react-calendar</div>
    </div>
   <br/>
  <div>You also need to provide acces to your MariaDB database. </div>
  <div>You must set your database url, password and username to *project directory*/src/main/resources/application.properties file</div>
