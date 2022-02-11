# REST API using SpringBoot (Performed CRUD Endpoints)
**Concepts used :- Java, Springboot, Hibernate, Spring JPA, MySQL Workbench, Postman (API Testing Tool), Eclipse IDE** <br/>
## Overview 
1.	Importing the generated Project from Spring Initializr (start.spring.io). Add Dependecy such as Spring Web and Spring Data JPA.
2.	Connection with MYSQL Database. Specify connection fields in the application.properties file.
3.	Created Team and Player models. After that repository and controller packages have been created for each model.
4.	Implemented CRUD Endpoints. Tested each Endpoints using Postman Tool (API testing tool).
________________________________________
## Get Started

## Generate project 
1.	Select Project option as Maven Project, Language as Java, SpringBoot as 2.6.3. Write the Artifact for the project (it’s considered as the name of the project). 
2.	Select Packaging as Jar (will create jar folder for the project) and Java Version as 8.
3.	Add Dependencies :– <br/>
•	Spring Web ( used to build web and RESTful applications) <br/>
•	Spring Data JPA (Persist data)
4.	Finally Generate Project. Extract the generated file in the computer file system. Then import the extracted folder in Eclipse IDE.
________________________________________

## Connection with MySQL Database
1.	Add mysql-connector-java dependency in the pom.xml file (Project Object Model). You can directly go to mvnrepository.com and as per the connector versions, copy the dependency situated in maven tab and paste it in the pom.xml file. <br/>
2.	application.properties (situated in resource) – Specify the following required fields for the connection.<br/>
```
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
spring.datasource.username=root
spring.datasource.password=tiger
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect

```
( jdbc:mysql – indicate that java database connectivity by using mysql database. localhost:3306 – it means that server is running locally with server port number 3306. Studentdb is the Database name created in MySQL Workbench.) <br/>
( username of the MySQL database ) <br/>
( password of the MySQL database ) <br/>
( hibernate dialect is the bridge between Java and SQL ) <br>
_____________________________________
## Implementation of Models Package <br/>
There are two models Team and Player. These models have One to Many relationship. Following is the ER-diagram. <br/>
<img width="689" alt="er" src="https://user-images.githubusercontent.com/66257068/151663826-dd739697-7e3f-4926-a226-4d1c644c664d.png">
<br/>
**Team Model (Class)** – 
1.	@Entity – this annotation is used before class declaration. This annotation will create the table in the database by referring to className. If you want custom table name then user @Table(name=”tableName”) annotation. It will map entity with specified tableName.<br/>
2.	@Id – used to generate primary key in the database table<br/>
3.	@OneToMany – Declare above the class object declaration. Indicates that one team -> many players. Used Cascade means if team deleted then its players will also gets deleted.<br/>
4.	@JoinColumn (name=”team_id”) – will join the column by using team_id field declared inside the player table. It is also considered as the Foreign key.<br/>
5.	Generated getters and setters for data members of Team class.<br/>

**Player Model (Class)** –
1.	@Entity is mapped with table name using @Table(name=”player”).<br/>
2.	Create the paramatarized contructor containing the each data member of the class.<br/>
3.	Generated getters and setters for data members of Player class.<br/>
________________________________________
## Implementation of Repository package <br/>
This package contains the interface used for player and team models.<br/>
**PlayerRepository (Interface)** – <br/>
1.	@Repository – this annotation indicates that the given interface provides the mechanism to perform CRUD operations on objects.
Declare this annotation before the interface declaration.<br/>
2.	PlayerRepository extends the properties of JpaRepository and contains Player and Long as parameters.<br/>


**TeamRepository (Interface)** – <br/>
1.	@Repository – declare this annotation before the interface declaration. This annotation indicates that the given interface provides the mechanism to perform CRUD operations on objects.<br/>
2.	TeamRepository extends the properties of JpaRepository and contains Team and Integer as parameters.<br/>
________________________________________
## Implementation of Controller Package for Endpoints.<br/>
This package consists of classes required to implement the endpoints.<br/>

**TeamController (Class)** – <br/>
1.	@RestController – Declare this annotation above the class declaration. It indicates that the class is used to create RESTful web services. <br/>
2.	@Autowired – this annotation is used to Inject the TeamRepository (Interface) object TeamController (Class).<br/>
### Endpoints (Team) <br/>
1. **Create Team (By using POST Request)** <br/>
```
@PostMapping("/create/team")
	//mapping team details from request to bean (@RequestBody)
	public String createTeam(@RequestBody Team team)
	{
		//getting all the values (using getter methods)
		//enter column names and its values
		//if skip any column then will insert null value in that column
		team.getId();
		team.getName();
		team.getLocation();
		team.getCreated_at();
		team.getUpdated_at();
		//save the records
		teamRepository.save(team);
		//displaying created message
		return "team created successfully";
	}
```
**Explanation**-<br/>
a]. @PostMapping annotation maps the HTTP POST request into method createTeam().<br/>
b]. String declared inside the @PostMapping annotation tells that when you hit the URL as http://localhost:3306/create/team <br/>
  by using POST request in POSTMAN Tool, then createTeam() method will get called. @RequestBody annotation allows us to retrieve the requested body.<br/>
