package book.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import book.store.entity.BookStore;

public interface BookStoreDao extends JpaRepository<BookStore, Long>  {

}
