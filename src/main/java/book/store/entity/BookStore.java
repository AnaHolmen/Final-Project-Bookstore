package book.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity  
@Data



public class BookStore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookStoreId;
	
	private String bookStoreName;
	private String bookStoreAddress;
	private String bookStoreCity;
	private String bookStoreState;
	private String bookStoreZip;
	private String bookStorePhone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "book_store_customer", joinColumns = @JoinColumn(name = "book_store_id"),
	inverseJoinColumns = @JoinColumn (name = "customer_id"))
	private Set<Customer> customers = new HashSet<>();
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "bookStore", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<>();
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "bookStore", cascade = CascadeType.ALL)
	private Set<Book> book = new HashSet<>();


	

}