package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    @Test
    void shouldCreateEmployeeWithNoArgs(){
        //arrange
        //act
        Employee employee = new Employee();
        //assert
        assertNotNull(employee);
    }

    @Test
    void shouldCreateEmployeeWithAllArgs(){
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
    void nullFirstNameShouldThrowException(){
        //arrange
        //act
        //assert
        assertThrows(Exception.class, () -> new Employee(null,"Rocha", "Student", 5));
    }

    @Test
    void emptyLastNameShouldThrowException(){
        //arrange
        //act
        //assert
        assertThrows(Exception.class, () -> new Employee("Ruben","", "Student", 5));
    }

    @Test
    void nullLastNameShouldThrowException(){
        //arrange
        //act
        //assert
        assertThrows(Exception.class, () -> new Employee("Ruben",null, "Student", 5));
    }

    @Test
    void emptyDescriptionShouldThrowException(){
        //arrange
        //act
        //assert
        assertThrows(Exception.class, () -> new Employee("Ruben","Rocha", "", 5));

    }

    @Test
    void nullDescriptionShouldThrowException(){
        //arrange
        //act
        //assert
        assertThrows(Exception.class, () -> new Employee("Ruben","Rocha", null, 5));

    }

    @Test
    void negativeJobYearsShouldThrowException(){
        //arrange
        //act
        //assert
        assertThrows(Exception.class, () -> new Employee("Ruben","Rocha", "Student",-1));
    }

    //EqualsOverride Tests
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
    void shouldReturnTrueIfSameEmployee(){
        //arrange
        Employee employee = new Employee("Ruben", "Rocha", "Student", 5);
        //act
        boolean result = employee.equals(employee);
        //assert
        assertTrue(result);

    }

    @Test
    void shouldReturnFalseIfDifferentEmployeeId(){
        //arrange
        Employee employee = new Employee("Ruben", "Rocha", "Student", 5);
        Employee employee2 = new Employee("Ruben", "Rocha", "Student", 5);

        employee.setId(1L);
        employee2.setId(2L);
        //act
        boolean result = employee.equals(employee2);
        //assert
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseIfDifferentFirstName(){
        //arrange
        Employee employee = new Employee("Alexandre", "Rocha", "Student", 5);
        Employee employee2 = new Employee("Ruben", "Rocha", "Student", 5);
        //act
        boolean result = employee.equals(employee2);
        //assert
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseIfDifferentLastName(){
        //arrange
        Employee employee = new Employee("Ruben", "Costa", "Student", 5);
        Employee employee2 = new Employee("Ruben", "Rocha", "Student", 5);
        //act
        boolean result = employee.equals(employee2);
        //assert
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseIfDifferentDescription(){
        //arrange
        Employee employee = new Employee("Ruben", "Rocha", "Teacher", 5);
        Employee employee2 = new Employee("Ruben", "Rocha", "Student", 5);
        //act
        boolean result = employee.equals(employee2);
        //assert
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseIfDifferentJobYears(){
        //arrange
        Employee employee = new Employee("Ruben", "Rocha", "Teacher", 5);
        Employee employee2 = new Employee("Ruben", "Rocha", "Student", 1);
        //act
        boolean result = employee.equals(employee2);
        //assert
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseIfNullEmployeeCompared(){
        //arrange
        Employee employee = new Employee("Ruben", "Rocha", "Student", 5);
        //act
        boolean result = employee.equals(null);
        //assert
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseIfDifferentClass() {
        // arrange
        Employee employee = new Employee("Ruben", "Rocha", "Student", 5);
        String differentClassObject = "I am not an Employee";
        // act
        boolean result = employee.equals(differentClassObject);
        // assert
        assertFalse(result);
    }
}