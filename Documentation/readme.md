# ParkPal Backend

## Overview
ParkPal Backend is the server-side component for the ParkPal service. It is built on Java using the Spring Boot framework, and it utilizes MariaDB and H2 databases. The application is designed to provide backend functionality for the ParkPal service, facilitating features related to managing parking information.

## Table of Contents
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Features](#features)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgements](#acknowledgements)
- [Contact](#contact)

## Getting Started
To get started with ParkPal Backend, follow the instructions below:

### Prerequisites
Ensure you have the following installed:

- Java (version X.X.X)
- Spring Boot (version X.X.X)
- MariaDB (version X.X.X)
- H2 Database (version X.X.X)

### Installation
1. Clone the repository: `git clone https://github.com/your-username/parkpal-backend.git`
2. Navigate to the project directory: `cd parkpal-backend`
3. Build the project: `./mvnw clean install`
4. Run the application: `./mvnw spring-boot:run`

## Usage
ParkPal Backend provides the backend services for the ParkPal application. Ensure that the application is running, and you can then integrate it with the ParkPal frontend or test the functionality using API tools like Postman.

## Configuration
ParkPal Backend supports configuration through application properties. Customize the `application.properties` file to set database configurations, logging levels, and other parameters.

Example `application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mariadb://localhost:3306/parkpal
spring.datasource.username=springuser
spring.datasource.password=springpw

# Hibernate Configuration
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
spring.jpa.hibernate.ddl-auto=update
```

## DB Setup
For a new Setup of the Countries in the DB use this enclosed python Script to generate an SQL Query. 

```phyton
import pycountry
import uuid

countries = list(pycountry.countries)

sql_script = "INSERT INTO countries (country_Id, name, iso2Code) VALUES\n"

for country in countries:
    country_id = str(uuid.uuid4())  # Generating a UUID for country_Id
    country_name = country.name.replace("'", "''")  # Doubling single quotes
    sql_script += f"('{country_id}', '{country_name}', '{country.alpha_2}'),\n"

sql_script = sql_script.rstrip(',\n') + ';'

print(sql_script)
```
You might also need to install pycountry, if you haven't already.
```install pycountry
pip install pycountry
```

# Features

- **Create Events:** Users can create Events in different parks in Vienna. 
- **Join Events:** Users can join Events, that where created by others.
- **Event Overview:** Users get an Overview of the most recent Events.
- **Event Navigation:** Users can quickly navigate between different Events and change from Park to Park.
- **Upload Media:** Users can upload Media to their Events.
- **Create Accounts:** Users must create Accounts to create and join Events.
- **See User-History:** Users will be able to see their past activities including their created Events and the Events of others they joined.
- **Database Connectivity:** Utilizes MariaDB for production and H2 for testing, with JPA and Hibernate for data access.

# UML Diagram
The UML diagram may not render directly in Markdown. Please use a PlantUML-compatible tool or online platform to visualize the diagram.
```
@startuml 

entity User {
  - userID: String
  - salutation: String
  - username: String
  - firstName: String
  - lastName: String
  - email: String
  - password: String
  - AuthToken: String
  - country: Country
  - isAdmin: Boolean
  - joinedEvents: List<Event>
  - profilePicture: Picture
}

enum GenderCategory {
  OTHER
  FEMALE
  MALE
}

entity Event {
  - eventID: String
  - title: String
  - description: String
  - startTS: LocalDateTime
  - endTS: LocalDateTime
  - park: Park
  - creator: User
  - joinedUsers: List<User>
  - eventTags: List<EventTag>
  - eventPicture: List<Picture>
  - eventVideos: List<Videos>
}

entity EventTag {
  - eventTagID: String
  - name: String
}

entity Picture {
  - pictureId: String
  - user: User
}

entity Video {
  - videoId: String
  - user: User
}

entity Park {
  - parkID: String
  - name: String
  - description: String
  - parkAddress: Address
  - parkEvents: List<Event>
  - parkPictures: List<Picture>
  - parkVideos: List<Video>
}

class Address <<(V, #FF7700) value-object>> {
  - streetNumber: String
  - zipCode: String
  - city: String
  - country: Country
}

entity Country {
  - name: String
  - iso2Code: String
}

Event "1..1" -- User : created by
Event --> "1..n" Park : has <
Event -left- "n..m" User : joined By
EventTag "n..m" --* Event : has <
Picture "n..m" <-- Event
Video "n..m" <-- Event
User -- "1..n" Picture  : User create Picture
Park "1..n" -- Picture
User -- "1..n" Video  : User create Video
Park "1..n" -- Video
User -- Country
Address - Country
Park -left> "0..1" Address
User -left> GenderCategory

@enduml
```
# Contributing

If you'd like to contribute to ParkPal Backend, please contact the Team. Feel free to report issues or submit pull requests.

# License

This project is licensed under the MIT License.

# Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [MariaDB](https://mariadb.org/)
- [H2 Database](https://www.h2database.com/html/main.html)
- [Hibernate](https://hibernate.org/)
- [Pycountry]((https://pypi.org/project/pycountry/))

# Contact

For questions or support, please contact the ParkPal team at [ww23e018@technikum-wien.at](mailto:ww23e018@technikum-wien.at).

