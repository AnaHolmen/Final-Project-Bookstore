package book.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import book.store.controller.model.BookStoreData;
import book.store.controller.model.BookStoreData.BookStoreBook;
import book.store.controller.model.BookStoreData.BookStoreCustomer;
import book.store.controller.model.BookStoreData.BookStoreEmployee;
import book.store.dao.BookDao;
import book.store.dao.BookStoreDao;
import book.store.dao.CustomerDao;
import book.store.dao.EmployeeDao;
import book.store.entity.Book;
import book.store.entity.BookStore;
import book.store.entity.Customer;
import book.store.entity.Employee;


@Service
public class BookStoreService {

    @Autowired
    private BookStoreDao bookStoreDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private BookDao bookDao;

    @Transactional
    public BookStoreData saveBookStore(BookStoreData bookStoreData) {
        BookStore bookStore = findOrCreateBookStore(bookStoreData.getBookStoreId());
        copyBookStoreFields(bookStore, bookStoreData);

        BookStore dbBookStore = bookStoreDao.save(bookStore);
        return new BookStoreData(dbBookStore);
    }

    private void copyBookStoreFields(BookStore bookStore, BookStoreData bookStoreData) {
        bookStore.setBookStoreId(bookStoreData.getBookStoreId());
        bookStore.setBookStoreName(bookStoreData.getBookStoreName());
        bookStore.setBookStoreAddress(bookStoreData.getBookStoreAddress());
        bookStore.setBookStoreCity(bookStoreData.getBookStoreCity());
        bookStore.setBookStoreState(bookStoreData.getBookStoreState());
        bookStore.setBookStoreZip(bookStoreData.getBookStoreZip());
        bookStore.setBookStorePhone(bookStoreData.getBookStorePhone());
    }

    private BookStore findOrCreateBookStore(Long bookStoreId) {
        BookStore bookStore;

        if (Objects.isNull(bookStoreId)) {
            bookStore = new BookStore();
        } else {
            bookStore = findBookStoreById(bookStoreId);
        }

        return bookStore;
    }

    private BookStore findBookStoreById(Long bookStoreId) {
        return bookStoreDao.findById(bookStoreId)
                .orElseThrow(() -> new NoSuchElementException("BookStore with ID=" + bookStoreId + " does not exist."));
    }

    @Transactional(readOnly = true)
    public BookStoreData retrieveBookStoreById(Long bookStoreId) {
        return new BookStoreData(findBookStoreById(bookStoreId));
    }

