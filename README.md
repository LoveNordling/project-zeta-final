# Primal

OSPP (1DT096) 2018 - Project zeta

Project in the course Operating systems and process oriented programming (1DT096) spring 2018, Uppsala university.

Primal is a simulation of an ecosystem. More specifically it is a simulation of a savana showing the most basic interaction between animals and their surroundings. The simulation is implemented in Java, running on concurrent threads for time optimization.

For a more detailed description of the project here is our [project proposal](./docs/project-proposal.pdf).

## Getting started

#### Dependencies
To run this project you will need:  
A Java distribution.  
The build tool [Gradle](https://gradle.org/install/).  

#### Running
Compile the project by navigating to the root folder in a terminal and type **gradle build**.  
If the compilation worked as planned, navigate to */build/libs* where the file *Primal.jar* should have appeared. Now to run the program type **java -jar Primal.jar**.

#### Testing


## Structure

**src**  
This folder contains all vital code and all tests.

**src/org/primal/java**  
Root folder for the java code.
- /behaviour
- /entity
- /graphics
- /map
- /responses
- /tile

**src/test**  
This folder conatains all tests.

**guidelines**  
Guidelines for working on the project.

**docs**  


**meta**  
Contains group contract and a presentation of the members of the project.

## Färdigställ 

- Allt eftersom projektet fortskrider kan ni lägga till fler rubriker i detta
  dokument med kompletterande information.
- Färdigställ dokumentet [meta/gruppkontrakt.md](./meta/gruppkontrakt.md).
- Färdigställ dokumentet [meta/medlemmar.md](./meta/medlemmar.md).
- Tag bort alla stycken markerade med **TODO** och **INFO**. 
- Tag bort hela detta avsnitt, dvs tag bort avsnittet **Färdigställ**.