c]. Getting all the values using getter() methods. Saving the object of Team model into database by using TeamRepository bean.<br/>
<br/>
**Result (Postman Tool)**
<br/>
![Screenshot (234)](https://user-images.githubusercontent.com/66257068/151600202-9edbf9aa-3b77-4f84-8e21-82e5ffdadd0c.png)
<br/>
Make sure to select the Body option -> raw option -> json (application/json) -> Finally enter records <br/><br/>
**2. Find Team By Id (By using Get Request)**-<br/>
```
@GetMapping("/team/{id}")
	public Team getTeamById(@PathVariable int id)
	{
		Optional<Team> teamList = teamRepository.findById(id);
		if(!teamList.isPresent()) {
			throw new TeamNotFoundException();
		}
		//get all the fields
		return teamList.get();
	}
```
**Explanation** -<br/>
a]. @GetMapping annotation maps the HTTP GET request into method getTeamById().<br/>
b]. Parameter declared inside the @GetMapping annotation tells that when you hit URL as http://localhost:3306/team/1 (here 1 is the id) <br/>
  by using GET request in Postman, getTeamById() method will get called. @PathVariable annotation is used to handle variables in request URI mapping.<br/>
c]. findById() is the method implemented in JpaRepository, which find the element by id (id declared as parameter).<br/>
d]. If the specified id is not present in the database then it will throw the Custom Exception named TeamNotFoundException().<br/>
  This exception is implemented in exception package. Finally returned the object of Team clcass using get() method.<br/>
<br/>
**Result (Postman)** <br/>
<br/>
![Screenshot (235)](https://user-images.githubusercontent.com/66257068/151604943-4e4712b2-a658-4221-9fdb-1f96f35634c9.png) 
<br/>
Select GET Request -> Enter URL (http://localhost:3306/team/4) -> Click on Send <br/><br/>
**3. Update Team Information (By using PUT Request)** -<br/>
```
  //setting (select options)------> body - raw - JSON (application) - Enter records
	//first enter all the fields (of specific id) in the postman then update whichever the field you want
	@PutMapping("/update/team/{id}")
	public String updateTeam(@PathVariable int id,@RequestBody Team team)
	{
		Team teamlist = teamRepository.findById(id).orElse(null);
		teamlist.setId(team.getId());
		teamlist.setName(team.getName());
		teamlist.setLocation(team.getLocation());
		teamlist.setCreated_at(team.getCreated_at());
		teamlist.setUpdated_at(team.getUpdated_at());
		//saving the updated records
		teamRepository.save(teamlist);
		//displaying updated message
		return "updated successfully";
	
	}
```
**Explanation** -<br/>
a]. @PutMapping annotation maps the HTTP PUT request into updateTeam() method.<br/>
b]. Parameter declared inside this annotation indicates that when you hit URL as http://localhost:3306/update/team/4 (4 is the id) <br/>
  by using PUT request in Postman, updateTeam() method will get called. @PathVariable is used to handle id variable in URI mapping.<br/>
  Here @RequestBody is used to retrieve requesed body of Team object.<br/>
