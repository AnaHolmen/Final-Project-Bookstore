package book.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import book.store.entity.Employee;

//This is the DAO interface for the employee table. The interface extends the JpaRepository interface that is
//used to perform CRUD operations on the employee table. It generates the SQL statements using its interfaces. 

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}