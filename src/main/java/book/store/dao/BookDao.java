package book.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import book.store.entity.Book;

public interface BookDao extends JpaRepository<Book, Long> {

}