c]. first step is to check whether the entered id is present or not. If id is not present then it will display null values.<br/>
 Else if id is present, it will set all the values by getting all the entered values in teamList object.<br/>
 Finally updated records have been saved using teamlist object into database.<br/>
 <br/>
 **Result (Postman)** -<br/>
 <br/>
 ![Screenshot (236)](https://user-images.githubusercontent.com/66257068/151644619-fe98a663-efc5-44ba-99ff-97fbfc2adf9c.png)
<br/><br/>
Make sure to select the Body option -> raw option -> json (application/json) -> Finally enter records <br/>
Enter the already created records first then change the fields you want to update<br/>
Select PUT Request -> Enter URL (http://localhost:8080/update/team/4) -> Click on Send <br/><br/>
**4. Delete Team (By using DELETE request)** -<br/>
```
@DeleteMapping("/delete/team/{id}")
	public String deleteTeam(@PathVariable int id)
	{
		teamRepository.deleteById(id);
		//return deleted message
		return "deleted successfully";
	}
```
**Explanation** -<br/>
a]. @DeleteMapping annotation maps the HTTP DELETE request into deleteItem() method.<br/>
b]. when you enter the URL as http://localhost:3306/delete/team/3 (3 is the id), then deleteTeam() method will get called with parameter as id<br/>
c]. deleteById() method is used to delete the entity specified as id <br/>
<br/>
**Result (Postman)** -<br/><br/>
![Screenshot (237)](https://user-images.githubusercontent.com/66257068/151657070-f2c2cbda-0211-4643-9d16-717966a5025c.png)
<br/><br/>
**5. List of Team (By using GET request)** - <br/>
```
@GetMapping("/teams")
	public List<Team> listofTeam()
	{
		return teamRepository.findAll();
	}
```

**Explanation** -<br/>
a]. @GetMapping maps the HTTP GET request into listofTeam() method.<br/>
b]. when you enter URL as http://localhost:3306/teams, listofTeam() method will get called.<br/>
c]. finaAll() method of JPA will find all the entity from teamRepository bean.<br/>
**Result (Postman)** -<br/><br/>
![Screenshot (238)](https://user-images.githubusercontent.com/66257068/151657602-db290dee-396f-49db-8057-ce7182d340d1.png)
<br/><br/>
**6. Find Team By Player Id (By using GET request)** - <br/>
```
@GetMapping("/teamByPlayerId/{id}")
	public Team teamByPlayerId(@PathVariable Long id)
	{
		return teamRepository.findByPlayerId(id);
	}
```
**Explanation** -<br/>
a]. @GetMapping maps the HTTP GET request into teamByPlayerId() method<br/>
b]. when you enter URL http://localhost:3306/teamByPlayerId/2, teamByPlayerId() method will get called.<br/>
c]. Declare the findByPlayerId() method in TeamRepository Interface which extends the JpaRepository.<br/>
d]. Jpa will automatically find the team based on player id. 
<br/><br/>
**Result (Postman)** -<br/><br/>
![Screenshot (239)](https://user-images.githubusercontent.com/66257068/151657945-f91dbe08-6172-4894-8074-0216ba9df840.png)
<br/><br/>
**PlayerController (Class)** – <br/>
1.	@RestController – Declare this annotation above the class declaration. It indicates that the class is used to create RESTful web services. <br/>
2.	@Autowired – this annotation is used to Inject the PlayerRepository (Interface) object PlayerController (Class).<br/>
### Endpoints (Player) <br/>
**1. Create Player (By using POST request)** -<br/>
```
@PostMapping("/create/player")
	public String createPlayer(@RequestBody Player player)
	{
		//getting all the values (using getter methods)
		player.getId();
		player.getAge();
		player.getCreated_at();
		player.getTeam_id();
		player.getUpdated_at();
		//save records
		playerRepository.save(player);
		//displaying message
		return "player created successfully";
	}
```
**Explanation** -<br/>
a]. @PostMapping annotation maps HTTP POST request into createPlayer() method.<br/>
b]. String parameter declared inside the @PostMapping annotation tells that when you hit the URL as http://localhost:3306/create/player <br/>
  by using POST request in POSTMAN Tool, createPlayer() method will get called. @RequestBody annotation allows us to retrieve the requested body.<br/>
c]. Getting all the values using getter() methods. Saving the object of Player model into database by using PlayerRepository bean.<br/>
<br/>
**Result (Postman)** -<br/><br/>
![Screenshot (240)](https://user-images.githubusercontent.com/66257068/151658767-68c099fa-1594-40cf-aced-08b31a83eaaa.png)
<br/>
Select Body option -> raw option -> json application option -> enter records -> send (POST request) 
<br/>
**2. Find Player By TeamId (By using GET request)** -<br/>
```
@GetMapping("/player/{id}")
	public Player getPlayerById(@PathVariable Long id) {
		
		Optional<Player> player = playerRepository.findById(id);
		if(!player.isPresent()) {
			//if not present then will throw custom exception
			throw new PlayerNotFoundException();
		}
		return player.get();
	}
```
**Explanation** -<br/>
a]. @GetMapping annotation maps the HTTP GET request into method getPlayerById().<br/>
b]. Parameter declared inside the @GetMapping annotation tells that when you hit URL as http://localhost:3306/player/1 (here 1 is the id) <br/>
  by using GET request in Postman, getPlayerById() method will get called. @PathVariable annotation is used to handle variables in request URI mapping.<br/>
c]. findById() is the method implemented in JpaRepository, which find the element by id (id declared as parameter).<br/>
d]. If the specified id is not present in the database then it will throw the Custom Exception named PlayerNotFoundException().<br/>
  This exception is implemented in exception package. Finally returned the object of Player class using get() method.<br/>
