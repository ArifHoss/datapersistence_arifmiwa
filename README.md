## Assigment 2 -Create a database and access it.-

Development Environment Setup
Before starting, make sure you have the following tools installed:



 • Intellij Ultimate

 • Postgres and PgAdmin

 • Java 17

 • Postgres SQL driver dependency

## Description

This assignment deals with two different tasks.
```
A) SQL scripts to create database
  
B) Reading data with JDBC

```

## A)SQL scripts to create database: SuperheroesDb
***
***
###  The SQL scrips are saved The SQL scrips are saved  into the same repo for task B under the resources into the same repo for task B under the resourses
***
***

-  Create several scripts which can be run to create a database, setup some tables in the database, add
   relationships to the tables, and then populate the tables with data
-   Create a database in PgAdmin called Superheroes. All subsequent scripts are to be run from there
***
### 1. Tables
- There are three main tables you need to create.
1. Superhero has: Autoincremented integer Id, Name, Alias, Origin.
2. Assistant has: Autoincremented integer Id, Name.
3. Power has: Autoincremented integer Id, Name, Description.

* REQUIREMENT: Create a script called 01_tableCreate.sql that contains statements to create each of these tables and
setup their primary keys.



***
### 2. Table relationships
- One Superhero can have multiple assistants, one assistant has one superhero they assist.
- One Superhero can have many powers, and one power can be present on many Superheroes.

* REQUIREMENT: Create a script called 02_relationshipSuperheroAssistant.sql that contains statements to ALTER any
  tables needed to add the foreign key and setup the constraint to configure the described relationship between
  Superhero and assistant.
  Finally, to setup the Superhero-power relationship, we need to add a linking table. The name of this table is up to you
  but should be based on convention. This table is there purely for linking, meaning it needs to contain only two fields,
  which are both foreign keys and a composite primary key.
* REQUIREMENT: Create a script called 03_relationshipSuperheroPower.sql that contains statements to create the linking
  table. This script should contain any ALTER statements needed to set up the foreign key constraints between the linking
  tables and the Superhero and Power tables.




***


### 3. Table relationships

- A user may only view this page if they are currently logged into the app. 
- Translation-Button: The user types in the input box at the top of the page and click the button to the right of the input box to translate the word to sign language.Tha sign language will be displayed the translation-box 
- Translations must be stored using the API.

***
### 4.  Inserting data
- populate the database using INSERT statements. Firstly, we should add superheroes.
*  REQUIREMENT: Create a script called 04_insertSuperheroes.sql that inserts three new superheroes into the database.
  Then we need to add assistant data.
*  REQUIREMENT: Create a script called 05_insertAssistants.sql that inserts three assistants, decide on which superheroes
  these can assist.
  Finally, we need to add powers. This requires you to add the powers first, then associate them with Superheroes.
*  REQUIREMENT: Create a script called 06_powers.sql that inserts four powers. Then the script needs to give the
  superheroes powers. Try have one superhero with multiple powers and one power that is used by multiple superheroes,
  to demonstrate the many-to-many.


***
### 5  Inserting data
- Pick one superhero to update their data.

- REQUIREMENT: Create a script called 07_updateSuperhero.sql where you can update a superheroes name. Pick any
superhero to do this with.


***

### 6  Deleting data

- Referential integrity will stop us from deleting any records which have got relationships used. Meaning, a superhero
  cannot be deleted if it is used in the assistant table.
* REQUIREMENT: Create a script called 08_deleteAssistant.sql where you can delete any assistant. You can delete that
  assistant by name (his name must be unique to avoid deleing multiple assistants), this is done to ease working with
  autoincremented numbers – my PC may skip a number or two.

***

## B)Reading data with JDBC : Chinook Database
***
***
### *** The Main class navigates our functionalities from 1 to 9***
***
***

#### To set up the development environment, follow these steps:
1) Create the Chinook database in PgAdmin by running the provided SQL script.
2) Add the PostgreSQL JDBC driver dependency to your project.
3) Set up the database connection in your Spring configuration file.

***
### 1 Functionality
- The following functionality is provided for customers in the Chinook database:
1) Read all customers
2) Read a specific customer by ID
3) Read a specific customer by name (using the LIKE keyword)
4) Return a page of customers (using the SQL limit and offset keywords)
5) Add a new customer
6) Update an existing customer
7) Return the country with the most customers
8) Return the customer with the highest total spend (based on invoice totals) 
9) Return the most popular genre for a given customer (based on track counts)

***
### 2 Classes

- The following classes have been created to represent the data structures used in the application:

1) Customer
2) CustomerCountry
3) CustomerSpender
4) CustomerGenre

These classes are located in the Models folder and are used to return the required data in a structured format, rather than a formatted string.

***
### 3 Repositories

-  To implement the repository pattern in this assignment.
- Project have a generic CRUD parent, then one for customer. All the functionality have in the single customer repository
- The repositories should be placed in a repository package. 
- Create a ApplicationRunner class to test the repository.
***