# _Part 1-VersionControl With Git_

## Introduction to Part 1

This section of the repository corresponds to the part 1 of the first class assignment (CA1) of the DevOps course in the Switch 24-25 program. 
It is divided into multiple parts, each focusing on different aspects of version control and development practices.

## Table of Contents

- [Part 1.1 Development Without Branches](#part-11-development-without-branches)
  - [Getting Started](#getting-started)
  - [Goals and Requirements](#goals-and-requirements)
  - [Implementation](#implementation)

- [Part 1.2 Development Using Branches](#part-12-development-using-branches)
  - Goals and Requirements
  - Implementation

- [Alternative Solution](#alternative-solution)
  - Comparison of SVN and Git
  - Utilizing SVN for the Assignment

- [Conclusion](#conclusion)


## Part 1.1 Development Without Branches

This part explores the development process without using branches, 
highlighting the challenges and solutions encountered.

## Getting Started

To begin, I cloned an existing repository that contained the Tutorial React.js and Spring Data REST application, ensuring I had a local copy of the tutorial project. 
Next, I established my own repository to store class assignments and track all modifications under version control.

**Setting Up My Repository:** I created a dedicated directory on my local machine for the DevOps class assignments and initialized it as a Git repository. This marked the initial step in organizing my project workspace.

```shell
mkdir ~/myDevOpsRepo
cd ~/myDevOpsRepo
git init
```

**Adding the Tutorial Application:** To integrate the tutorial application into my project, I copied its files into my repository. 
This step guaranteed that all necessary resources were included within my version control setup.
The tutorial application can be found at: [Tutorial Repository](https://github.com/spring-attic/tut-react-and-spring-data-rest/tree/main/basic)

```shell
cp -r ~/tutorial ~/myDevOpsRepo
```

**Connecting to GitHub:** Once the tutorial application was in place, I linked my local repository to a new GitHub repository. 
This connection enabled me to push updates to a remote server, ensuring both backup and accessibility.

```shell
git remote add origin <repository-URL>
```

**Initial Commit:** After setting up the repository and verifying the presence of all files, I staged and committed the README file. 
This first commit, labeled "add readme," marked the beginning of my work on the assignments. 

```shell
git add .
git commit -m "add readme"
```
**Commit Strategy:** After the initial commit, I implemented a structured approach to committing changes. 
Each commit follows a clear and consistent logic, ensuring better tracking and maintainability of the project:

- `ci:` for continuous integration updates.
- `test:` for adding new tests or fixing existing ones.
- `feat:` for introducing new features.
- `fix:` for addressing and resolving bugs.

*Furthermore, all commits are referenced to GitHub issues, ensuring traceability and alignment with project tasks.*

**Pushing to Remote:** Lastly, I pushed my initial commit to the GitHub repository, officially initiating the version history for my assignments in a remote location.


```shell
git push -u origin master
```

## Goals and Requirements

- The first part of the assignment focuses on understanding and using basic version control operations without branching.
- Tasks involve setting up the project environment, making changes directly to the main branch, and committing those modifications.
- An essential requirement is to add a new feature (e.g., introducing a jobYears field to the Employee object) and ensuring proper version tagging, starting with an initial version and updating it after adding the new feature.
- The emphasis is on practicing commits, understanding commit history, and using tags for version management.

## Implementation

In the initial phase, all development was carried out on the master branch. The process involved:

1. **Copy the code of the [Tutorial React.js and Spring Data REST Application](https://github.com/spring-attic/tut-react-and-spring-data-rest/tree/main/basic) into a new folder named `CA1/part1`**

These commands were used to create a new directory and subdirectory named `CA1/part1`and copy the tutorial director recursively to `CA1/part1`:

```shell
mkdir CA1/part1
cp -r ~/myDevOpsRepo/tutorial ~/myDevOpsRepo/CA1/part1
```

2. **Commit the changes (and push them)**

Once the `CA1/part1` directory was set up with the Tutorial application, I proceeded to create a .gitignore file to prevent some files to be pushed into the central repository.
After that I commited these changes to the master branch with the following commands:

```shell
git add .
git commit -m "ci: add folder CA1/part1 with Tutorial"
git push
```

3. **Tagging the Repository to Mark the Application Version**

To track the application's version, I followed the versioning pattern specified in the assignment: major.minor.revision. 
The initial setup was tagged as v1.1.0, and this tag was then pushed to the remote repository using the following commands:

```shell
git tag v1.1.0 -m "Version 1.1.0"
git push origin v1.1.0
```

4. **Developing a New Feature: Adding a New Field to the Application**

The primary goal of this phase was to introduce a new feature by adding a jobYears field to the application. 
This field records the number of years an employee has been with the company.

Additionally, I implemented unit tests to ensure the correct creation of employees and validation of their attributes. 
These tests specifically enforce that:

- The **jobYears** field only accepts integer values.
- String-type fields cannot be null or empty.

To incorporate this new feature, the following files were modified:

- `Employee.java`

This Java class, which represents the employee model, was updated to include a new integer field named **jobYears**. 
The changes involved:

- Adding the **jobYears** field.
- Implementing getter and setter methods to maintain data encapsulation and controlled access.
- Validating all parameters to ensure data integrity.

Below are the key additions and modifications made to the `Employee` class to support this new functionality and ensure robust data validation:

```java
import org.springframework.data.annotation.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

/**
 * @author Ruben Rocha
 */
// tag::code[]
@Entity
public class Employee {

  private @Id
  @GeneratedValue Long id;
  private String firstName;
  private String lastName;
  private String description;
  private int jobYears;
  
  //Empty Constructor
  public Employee(){}
  
  //Constructor
  public Employee(String firstName, String lastName, String description, int jobYears) {

    if (isAttributeInvalid(firstName)) {
      throw new IllegalArgumentException("FirstName cannot be empty or null");
    }
    this.firstName = firstName;

    if (isAttributeInvalid(lastName)) {
      throw new IllegalArgumentException("LastName cannot be empty or null");
    }
    this.lastName = lastName;

    if (isAttributeInvalid(description)) {
      throw new IllegalArgumentException("Description cannot be empty or null");
    }
    this.description = description;

    if (jobYears < 0) {
      throw new IllegalArgumentException("JobYears cannot be negative");
    }
    this.jobYears = jobYears;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return Objects.equals(id, employee.id) &&
            Objects.equals(firstName, employee.firstName) &&
            Objects.equals(lastName, employee.lastName) &&
            Objects.equals(description, employee.description) &&
            Objects.equals(jobYears, employee.jobYears);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, description, jobYears);
  }
  
  //Getters and Setters
  public Long getId() {return id;}

  public void setId(Long id) {this.id = id;}

  public String getFirstName() {return firstName;}

  public void setFirstName(String firstName) {
    if (isAttributeInvalid(firstName)) {
      throw new IllegalArgumentException("First Name cannot be empty or null");
    }
    this.firstName = firstName;
  }

  public String getLastName() {return lastName;}

  public void setLastName(String lastName) {
    if (isAttributeInvalid(lastName)) {
      throw new IllegalArgumentException("Last Name cannot be empty or null");
    }
    this.lastName = lastName;
  }

  public String getDescription() {return description;}

  public void setDescription(String description) {
    if (isAttributeInvalid(description)) {
      throw new IllegalArgumentException("Description cannot be empty or null");
    }
    this.description = description;
  }

  public int getJobYears() {return jobYears;}

  public void setJobYears(int jobYears) {
    if (jobYears < 0) {
      throw new IllegalArgumentException("JobYears cannot be negative");
    }
    this.jobYears = jobYears;
  }

  @Override
  public String toString() {
    return "Employee{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", description='" + description + '\'' +
            ", jobYears='" + jobYears + '\'' +
            '}';
  }

  private boolean isAttributeInvalid(String attribute){
    if(attribute==null || attribute.isEmpty()){
      return true;
    }
    return false;
  }
}
// end::code[]
```
- `EmployeeTest.java`

To ensure the reliability of the newly introduced jobYears field, the EmployeeTest.java file was updated with comprehensive unit tests. 
The key testing aspects include:

- **Validation Tests:** Confirmed that the constructor and setter methods correctly reject invalid inputs, such as null or empty strings and negative values for jobYears, preventing improper object creation.
- **Positive Scenarios:** Verified that valid inputs allow successful object creation without exceptions, ensuring that the `Employee class functions correctly under normal usage.
- **Equality and Hashing:** Tested the proper implementation of the `equals` and `hashCode` methods to ensure accurate object comparison and correct behavior in collections.
- **String Representation:** Evaluated the `toString` method to confirm that it accurately represents `Employee` object details, improving readability for debugging and logging.

Here are some examples of my tests:

```java
class EmployeeTest {
    @Test
    void shouldCreateEmployeeWithAllArgs() {
        //arrange
        //act
        Employee employee = new Employee("Ruben", "Rocha", "Student", 5);
        //assert
        assertNotNull(employee);
    }

  @Test
  void emptyFirstNameShouldThrowException(){
    //arrange
    //act
    //assert
    assertThrows(Exception.class, () -> new Employee("","Rocha", "Student", 5));
  }

  @Test
  void negativeJobYearsShouldThrowException(){
    //arrange
    //act
    //assert
    assertThrows(Exception.class, () -> new Employee("Ruben","Rocha", "Student",-1));
  }

  @Test
  void shouldReturnTrueIfSameEmployeeAttributes(){
    //arrange
    Employee employee = new Employee("Ruben", "Rocha", "Student", 5);
    Employee employee2 = new Employee("Ruben", "Rocha", "Student", 5);
    //act
    boolean result = employee.equals(employee2);
    //assert
    assertTrue(result);
  }

  @Test
  void shouldReturnSameHashCodeForEqualEmployees() {
    // arrange
    Employee employee1 = new Employee("Ruben", "Rocha", "Student", 5);
    Employee employee2 = new Employee("Ruben", "Rocha", "Student", 5);

    employee1.setId(1L);
    employee2.setId(1L);

    // act
    int hashCode1 = employee1.hashCode();
    int hashCode2 = employee2.hashCode();

    // assert
    assertEquals(hashCode1, hashCode2);
  }

  @Test
  void whenSetWithEmptyFirstNameThrowsException() {
    //arrange
    String firstName = "";
    Employee employee1 = new Employee("Ruben", "Rocha", "Student", 5);
    //act+assert
    assertThrows(Exception.class, () -> employee1.setFirstName(firstName));
  }

  @Test
  void whenSetWithNegativeJobYearsThrowsException(){
    //arrange
    int jobYears = -1;
    Employee employee1 = new Employee("Ruben", "Rocha", "Student", 5);
    //act+assert
    assertThrows(Exception.class, () -> employee1.setJobYears(jobYears));
  }

  @Test
  void shouldReturnEmployeeToString(){
    //arrange
    Employee employee1 = new Employee("Ruben", "Rocha", "Student", 5, "rubenrocha26@hotmail.com");
    employee1.setId(1L);
    //act
    String result = employee1.toString();
    // assert
    String expected = "Employee{id=1, firstName='Ruben', lastName='Rocha', description='Student', jobYears='5'}";
    assertEquals(expected, result);
  }   
}
```

- `DatabeseLoader.java`

The `DatabaseLoader.java` class, responsible for pre-loading the database with sample data, was modified to include jobYears information for sample employees. 
This update ensures that the application can demonstrate the functionality of the new field from the very beginning.

Below is a code snippet illustrating the modifications made to `DatabaseLoader` to incorporate jobYears for the sample employees:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Ruben Rocha
 */
// tag::code[]
@Component // <1>
public class DatabaseLoader implements CommandLineRunner { // <2>

	private final EmployeeRepository repository;

	@Autowired // <3>
	public DatabaseLoader(EmployeeRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... strings) throws Exception { // <4>
		this.repository.save(new Employee("Frodo", "Baggins", "ring bearer", 5));
		this.repository.save(new Employee("Ruben", "Rocha", "Student", 6));
	}
}
// end::code[]
```

- `app.js`

The React components within `app.js were updated to support the display of the newly introduced jobYears field in the employee list.

Specifically, the `EmployeeList` and `Employee` components were modified to include a "Job Years" column in the rendered table. This allows users to view the number of years an employee has been with the company alongside their other details.

Below is a code snippet illustrating the modifications made to app.js to integrate the jobYears field into the application's frontend:

```js
class EmployeeList extends React.Component{
  render() {
    const employees = this.props.employees.map(employee =>
            <Employee key={employee._links.self.href} employee={employee}/>
    );
    return (
            <table>
              <tbody>
                <tr>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Description</th>
                  <th>JobYears</th>
                </tr>
                {employees}
              </tbody>
            </table>
    )
  }
}
```

```js
class Employee extends React.Component{
  render() {
    return (
            <tr>
              <td>{this.props.employee.firstName}</td>
              <td>{this.props.employee.lastName}</td>
              <td>{this.props.employee.description}</td>
              <td>{this.props.employee.jobYears}</td>
            </tr>
    )
  }
}
```

5. **Debugging the Server and Client Components**

After verifying the integration of the jobYears field, I ran the application using the command: `./mvnw spring-boot:run`

This allowed me to test its real-time functionality at http://localhost:8080/. 
This step was essential for hands-on testing within the application's interface, ensuring smooth operation and compatibility with existing features.

To further validate the implementation, I leveraged **React DevTools** to inspect component hierarchies, track state updates, and confirm that the jobYears field was correctly managed within the React components. 
This tool was particularly useful in debugging data flow issues and ensuring seamless interaction between the frontend and backend.

Simultaneously, I conducted a comprehensive code review to:

- Verify **server-side data handling,** ensuring that the **jobYears** field was correctly processed and stored.
- Confirm **client-side representation,** making sure that **jobYears** was accurately displayed in the UI and updated dynamically.

By combining real-time testing, **React DevTools**, and meticulous code review, I ensured the correctness of the feature while maintaining high code quality and system reliability.

6. **End of the assignment**

Once I was satisfied with the stability and performance of the new feature, I committed the changes to the repository with a descriptive message outlining the enhancements. 
After that, I pushed the updated code to the remote repository.

To formally mark this significant update, I tagged the commit as `v1.2.0`, adhering to the semantic versioning pattern established for the project. 
Additionally, at the conclusion of the assignment, I tagged the repository with `ca1-part1.1`, providing a clear reference point for this specific phase of development.



## Part 1.2 Development Using Branches

This part demonstrates the use of branches in development, showcasing the benefits and best practices.

## Alternative Solution

A comparison between SVN and Git, and an exploration of utilizing SVN for the assignment.

## Conclusion