<br/>
**Result (Postman)** -<br/><br/>
![Screenshot (241)](https://user-images.githubusercontent.com/66257068/151659213-b289784c-932e-4508-adbf-ee628f2ee6f9.png)
<br/>
select GET -> http://localhost:3306/player/3 -> send <br/><br/>
**3. Update Player Information (By using PUT request)** -<br/>
```
@PutMapping("/update/player/{id}")
	public String updatePlayer(@PathVariable Long id, @RequestBody Player player)
	{
		//first need to find the player by
		//if found then will move forward to update, else display null
		Player playerlist = playerRepository.findById(id).orElse(null);
		playerlist.setId(player.getId());
		playerlist.setAge(player.getAge());
		playerlist.setCreated_at(player.getCreated_at());
		playerlist.setName(player.getName());
		playerlist.setTeam_id(player.getTeam_id());
		playerlist.setUpdated_at(player.getUpdated_at());
		//save the records
		playerRepository.save(playerlist);
		//display message
		return "updated successfully";
	}
```
**Explanation** -<br/>
a]. a]. @PutMapping annotation maps the HTTP PUT request into updatePlayer() method.<br/>
b]. Parameter declared inside this annotation indicates that when you hit URL as http://localhost:3306/update/player/4 (4 is the id) <br/>
  by using PUT request in Postman, updatePlayer() method will get called. @PathVariable is used to handle id variable in URI mapping.<br/>
  Here @RequestBody is used to retrieve requesed body of Player object.<br/>
c]. first step is to check whether the entered id is present or not. If id is not present then it will display null values.<br/>
 Else if id is present, it will set all the values by getting all the entered values in playerList object.<br/>
d]. Finally updated records have been saved using playerlist object into database.<br/>
 <br/><br/>
 **Result (Postman)** -<br/><br/>
 ![Screenshot (242)](https://user-images.githubusercontent.com/66257068/151659506-72695694-ba50-4ea0-bd4c-67f3f9ebf0fa.png)
<br/>
Make sure to select the Body option -> raw option -> json (application/json) -> Finally enter records <br/>
Enter the already created records first then change the fields you want to update<br/>
Select PUT Request -> Enter URL http://localhost:8080/update/player/3 -> Click on Send <br/><br/>
**4. Delete Player (By using DELETE request)** -<br/><br/>
```
@DeleteMapping("/delete/player/{id}")
	public String deletePlayer(@PathVariable Long id)
	{
		playerRepository.deleteById(id);
		//return deleted message
		return "deleted successfully";
	}
```
**Explanation** -<br/>
a]. @DeleteMapping annotation maps the HTTP DELETE request into deletePlayer() method.<br/>
b]. when you enter the URL as http://localhost:3306/delete/player/3 (3 is the id), then deletePlayer() method will get called with parameter as id<br/>
c]. deleteById() method is used to delete the entity specified as id <br/>
<br/>
**Result** -</br><br/>
![Screenshot (243)](https://user-images.githubusercontent.com/66257068/151659674-2263bc0b-dd3d-4f18-b796-05b0096c0a8d.png)
<br/><br/>
**5. List of Players (by using GET request)** -<br/>
```
@GetMapping("/players")
	public List<Player> listofPlayers()
	{
		return playerRepository.findAll();
	}
```
**Explanation** - <br/>
a]. @GetMapping maps the HTTP GET request into listofTeam() method.<br/>
b]. when you enter URL as http://localhost:3306/players, listofPlayers() method will get called.<br/>
c]. finaAll() method of JPA will find all the entity from playerRepository bean.<br/>
<br/>
**Result** -<br/><br/>
![Screenshot (245)](https://user-images.githubusercontent.com/66257068/151660088-33703d7b-e3ef-40a0-ad7c-c64d6b1461ca.png)
<br/><br/>
**6. Find Players By Id (by using GET request)** -<br/>
```
@GetMapping("/teamplayers/{id}")
	public List<Player> playersByTeamId(@PathVariable("id") Long teamId)
	{
		return playerRepository.findByTeamId(teamId);
		
	}
```
**Explanation** -<br/>
a]. @GetMapping maps the HTTP GET request into playersByTeamId() method<br/>
b]. when you enter URL http://localhost:3306/teamplayers/2, playersByTeamIdId() method will get called.<br/>
c]. Declare the findByTeamId() method in PlayerRepository Interface which extends the JpaRepository.<br/>
d]. Jpa will automatically find the team based on player id. 
<br/><br/>
**Result** -</br><br/>
![Screenshot (246)](https://user-images.githubusercontent.com/66257068/151660356-77eb90c4-19c4-4e14-9c4f-0e5e67fb1807.png)