    private Employee findEmployeeById(Long bookStoreId, Long employeeId) {
        Employee dbEmployee = employeeDao.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " does not exist."));

        if (!dbEmployee.getBookStore().getBookStoreId().equals(bookStoreId)) {
            throw new IllegalArgumentException(
                    "Employee with ID=" + employeeId + " is not an employee at book store with ID=" + bookStoreId + ".");
        } else {
            return dbEmployee;
        }
    }

    private Employee findOrCreateEmployee(Long employeeId, Long bookStoreId) {
        Employee employee;

        if (Objects.isNull(employeeId)) {
            employee = new Employee();
        } else {
            employee = findEmployeeById(bookStoreId, employeeId);
        }
        return employee;
    }

    private void copyEmployeeFields(Employee employee, BookStoreEmployee bookStoreEmployee) {
        employee.setEmployeeFirstName(bookStoreEmployee.getEmployeeFirstName());
        employee.setEmployeeLastName(bookStoreEmployee.getEmployeeLastName());
        employee.setEmployeeJobTitle(bookStoreEmployee.getEmployeeJobTitle());
        employee.setEmployeePhone(bookStoreEmployee.getEmployeePhone());
        // Assuming employeeId is managed by the database and not set manually
    }

    @Transactional
    public BookStoreEmployee saveEmployee(Long bookStoreId, BookStoreEmployee bookStoreEmployee) {
        BookStore bookStore = findBookStoreById(bookStoreId);

        Employee employee = findOrCreateEmployee(bookStoreEmployee.getEmployeeId(), bookStoreId);
        copyEmployeeFields(employee, bookStoreEmployee);
        employee.setBookStore(bookStore);
        bookStore.getEmployees().add(employee);
        Employee dbEmployee = employeeDao.save(employee);
        return new BookStoreEmployee(dbEmployee);
    }

    private Customer findCustomerById(Long customerId, Long bookStoreId) {
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " does not exist."));

        boolean found = false;
        for (BookStore bs : customer.getBookStores()) {
            if (bs.getBookStoreId().equals(bookStoreId)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException(
                    "BookStore with ID=" + bookStoreId + " not found for the Customer with ID=" + customerId);
        }
        return customer;
    }

    private Customer findOrCreateCustomer(Long customerId, Long bookStoreId) {
        Customer customer;

        if (Objects.isNull(customerId)) {
            customer = new Customer();
        } else {
            customer = findCustomerById(customerId, bookStoreId);
        }
        return customer;
    }

    private void copyCustomerFields(Customer customer, BookStoreCustomer bookStoreCustomer) {
        customer.setCustomerFirstName(bookStoreCustomer.getCustomerFirstName());
        customer.setCustomerLastName(bookStoreCustomer.getCustomerLastName());
        customer.setCustomerEmail(bookStoreCustomer.getCustomerEmail());
        // Assuming customerId is managed by the database and not set manually
    }

    @Transactional
    public BookStoreCustomer saveCustomer(Long bookStoreId, BookStoreCustomer bookStoreCustomer) {
        BookStore bookStore = findBookStoreById(bookStoreId);
        Long customerId = bookStoreCustomer.getCustomerId();
        Customer customer = findOrCreateCustomer(customerId, bookStoreId);
        copyCustomerFields(customer, bookStoreCustomer);
        bookStore.getCustomers().add(customer);
        customer.getBookStores().add(bookStore);
        Customer dbCustomer = customerDao.save(customer);
        return new BookStoreCustomer(dbCustomer);
    }

    public BookStoreBook saveBook(Long bookStoreId, BookStoreBook bookstorebook) {
        BookStore bookStore = findBookStoreById(bookStoreId);
        Long bookId = bookstorebook.getBookId();
         Book book = findOrCreateBook(bookStoreId, bookId);
        copyBookFields(book, bookstorebook);
         bookStore.getBook().add(book);
         book.setBookStore(bookStore);
         bookStore.getBook().add(book);
        Book savedBook = bookDao.save(book);
        return new BookStoreBook(savedBook);
    }

    private Book findOrCreateBook(Long bookStoreId, Long bookId) {
    	
    	 if (Objects.isNull(bookId)) {
          return new Book();
         } 
           
       return findbookById(bookId, bookStoreId);
         
         }
        
     
    	
		
	

	private Book findbookById(Long bookId, Long bookStoreId) {
		
		Book book = bookDao.findById(bookId)
	                .orElseThrow(() -> new NoSuchElementException("Book with ID=" + bookId + " does not exist."));
		
	if (book.getBookStore().getBookStoreId()!=bookStoreId){

		 throw new IllegalArgumentException(
                 "BookStore with ID=" + bookStoreId + " not found for the Book with ID=" + bookId);
	}

      return book;
	
	}

	private void copyBookFields(Book book, BookStoreBook bookstorebook) {
		book.setGenre(bookstorebook.getGenre());
	       book.setPrice(bookstorebook.getPrice());
	       book.setDescription(bookstorebook.getDescription());
	       book.setAuthor(bookstorebook.getAuthor());
	
		
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
    public List<Book> getBooksForBookStore(Long bookStoreId) {
        BookStore bookStore = findBookStoreById(bookStoreId);
        return (List<Book>) bookStore.getBook();
    }
    
    @Transactional(readOnly = true)
    public List<BookStoreData> retrieveAllBookStores() {
        List<BookStore> bookStores = bookStoreDao.findAll();
        List<BookStoreData> result = new LinkedList<>();

        for (BookStore bookStore : bookStores) {
        	BookStoreData psd = new BookStoreData(bookStore);
            // Clear unnecessary collections or fields if needed
        	psd.getCustomers().clear();
			psd.getEmployees().clear();
			psd.getBooks().clear();
			
            
            result.add(psd);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public BookStoreData returnBookStoreById(Long bookStoreId) {
        BookStore bookStore = findBookStoreById(bookStoreId);
        return new BookStoreData(bookStore);
    }

    @Transactional
    public void deleteBookStoreById(Long bookStoreId) {
        BookStore bookStore = findBookStoreById(bookStoreId);
        bookStoreDao.delete(bookStore);
        
    }
        
        
       
    }

    
    
    

	
	



