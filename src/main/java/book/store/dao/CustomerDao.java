package book.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import book.store.entity.Customer;

//This is the DAO interface for the customer table. The interface extends the JpaRepository interface that is
//used to perform CRUD operations on the customer table. It generates the SQL statements using its interfaces. 

public interface CustomerDao extends JpaRepository<Customer, Long> {



}
