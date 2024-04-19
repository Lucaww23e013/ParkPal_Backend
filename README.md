# ParkPal Backend

## Overview
ParkPal Backend is the server-side component for the ParkPal service. It is built on Java using the Spring Boot framework, and it utilizes MariaDB and H2 databases. The application is designed to provide backend functionality for the ParkPal service, facilitating features related to managing parking information.

## Table of Contents
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Workflow](#workflow)
      - [Creating new Issues](#creating-new-issues)
      - [Working on a new Issue](#working-on-a-new-issue)
      - [Commits](#commits)
- [Usage](#usage)
- [Configuration](#configuration)
- [DB Setup](#db-setup)
- [Features](#features)
- [UML Diagram](#uml-diagram)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgements](#acknowledgements)
- [Contact](#contact)

## Getting Started
To get started with ParkPal Backend, follow the instructions below:

### Prerequisites
Ensure you have the following installed:

- Java (version 21)
- Spring Boot (version 3.2.2)
- MariaDB 
- H2 Database 
- Minio

### Installation
1. Clone the repository: `git clone https://github.com/your-username/parkpal-backend.git`
2. Navigate to the project directory: `cd parkpal-backend`
3. Build the project: `./mvnw clean install`
4. Run the application: `./mvnw spring-boot:run`

## Workflow
Establishing a consistent and efficient workflow serves to benefit productivity.

### Creating new Issues
1. From Repo go to Projects
2. Select the Parkpal Project
3. In the Backlog section have ur cursor in add item
4. Click the plus icon again to create a new issue
       - Issue message should start with "Ppp:" and a short message to clearly communicate the task
       - use dashes for spaces
       - a issue number will be generated automaticly
   
### Working on a new Issue
1. Move Issue to "in Progress" in the Kanbanbaord
2. In the Issue click on the 3 dots to open in a new tab
3. In the left menu assigne it and create a new branch for the Issue
4. Use this as branch name "[task]/[issue-number]-ppp-[issue-title]"
       - for branch type -> for [task] put in "feature" or "bugfix"
6. Use {git fetch origin [branch-name]} to get branch to local repo (or use downwards arrow in branch section)
7. Use {git checkout [branch-name]} to work on ur Issue in ur issue-branch

### Commits
1. You can do what ever you want on your own branch
2. Best practice: every single commit is one new feature for your feature
3. Commit and push every day, task for task and save your work
4. Commit messages should start with "Ppp-[issue-number]: [short-message-title]

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

sql_script = "INSERT INTO country (country_Id, name, iso2Code) VALUES\n"

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

