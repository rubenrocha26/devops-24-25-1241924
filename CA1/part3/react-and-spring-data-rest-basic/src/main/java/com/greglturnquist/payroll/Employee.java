/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greglturnquist.payroll;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * @author Ruben Rocha
 */
// tag::code[]
@Entity // <1>
public class Employee {

	private @Id
	@GeneratedValue Long id; // <2>
	private String firstName;
	private String lastName;
	private String description;
	private int jobYears;
	private String email;

	//Empty Constructor
	public Employee() {
	}

	//Constructor
	public Employee(String firstName, String lastName, String description, int jobYears, String email) {
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

		if (isEmailInvalid(email)) {
			throw new IllegalArgumentException("Email cannot be empty, null or invalid");
		}
		this.email = email;
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
				Objects.equals(jobYears, employee.jobYears) &&
				Objects.equals(email, employee.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, description, jobYears, email);
	}

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

	public String getEmail(){return email;}

	public void setEmail(String email){
		if(isEmailInvalid(email)){
			throw new IllegalArgumentException("Email cannot be empty, null or invalid");
		}
		this.email = email;}

	@Override
	public String toString() {
		return "Employee{" +
			"id=" + id +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", description='" + description + '\'' +
			", jobYears='" + jobYears + '\'' +
			", email='" + email + '\'' +
			'}';
	}

	private boolean isAttributeInvalid(String attribute){
		if(attribute==null || attribute.isEmpty()){
			return true;
		}
		return false;
	}

	private boolean isEmailInvalid(String email){
		if(email==null || email.isEmpty()){
			return true;
		}
		return !email.matches(".*@.*");
	}
}
// end::code[]
