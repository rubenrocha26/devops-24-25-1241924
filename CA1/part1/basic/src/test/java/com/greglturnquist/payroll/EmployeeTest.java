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

}