package book.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import book.store.controller.model.BookStoreData;
import book.store.controller.model.BookStoreData.BookStoreBook;
import book.store.controller.model.BookStoreData.BookStoreCustomer;
import book.store.controller.model.BookStoreData.BookStoreEmployee;
import book.store.entity.Book;
import book.store.service.BookStoreService;


@RestController
@RequestMapping("/book_store") // Maps all HTTP requests prefixed with /book_store to this controller
@Slf4j // Enables logging via SLF4J
public class BookStoreController {

    @Autowired
    private BookStoreService bookStoreService; // Injects BookStoreService bean

  //Maps POST requests to the method. The requests are sent to /Boko_store. 
  	//The method creates/inserts a book store's data into the database by calling the saveBookStore() method in the BookStoreService class
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookStoreData insertBookStore(@RequestBody BookStoreData bookStoreData) {
        log.info("Creating the book store {}", bookStoreData);
        return bookStoreService.saveBookStore(bookStoreData);
    }

  //Maps PUT requests to the method. The requests are sent to /book_store/{bookStoreId}.
  	//The method updates a book store's data in the database by calling the saveBookStore() method in the BookStoreService class
    
    @PutMapping("/{bookStoreId}")
    public BookStoreData updateBookStore(@PathVariable Long bookStoreId, @RequestBody BookStoreData bookStoreData) {
        bookStoreData.setBookStoreId(bookStoreId); // Sets the ID from path variable
        log.info("Updating book store {}", bookStoreData);
        return bookStoreService.saveBookStore(bookStoreData);
    }

  //Maps a POST request to the method. The requests are sent to /book_store/{bookStoreId}/employee.
  	//The method inserts an employee's data into the database by calling the saveEmployee() method in the BookStoreService class
    
    @PostMapping("/{bookStoreId}/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public BookStoreEmployee insertEmployee(@PathVariable Long bookStoreId, @RequestBody BookStoreEmployee bookStoreEmployee) {
        log.info("Creating employee {} for book store with ID={}", bookStoreEmployee, bookStoreId);
        return bookStoreService.saveEmployee(bookStoreId, bookStoreEmployee);
    }
    
  //Maps a POST request to the method. The requests are sent to /book_store/{bookStoreId}/customer.
  	//The method inserts a customer's data into the database by calling the saveCustomer() method in the BookStoreService class
   
    @PostMapping("/{bookStoreId}/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public BookStoreCustomer insertCustomer(@PathVariable Long bookStoreId, @RequestBody BookStoreCustomer bookStoreCustomer) {
        log.info("Creating customer {} for book store with ID={}", bookStoreCustomer, bookStoreId);
        return bookStoreService.saveCustomer(bookStoreId, bookStoreCustomer);
        
    }
    
    @PostMapping("/{bookstoreId}/book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookStoreBook insertBook(@PathVariable Long bookstoreId, @RequestBody BookStoreBook book) {
        log.info("Creating book {} for bookstore with ID={}", book, bookstoreId);
        return bookStoreService.saveBook(bookstoreId, book);
    }

  //Maps a GET request to the method. The requests are sent to /Book_store.
  	//The method retrieves all Book store data from the database by calling the retrieveAllBooktores() method in the BookStoreService class
 
    @GetMapping()
    public List<BookStoreData> retrieveAllBookStores() {
        log.info("Retrieve all book stores.");
        return bookStoreService.retrieveAllBookStores();
    }
    
  //Maps a GET request to the method. The requests are sent to /book_store/{bookStoreId}. 
  	//The method retrieves a book store's data from the database by calling the retrieveBookStoreById() method in the BookStoreService class
    
    @GetMapping("/{bookStoreId}")
    public BookStoreData retrieveBookStoreById(@PathVariable Long bookStoreId) {
        log.info("Retrieving book store by ID={}", bookStoreId);
        return bookStoreService.retrieveBookStoreById(bookStoreId);
    }
  //Maps a DELETE request to the method. The requests are sent to /book_store/{bookStoreId}.
  	//The method deletes a book store's data from the database by calling the deleteBookStoreById() method in the BookStoreService class
    
    @DeleteMapping("/{bookStoreId}")
    public Map<String, String> deleteBookStoreById(@PathVariable Long bookStoreId) {
        log.info("Deleting book store with ID={}", bookStoreId);
        bookStoreService.deleteBookStoreById(bookStoreId);

        return Map.of("message", "Deletion of book store with ID=" + bookStoreId
                + " was successful.");
    }
}

  